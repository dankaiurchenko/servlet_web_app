package com.danarossa.database.oracledao;

import com.danarossa.database.OracleDaoFactory;
import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.IRealizedCourseDao;
import com.danarossa.entities.Course;
import com.danarossa.entities.RealizedCourse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RealizedCourseDao extends AbstractGenericDao<RealizedCourse, Long> implements IRealizedCourseDao {

    private static final String REALIZED_COURSE_NEXT_PRIMARY_KEY = "REALIZED_COURSE_NEXT_PRIMARY_KEY";
    private static final String REALIZED_COURSE_ID = "REALIZED_COURSE_ID";
    private static final String START_DATE = "START_DATE";
    private static final String COURSES_TABLE = "COURSES";
    private static final String END_DATE = "END_DATE";
    private static final String EXAM_DATE = "EXAM_DATE";
    private static final String STATUS = "STATUS";

    public RealizedCourseDao(OracleDaoFactory.OracleConnectionPool connectionPool) {
        super(connectionPool, "realized_course_sql.properties");
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


    @Override
    public List<RealizedCourse> getAllRealizedCoursesOfLecturer(Long lecturerId) {
        String sql = sqlQueries.getProperty("select.all.for.lecturer");
        return getFromQueryWithId(lecturerId,sql);
    }

    @Override
    public List<RealizedCourse> getAllRealizedCoursesOfStudent(Long studentId) {
        String sql = sqlQueries.getProperty("select.all.for.student");
        return getFromQueryWithId(studentId,sql);
    }

    @Override
    public List<RealizedCourse> getAllRealizedCoursesOfCourse(Long courseId) {
        String sql = sqlQueries.getProperty("select.all.for.course");
        return getFromQueryWithId(courseId,sql);
    }
}





