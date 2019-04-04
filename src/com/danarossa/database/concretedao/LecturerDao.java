package com.danarossa.database.concretedao;

import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.ILecturerDao;
import com.danarossa.entities.Lecturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LecturerDao extends AbstractGenericDao<Lecturer, Long> implements ILecturerDao {


    private static final String lecturer_next_primary_key = "lecturer_next_primary_key";
    private static final String lecturer_id = "LECTURER_ID";
    private static final String name = "NAME";
    private static final String surname = "SURNAME";
    private static final String birthday = "BIRTHDAY";
    private static final String position = "POSITION";
    private static final String hire_date = "HIRE_DATE";
    private static final String table = "LECTURERS";

    public LecturerDao(Connection connection) {
        super(connection);
    }

    private String getBasicSelectQuery() {
        return "select " + lecturer_id + ", " + name + ", " + surname + ", " + birthday + ", " + position + ", " + hire_date + " from " + table;
    }

    @Override
    protected String getSelectByIdQuery() {
        return getBasicSelectQuery() + " where " + lecturer_id + " = ?;";
    }

    @Override
    protected String getSelectQuery() {
        return getBasicSelectQuery() + " ;";
    }

    @Override
    protected String getInsertQuery() {
        return "insert into " + table + " (" + lecturer_id + ", " + name + ", " + surname + ", " + birthday + ", " + position + ", " + hire_date + " )\n" +
                "values (?, ?, ?, ?, ?, ?);";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from " + table + " where " + lecturer_id + " = ?;";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + table + " set " + name + " = ? , " + surname + " = ? , " + birthday + " = ? , " + position + " = ? , " + hire_date + " = ? where " + lecturer_id + " = ?;";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select " + table + "_STUDENTS_SQ.nextval as " + lecturer_next_primary_key + " from dual;";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Lecturer entity) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getSurname());
        statement.setTimestamp(4, new Timestamp(entity.getBirthday().getTime()));
        statement.setString(5, entity.getPosition());
        statement.setTimestamp(6, new Timestamp(entity.getHireDate().getTime()));

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Lecturer entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setTimestamp(3, new Timestamp(entity.getBirthday().getTime()));
        statement.setString(4, entity.getPosition());
        statement.setTimestamp(5, new Timestamp(entity.getHireDate().getTime()));
        statement.setLong(6, entity.getId());
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(lecturer_next_primary_key);
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
        Long id = rs.getLong(LecturerDao.lecturer_id);
        String name = rs.getString(LecturerDao.name);
        String surname = rs.getString(LecturerDao.surname);
        Date birthday = rs.getTimestamp(LecturerDao.birthday);
        String position = rs.getString(LecturerDao.position);
        Date hireDate = rs.getTimestamp(LecturerDao.hire_date);
        return new Lecturer(id, name, surname, birthday, position, hireDate);
    }

}
