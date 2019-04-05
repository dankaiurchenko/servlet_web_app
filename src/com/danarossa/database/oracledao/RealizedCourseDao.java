package com.danarossa.database.oracledao;

import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.IRealizedCourseDao;
import com.danarossa.entities.Course;
import com.danarossa.entities.RealizedCourse;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RealizedCourseDao extends AbstractGenericDao<RealizedCourse, Long> implements IRealizedCourseDao {

    private static final String REALIZED_COURSE_NEXT_PRIMARY_KEY = "REALIZED_COURSE_NEXT_PRIMARY_KEY";
    private static final String COURSE_ID = "COURSE_ID";
    private static final String REALIZED_COURSE_ID = "REALIZED_COURSE_ID";
    private static final String START_DATE = "START_DATE";
    private static final String COURSES_TABLE = "COURSES";
    private static final String table = "REALIZED_" + COURSES_TABLE;
    private static final String END_DATE = "END_DATE";
    private static final String EXAM_DATE = "EXAM_DATE";
    private static final String STATUS = "STATUS";

    public RealizedCourseDao(Connection connection) {
        super(connection);
    }

    private String getBasicSelectQuery() {
        String LECTURERS = "LECTURERS";
        String LECTURER_ID = "LECTURER_ID";
        return "select " +
                CourseDao.getFieldsNames() + ",  " +
                LecturerDao.getFieldsNames() + ", " +
                getFieldsNames() +
                " from " + table + " join " + COURSES_TABLE + " using (" + COURSE_ID + ") join " +
                LECTURERS + " using (" + LECTURER_ID + ")";
    }

    @Override
    protected String getSelectByIdQuery() {
        return getSelectQuery() + " where" + REALIZED_COURSE_ID + " = ?";
    }

    @Override
    protected String getSelectQuery() {
        return getBasicSelectQuery();
    }

    @Override
    protected String getInsertQuery() {
        return "insert into " + table + "(" + getFieldsNames() + ") values (?, ?, ?, ?, ?, ?)";
    }

    static String getFieldsNames() {
        return REALIZED_COURSE_ID + ", " + COURSE_ID + ", " + START_DATE + ", " +
                END_DATE + ", " + EXAM_DATE + ", " + STATUS;
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from " + table + " where " +
                REALIZED_COURSE_ID + " = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + table + " set  " + COURSE_ID + " = ?, " + START_DATE + " = ?, " +
                END_DATE + " = ?, " + EXAM_DATE + " = ?, " + STATUS + " = ? where " +
                REALIZED_COURSE_ID + " = ?";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select " + table + "_SQ.nextval as " + REALIZED_COURSE_NEXT_PRIMARY_KEY + " from dual";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, RealizedCourse entity) throws SQLException {
        statement.setLong(1, entity.getId());
        setFields(statement, entity, 2);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, RealizedCourse entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setLong(6, entity.getId());
    }

    private void setFields(PreparedStatement statement, RealizedCourse entity, int startIndex) throws SQLException {
        statement.setLong(startIndex, entity.getCourseId());
        statement.setTimestamp(startIndex + 1, new Timestamp(entity.getStartDate().getTime()));
        statement.setTimestamp(startIndex + 2, new Timestamp(entity.getEndDate().getTime()));
        statement.setTimestamp(startIndex + 3, new Timestamp(entity.getExamDate().getTime()));
        statement.setString(startIndex + 4, entity.getStatus());
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(REALIZED_COURSE_NEXT_PRIMARY_KEY);
        } else throw new PersistException("No value returned!");
    }

    @Override
    protected List<RealizedCourse> parseResultSet(ResultSet rs) throws SQLException {
        List<RealizedCourse> list = new ArrayList<>();
        while (rs.next()) {
            list.add(parseRealizedCourse(rs));
        }
        return list;
    }


    static RealizedCourse parseRealizedCourse(ResultSet rs) throws SQLException {
        Long id = rs.getLong(REALIZED_COURSE_ID);
        Course course = CourseDao.parseCourse(rs);
        Date startDate = rs.getTimestamp(START_DATE);
        Date endDate = rs.getTimestamp(END_DATE);
        Date examDate = rs.getTimestamp(EXAM_DATE);
        String status = rs.getString(STATUS);
        return new RealizedCourse(id, course, startDate, endDate, examDate, status);
    }


}





