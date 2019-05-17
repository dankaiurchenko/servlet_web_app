package com.danarossa.database.posgres;

import com.danarossa.database.PersistException;
import com.danarossa.database.PostgresDabFactory;
import com.danarossa.entities.User;
import com.danarossa.router.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoPostgres extends AbstractGenericDao<User, Integer> implements com.danarossa.database.daointerfaces.UserDao {

    private static final String USER_ID = "USER_ID";
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String SURNAME = "SURNAME";
    private static final String DATE_ENTERED = "DATE_ENTERED";
    private static final String ROLE = "Role";

    public UserDaoPostgres(PostgresDabFactory.PostgresConnectionPool connectionPool) {
        super(connectionPool, "user_sql.properties");
    }


    @Override
    protected void setId(PreparedStatement statement, Integer id) throws SQLException {
        statement.setInt(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User entity) throws SQLException {
        setFields(statement, entity);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User entity) throws SQLException {
        setFields(statement, entity);
        statement.setString(7, entity.getToken());
        statement.setInt(8, entity.getId());
    }


    private void setFields(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getEmail());
        statement.setString(2, entity.getPassword());
        statement.setString(3, entity.getName());
        statement.setString(4, entity.getSurname());
        statement.setTimestamp(5, new Timestamp(entity.getDateEntered().getTime()));
        statement.setString(6, entity.getRole().toString());
    }


    @Override
    protected Integer parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getInt("USER_ID");
        } else throw new PersistException("No value returned!");
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws SQLException {
        List<User> list = new ArrayList<>();
        while (rs.next()) {
            list.add(parseUser(rs));
        }
        return list;
    }

    static User parseUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt(USER_ID);
        String email = rs.getString(EMAIL);
        String password = rs.getString(PASSWORD);
        String name = rs.getString(NAME);
        String surname = rs.getString(SURNAME);
        Date dateEntered = rs.getTimestamp(DATE_ENTERED);
        Role role = Role.valueOf(rs.getString(ROLE));
        return new User(id, name, surname, email, password, dateEntered, role);
    }

    @Override
    public List<User> getAllLecturersForStudent(Integer studentId) {
        String sql = sqlQueries.getProperty("select.lecturers.for.student");
        return getFromQueryWithId(studentId, sql);
    }

    @Override
    public User getUserByToken(String token) {
        String sql = sqlQueries.getProperty("select.by.token");
        return getUniqueFromString(token, sql);
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = sqlQueries.getProperty("select.by.email");
        return getUniqueFromString(email, sql);
    }

    @Override
    public User getUserByEmailAndPass(String email, String password) {
        List<User> list;
        String sql = sqlQueries.getProperty("select.by.email.and.pass");
        log(sql, "LOG SelectByIdQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,email);
            statement.setString(2,password);
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

    @Override
    public List<User> getAllOfRole(Role role) {
        String sql = sqlQueries.getProperty("select.all.with.role");
        return getFromQueryWithString(role.name(), sql);
    }

    @Override
    public List<User> getAllStudentsOfRealizedCourse(Integer realizedCourseId) {
        String sql = sqlQueries.getProperty("select.students.of.realized.course");
        return getFromQueryWithId(realizedCourseId, sql);
    }
}

