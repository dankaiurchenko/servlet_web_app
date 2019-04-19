package com.danarossa.database.oracledao;

import com.danarossa.database.OracleDaoFactory;
import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.IUserDao;
import com.danarossa.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDao extends AbstractGenericDao<User, Long> implements IUserDao {

    private static final String USER_ID = "USER_ID";
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String SURNAME = "SURNAME";
    private static final String DATE_ENTERED = "DATE_ENTERED";
    private static final String TABLE = "users";
    private static final String ROLE = "ROLE";
    private static final String USER_NEXT_PRIMARY_KEY = "USER_NEXT_PRIMARY_KEY";

    public UserDao(OracleDaoFactory.OracleConnectionPool connectionPool) {
        super(connectionPool);
    }

    private String getBasicSelectQuery() {
        return "select " + getFieldsNames() + " from " + TABLE;
    }

    @Override
    protected String getSelectByIdQuery() {
        return getBasicSelectQuery() + " where " + USER_ID + " = ?";
    }

    @Override
    protected String getSelectQuery() {
        return getBasicSelectQuery();
    }

    @Override
    protected String getInsertQuery() {
        return "insert into " + TABLE + " (" + getFieldsNames() + " )" +
                "values (?, ?, ?, ?, ?, ?, ?)";
    }

    static String getFieldsNames() {
        return  USER_ID + ", " + EMAIL+ ", " +  PASSWORD+ ", " + NAME+ ", " + SURNAME+ ", " + DATE_ENTERED + ", " + ROLE;
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from " + TABLE + " where " +
                USER_ID + " = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + TABLE + " set "+ EMAIL + " = ? , " + PASSWORD + " = ? , "+ NAME + " = ? , " + SURNAME + " = ? , " +
                DATE_ENTERED + " = ? , " + ROLE + " = ? where " + USER_ID + " = ?";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select " + "LECTURERS_STUDENTS_SQ.nextval as " + USER_NEXT_PRIMARY_KEY + " from dual";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User entity) throws SQLException {
        statement.setLong(1, entity.getId());
        setFields(statement, entity, 2);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setLong(7, entity.getId());
    }


    private void setFields(PreparedStatement statement, User entity, int startIndex) throws SQLException {
        statement.setString(startIndex, entity.getEmail());
        statement.setString(startIndex + 1, entity.getPassword());
        statement.setString(startIndex + 2, entity.getName());
        statement.setString(startIndex + 3, entity.getSurname());
        statement.setTimestamp(startIndex + 4, new Timestamp(entity.getDateEntered().getTime()));
        statement.setString(startIndex + 5, entity.getRole());
    }


    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(USER_NEXT_PRIMARY_KEY);
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
        Long id = rs.getLong(USER_ID);
        String email = rs.getString(EMAIL);
        String password = rs.getString(PASSWORD);
        String name = rs.getString(NAME);
        String surname = rs.getString(SURNAME);
        Date dateEntered = rs.getTimestamp(DATE_ENTERED);
        String role = rs.getString(ROLE);
        return new User(id, name, surname, email, password, dateEntered, role);
    }

    @Override
    public List<User> getAllLecturersForStudent(Long studentId) {
        String sql = "select " + getFieldsNames() + " from student_lecturers_view where STUDENT_ID = ?";
        return getFromQueryWithId(studentId,sql);
    }
}
