package com.danarossa.database.oracledao;

import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.IStudentDao;
import com.danarossa.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDao extends AbstractGenericDao<Student, Long> implements IStudentDao {

    private static final String STUDENT_NEXT_PRIMARY_KEY = "STUDENT_NEXT_PRIMARY_KEY";
    private static final String STUDENT_ID = "STUDENT_ID";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String BIRTHDAY = "BIRTHDAY";
    private static final String DATE_ENTERED = "DATE_ENTERED";
    private static final String TABLE = "STUDENTS";

    public StudentDao(Connection connection) {
        super(connection);
    }

    private String getBasicSelectQuery() {
        return "select " + getFieldNames() + " from " + TABLE;
    }

    static String getFieldNames() {
        return STUDENT_ID + ", " + NAME + ", " + SURNAME + ", " + BIRTHDAY + ", " + DATE_ENTERED;
    }

    @Override
    protected String getSelectByIdQuery() {
        return getBasicSelectQuery() + " where " + STUDENT_ID + " = 5";
    }

    @Override
    protected String getSelectQuery() {
        return getBasicSelectQuery();
    }

    @Override
    protected String getInsertQuery() {
        return "insert into " + TABLE + " (" + getFieldNames() + ") values (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete  from " + TABLE + " where " + STUDENT_ID + " = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + TABLE + " set " + NAME + " = ?, " + SURNAME + " = ?, " +
                BIRTHDAY + " = ? , " + DATE_ENTERED + " = ? where " + STUDENT_ID + " = ?";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select LECTURERS_" + TABLE + "_SQ.nextval as " + STUDENT_NEXT_PRIMARY_KEY + " from dual";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Student entity) throws SQLException {
        statement.setLong(1, entity.getId());
        setFields(statement, entity, 2);
    }

    private void setFields(PreparedStatement statement, Student entity, int startIndex) throws SQLException {
        statement.setString(startIndex, entity.getName());
        statement.setString(startIndex + 1, entity.getSurname());
        statement.setTimestamp(startIndex + 2, new Timestamp(entity.getBirthday().getTime()));
        statement.setTimestamp(startIndex + 3, new Timestamp(entity.getDateEntered().getTime()));
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Student entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setLong(5, entity.getId());

    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(STUDENT_NEXT_PRIMARY_KEY);
        } else throw new PersistException("No value returned!");
    }

    @Override
    protected List<Student> parseResultSet(ResultSet rs) throws SQLException {
        List<Student> list = new ArrayList<>();
        while (rs.next()) {
            list.add(parseStudent(rs));
        }
        return list;
    }

    static Student parseStudent(ResultSet rs) throws SQLException {
        long id = rs.getLong(STUDENT_ID);
        String name = rs.getString(NAME);
        String surname = rs.getString(SURNAME);
        Date birthday = rs.getTimestamp(BIRTHDAY);
        Date dateEntered = rs.getTimestamp(DATE_ENTERED);
        return new Student(id, name, surname, birthday, dateEntered);
    }


}
