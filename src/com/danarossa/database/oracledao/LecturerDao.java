package com.danarossa.database.oracledao;

import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.ILecturerDao;
import com.danarossa.entities.Lecturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LecturerDao extends AbstractGenericDao<Lecturer, Long> implements ILecturerDao {

    private static final String LECTURER_ID = "LECTURER_ID";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String BIRTHDAY = "BIRTHDAY";
    private static final String POSITION = "POSITION";
    private static final String HIRE_DATE = "HIRE_DATE";
    private static final String TABLE = "LECTURERS";
    private static final String LECTURER_NEXT_PRIMARY_KEY = "LECTURER_NEXT_PRIMARY_KEY";

    public LecturerDao(Connection connection) {
        super(connection);
    }

    private String getBasicSelectQuery() {
        return "select " + getFieldsNames() + " from " + TABLE;
    }

    @Override
    protected String getSelectByIdQuery() {
        return getBasicSelectQuery() + " where " + LECTURER_ID + " = ?";
    }

    @Override
    protected String getSelectQuery() {
        return getBasicSelectQuery();
    }

    @Override
    protected String getInsertQuery() {
        return "insert into " + TABLE + " (" + getFieldsNames() + " )" +
                "values (?, ?, ?, ?, ?, ?)";
    }

    static String getFieldsNames() {
        return LECTURER_ID + ", " + NAME + ", " + SURNAME + ", " + BIRTHDAY + ", " +
                POSITION + ", " + HIRE_DATE;
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from " + TABLE + " where " +
                LECTURER_ID + " = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + TABLE + " set " + NAME + " = ? , " + SURNAME + " = ? , " +
                BIRTHDAY + " = ? , " + POSITION + " = ? , " + HIRE_DATE + " = ? where " +
                LECTURER_ID + " = ?";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select " + TABLE + "_STUDENTS_SQ.nextval as " + LECTURER_NEXT_PRIMARY_KEY  + " from dual";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Lecturer entity) throws SQLException {
        statement.setLong(1, entity.getId());
        setFields(statement, entity, 2);

    }

    private void setFields(PreparedStatement statement, Lecturer entity, int startIndex) throws SQLException {
        statement.setString(startIndex, entity.getName());
        statement.setString(startIndex + 1, entity.getSurname());
        statement.setTimestamp(startIndex + 2, new Timestamp(entity.getBirthday().getTime()));
        statement.setString(startIndex + 3, entity.getPosition());
        statement.setTimestamp(startIndex + 4, new Timestamp(entity.getHireDate().getTime()));
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Lecturer entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setLong(6, entity.getId());
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(LECTURER_NEXT_PRIMARY_KEY);
        } else throw new PersistException("No value returned!");
    }

    @Override
    protected List<Lecturer> parseResultSet(ResultSet rs) throws SQLException {
        List<Lecturer> list = new ArrayList<>();
        while (rs.next()) {
            list.add(parseLecturer(rs));
        }
        return list;
    }

    static Lecturer parseLecturer(ResultSet rs) throws SQLException {
        Long id = rs.getLong(LECTURER_ID);
        String name = rs.getString(NAME);
        String surname = rs.getString(SURNAME);
        Date birthday = rs.getTimestamp(BIRTHDAY);
        String position = rs.getString(POSITION);
        Date hireDate = rs.getTimestamp(HIRE_DATE);
        return new Lecturer(id, name, surname, birthday,
                position, hireDate);
    }

}
