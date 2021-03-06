package com.danarossa.database.posgres;

import com.danarossa.database.PersistException;
import com.danarossa.database.PostgresDabFactory;
import com.danarossa.database.daointerfaces.GenericDao;
import com.danarossa.entities.Entity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @param <E> entity type
 * @param <K>  type of entity's primary key
 */
public abstract class AbstractGenericDao<E extends Entity<K>, K> implements GenericDao<E, K> {

    Connection connection;
    private final PostgresDabFactory.PostgresConnectionPool connectionPool;
    Properties sqlQueries;

    AbstractGenericDao(PostgresDabFactory.PostgresConnectionPool connectionPool, String SQL_PROPERTIES_FILE) {
        this.connectionPool = connectionPool;
        this.connection = connectionPool.getConnection();
        sqlQueries = new Properties();
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SQL_PROPERTIES_FILE)){
            if (inputStream != null) {
                sqlQueries.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + SQL_PROPERTIES_FILE + "' not found in the classpath");
            }
        }catch (Exception e){
            throw new PersistException("Unable to open sql queries File", e);
        }
    }

    @Override
    public List<E> getAll() {
        List<E> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public E getEntityById(K id) {
        List<E> list;
        String sql = getSelectByIdQuery();
        log(sql, "LOG SelectByIdQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setId(statement, id);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    void log(String sql, String msg) {
        System.out.println(msg);
        System.out.println(sql);
    }

    @Override
    public void update(E entity) {
        String sql = getUpdateQuery();
        log(sql, "LOG UpdateQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(statement, entity); // заполнение аргументов запроса оставим на совесть потомков
            int count = statement.executeUpdate();
            if (count > 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException(e);
        }
    }

    @Override
    public void delete(K id) {
        String sql = getDeleteQuery();
        log(sql, "LOG DeleteQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setId(statement, id); // заполнение аргументов запроса оставим на совесть потомков
            int count = statement.executeUpdate();
            if (count > 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void insert(E entity) {
        if (entity.getId() != null) {
            throw new PersistException("Object is already persist.");
        }
        String sql = getInsertQuery();
        log(sql, "LOG InsertQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, entity);
            entity.setId(parseResultSetForPrimaryKey(statement.executeQuery()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException(e);
        }
    }

    public void close() {
        connectionPool.releaseConnection(connection);
        this.connection = null;
    }

    private String getSelectByIdQuery() {
        return sqlQueries.getProperty("select.by.id");
    }

    private String getSelectQuery() {
        return sqlQueries.getProperty("select.all");
    }

    private String getInsertQuery() {
        return sqlQueries.getProperty("insert");
    }

    private String getDeleteQuery() {
        return sqlQueries.getProperty("delete");
    }

    private String getUpdateQuery() {
        return sqlQueries.getProperty("update");
    }

    protected abstract void setId(PreparedStatement statement, K id) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, E entity) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, E entity) throws SQLException;

    protected abstract K parseResultSetForPrimaryKey(ResultSet rs) throws SQLException;

    protected abstract List<E> parseResultSet(ResultSet rs) throws SQLException;

    List<E> getFromQueryWithId(Integer id, String sql) {
        List<E> list;
        log(sql,"\ngetFromQueryWithId");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException(e);
        }
        return list;
    }

    List<E> getFromQueryWithString(String string, String sql) {
        List<E> list;
        log(sql, "\ngetFromQueryWithId");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, string);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException(e);
        }
        return list;
    }

    E getUniqueFromString(String string, String sql){
        List<E> list;
        log(sql, "LOG SelectByIdQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,string);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }


}
