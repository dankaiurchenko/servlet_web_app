package com.danarossa.database.concretedao;

import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.IStudentDao;
import com.danarossa.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDao extends AbstractGenericDao<Student, Long>implements IStudentDao {

    private static final String student_next_primary_key = "student_next_primary_key";
    private static final String student_id = "STUDENT_ID";
    private static final String name = "NAME";
    private static final String surname = "SURNAME";
    private static final String birthday = "BIRTHDAY";
    private static final String date_entered = "DATE_ENTERED";
    private static final String table = "STUDENTS";

    public StudentDao(Connection connection) {
        super(connection);
    }

    private String getBasicSelectQuery(){
        return "select " + student_id + ", " + name + ", " + surname + ", " + birthday + ", " + date_entered + " from " + table;
    }

    @Override
    protected String getSelectByIdQuery() {
        return getBasicSelectQuery() + " where " + student_id + " = 5;";
    }

    @Override
    protected String getSelectQuery() {
        return getBasicSelectQuery() + " ;";
    }

    @Override
    protected String getInsertQuery() {
        return "insert into " + table + " (" + student_id + ", " + name + ", " + surname + ", " + birthday + ", " + date_entered + ") values (?, ?, ?, ?, ?);";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete  from " + table + " where " + student_id + " = ?;";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + table + " set " + name + " = ?, " + surname + " = ?, " + birthday + " = ? , " + date_entered + " = ? where " + student_id + " = ?;";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select LECTURERS_" + table + "_SQ.nextval as " + student_next_primary_key + " from dual;";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
       statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Student entity) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getSurname());
        statement.setTimestamp(4, new Timestamp(entity.getBirthday().getTime()));
        statement.setTimestamp(5, new Timestamp(entity.getDateEntered().getTime()));
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Student entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setTimestamp(3, new Timestamp(entity.getBirthday().getTime()));
        statement.setTimestamp(4, new Timestamp(entity.getDateEntered().getTime()));
        statement.setLong(5, entity.getId());
        
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(student_next_primary_key);
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

    private static Student parseStudent(ResultSet rs) throws SQLException {
        long id = rs.getLong(StudentDao.student_id);
        String name = rs.getString(StudentDao.name);
        String surname = rs.getString(StudentDao.surname);
        Date birthday = rs.getTimestamp(StudentDao.birthday);
        Date dateEntered = rs.getTimestamp(StudentDao.date_entered);
        return new Student(id, name, surname, birthday, dateEntered);
    }


}
