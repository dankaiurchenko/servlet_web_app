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
        setFields(statement, entity, 1);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setInt(7, entity.getId());
    }


    private void setFields(PreparedStatement statement, User entity, int startIndex) throws SQLException {
        statement.setString(startIndex, entity.getEmail());
        statement.setString(startIndex + 1, entity.getPassword());
        statement.setString(startIndex + 2, entity.getName());
        statement.setString(startIndex + 3, entity.getSurname());
        statement.setTimestamp(startIndex + 4, new Timestamp(entity.getDateEntered().getTime()));
        statement.setString(startIndex + 5, entity.getRole().toString());
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
        return getFromQueryWithId(studentId,sql);
    }

    @Override
    public User getUserByToken(String token) {
        return null;
    }
}
