package com.danarossa.database.oracledao;

import com.danarossa.database.OracleDaoFactory;
import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.GenericDao;
import com.danarossa.entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @param <E> entity type
 * @param <K>  type of entity's primary key
 */
public abstract class AbstractGenericDao<E extends Entity<K>, K> implements GenericDao<E, K> {

    protected Connection connection;
    private OracleDaoFactory.OracleConnectionPool connectionPool;

    AbstractGenericDao(OracleDaoFactory.OracleConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.connection = connectionPool.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistException("Error while setting autocommit false", e);
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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

    private void log(String sql, String msg) {
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
            throw new PersistException(e);
        }
    }

    @Override
    public void insert(E entity) {
        if (entity.getId() == null) {
            K newKey = getNextPrimaryKey();
            entity.setId(newKey);
//            throw new PersistException("Object is already persist.");
        }
        // Добавляем запись
        String sql = getInsertQuery();
        log(sql, "LOG InsertQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, entity);
            int count = statement.executeUpdate();
            if (count > 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new PersistException(e);
        }
    }

    @Override
    public K getNextPrimaryKey() {
        K newPrimaryKey;
        String sql = getSelectNextPrimaryKeyQuery();
        log(sql, "LOG NextPrimaryKeyQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            newPrimaryKey = parseResultSetForPrimaryKey(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new PersistException(e);
        }
        return newPrimaryKey;
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new PersistException("Error while rolling back", e);
        }
        System.out.println("Done rollback successfully");
    }


    @Override
    public void close() throws Exception {
//        this.connection.close();
        connectionPool.releaseConnection(connection);
        this.connection = null;
        System.out.println("Closed the connection");
    }

    protected abstract String getSelectByIdQuery();

    protected abstract String getSelectQuery();

    protected abstract String getInsertQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getSelectNextPrimaryKeyQuery();


    protected abstract void setId(PreparedStatement statement, K id) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, E entity) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, E entity) throws SQLException;


    protected abstract K parseResultSetForPrimaryKey(ResultSet rs) throws SQLException;

    protected abstract List<E> parseResultSet(ResultSet rs) throws SQLException;


    List<E> getFromQueryWithId(Long id, String sql) {
        List<E> list;
        log(sql,"\ngetFromQueryWithId");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new PersistException(e);
        }
        return list;
    }
}
