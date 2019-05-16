package com.danarossa.database.posgres;

import com.danarossa.database.PersistException;
import com.danarossa.database.PostgresDabFactory;
import com.danarossa.entities.RealizedCourse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RealizedCourseDaoPostgres extends AbstractGenericDao<RealizedCourse, Integer> implements com.danarossa.database.daointerfaces.RealizedCourseDao {


    public RealizedCourseDaoPostgres(PostgresDabFactory.PostgresConnectionPool connectionPool) {
        super(connectionPool, "realized_course_sql.properties");
    }

    @Override
    protected void setId(PreparedStatement statement, Integer id) throws SQLException {
        statement.setInt(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, RealizedCourse entity) throws SQLException {
        setFields(statement, entity, 1);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, RealizedCourse entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setInt(6, entity.getId());
    }

    private void setFields(PreparedStatement statement, RealizedCourse entity, int startIndex) throws SQLException {
        statement.setInt(startIndex, entity.getCourseId());
        statement.setTimestamp(startIndex + 1, new Timestamp(entity.getStartDate().getTime()));
        statement.setTimestamp(startIndex + 2, new Timestamp(entity.getEndDate().getTime()));
        statement.setTimestamp(startIndex + 3, new Timestamp(entity.getExamDate().getTime()));
        statement.setString(startIndex + 4, entity.getStatus());
    }

    @Override
    protected Integer parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getInt("realized_course_id");
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
        Integer id = rs.getInt("realized_course_id");
        Integer courseId = rs.getInt("course_id");
        Date startDate = rs.getTimestamp("start_date");
        Date endDate = rs.getTimestamp("END_DATE");
        Date examDate = rs.getTimestamp("EXAM_DATE");
        String status = rs.getString("STATUS");
        return new RealizedCourse(id, courseId, startDate, endDate, examDate, status);
    }


    @Override
    public List<RealizedCourse> getAllRealizedCoursesOfLecturer(Integer lecturerId) {
        String sql = sqlQueries.getProperty("select.all.for.lecturer");
        return getFromQueryWithId(lecturerId,sql);
    }

    @Override
    public List<RealizedCourse> getAllRealizedCoursesOfStudent(Integer studentId) {
        String sql = sqlQueries.getProperty("select.all.for.student");
        return getFromQueryWithId(studentId,sql);
    }

    @Override
    public List<RealizedCourse> getAllRealizedCoursesOfCourse(Integer courseId) {
        String sql = sqlQueries.getProperty("select.all.for.course");
        return getFromQueryWithId(courseId,sql);
    }

}





