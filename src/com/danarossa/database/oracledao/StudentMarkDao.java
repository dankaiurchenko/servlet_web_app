package com.danarossa.database.oracledao;

import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.IStudentMarkDao;
import com.danarossa.entities.RealizedCourse;
import com.danarossa.entities.Student;
import com.danarossa.entities.StudentMark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentMarkDao extends AbstractGenericDao<StudentMark, Long> implements IStudentMarkDao {
    private final String COURSES = "COURSES";
    private final String STUDENTS = "STUDENTS";
    private final String STUDENTS_COURSES = STUDENTS + "_COURSES";
    private final String STUDENT_COURSE_NEXT_PRIMARY_KEY = "STUDENT_COURSE_NEXT_PRIMARY_KEY";
    private final String MARK = "MARK";
    private final String COURSE_ID = "COURSE_ID";
    private final String STUDENT_COURSE_ID = "STUDENT_" + COURSE_ID;
    private final String STUDENT_ID = "STUDENT_ID";
    private final String REALIZED_COURSE_ID = "REALIZED_COURSE_ID";

    public StudentMarkDao(Connection connection) {
        super(connection);
    }

    private String getBasicSelectQuery() {
        String REALIZED_COURSES = "REALIZED_COURSES";
        String LECTURERS = "LECTURERS";
        String LECTURER_ID = "LECTURER_ID";
        return "select " + STUDENT_COURSE_ID + ", " + MARK + ",\n" +
                CourseDao.getFieldsNames() +
                StudentDao.getFieldNames() +
                RealizedCourseDao.getFieldsNames() +
                LecturerDao.getFieldsNames() +
                "from " + STUDENTS_COURSES + " join " + REALIZED_COURSES + " using (" +
                REALIZED_COURSE_ID + ")\n" + "  join " + COURSES + " using (" + COURSE_ID + ") join " +
                STUDENTS + " using(" + STUDENT_ID + ") join " + LECTURERS + " using(" + LECTURER_ID + ") ";
    }

    @Override
    protected String getSelectByIdQuery() {
        return getBasicSelectQuery() + " where " + STUDENT_COURSE_ID + " = ?";
    }

    @Override
    protected String getSelectQuery() {
        return getBasicSelectQuery();
    }

    @Override
    protected String getInsertQuery() {
        return "insert into " + STUDENTS_COURSES + "(" + STUDENT_COURSE_ID + ", " +
                STUDENT_ID + ", " + REALIZED_COURSE_ID + ")" +
                "values ( ?, ?, ?);";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from " + STUDENTS + "_" + COURSES + " where " + STUDENT_COURSE_ID + " = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + STUDENTS + "_" + COURSES + " set " + STUDENT_ID + " = ? , " +
                REALIZED_COURSE_ID + " = ? where " + STUDENT_COURSE_ID + " = ?";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select " + STUDENTS + "_" + COURSES + "_SQ.nextval as " +
                STUDENT_COURSE_NEXT_PRIMARY_KEY + " from dual";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, StudentMark entity) throws SQLException {
//        STUDENT_COURSE_ID, STUDENT_ID, REALIZED_COURSE_ID
        statement.setLong(1, entity.getId());
        setFields(statement, entity,2);
    }

    private void setFields(PreparedStatement statement, StudentMark entity, int startIndex) throws SQLException {
        statement.setLong(startIndex, entity.getStudentId());
        statement.setLong(startIndex + 1, entity.getRealizedCourseId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, StudentMark entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setLong(3, entity.getId());
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(STUDENT_COURSE_NEXT_PRIMARY_KEY);
        } else throw new PersistException("No value returned!");
    }

    @Override
    protected List<StudentMark> parseResultSet(ResultSet rs) throws SQLException {
        List<StudentMark> list = new ArrayList<>();
        while (rs.next()) {
            list.add(parseStudentCourse(rs));
        }
        return list;
    }

    private StudentMark parseStudentCourse(ResultSet rs) throws SQLException {
        Long id = rs.getLong(STUDENT_COURSE_ID);
        Student student = StudentDao.parseStudent(rs);
        RealizedCourse realizedCourse = RealizedCourseDao.parseRealizedCourse(rs);
        Double mark = rs.getDouble(MARK);
        return new StudentMark(id, student, realizedCourse, mark);
    }

}
