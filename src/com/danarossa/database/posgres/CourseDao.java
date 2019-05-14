package com.danarossa.database.posgres;

import com.danarossa.database.PersistException;
import com.danarossa.database.PostgresDabFactory;
import com.danarossa.database.daointerfaces.ICourseDao;
import com.danarossa.entities.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao extends AbstractGenericDao<Course, Integer> implements ICourseDao {


    public CourseDao(PostgresDabFactory.PostgresConnectionPool connectionPool) {
        super(connectionPool, "course_sql.properties");
    }

    @Override
    protected void setId(PreparedStatement statement, Integer id) throws SQLException {
        statement.setInt(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Course entity) throws SQLException {
        setFields(statement, entity, 1);
    }

    private void setFields(PreparedStatement statement, Course entity, int startIndex) throws SQLException {
        statement.setString(startIndex, entity.getTitle());
        statement.setInt(startIndex + 1, entity.getNumberOfHours());
        statement.setInt(startIndex + 2, entity.getHoursForLectures());
        statement.setInt(startIndex + 3, entity.getHoursForPractice());
        statement.setInt(startIndex + 4, entity.getLecturerId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Course entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setInt(6, entity.getId());
    }

    @Override
    protected Integer parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getInt("course_id");
        } else throw new PersistException("No value returned!");
    }

    @Override
    protected List<Course> parseResultSet(ResultSet rs) throws SQLException {
        List<Course> list = new ArrayList<>();
        while (rs.next()) {
            Course course = parseCourse(rs);
            list.add(course);
        }
        return list;
    }

    static Course parseCourse(ResultSet rs) throws SQLException {
        Integer course_id = rs.getInt("COURSE_ID");
        String title = rs.getString("TITLE");
        Integer number_of_hours = rs.getInt("NUMBER_OF_HOURS");
        Integer hours_for_lectures = rs.getInt("HOURS_FOR_LECTURES");
        Integer hours_for_practice = rs.getInt("HOURS_FOR_PRACTICE");
        Integer lecturerId = rs.getInt("LECTURER_ID");
        return new Course(course_id, title,
                number_of_hours, hours_for_lectures,
                hours_for_practice, lecturerId);
    }

    @Override
    public List<Course> getAllCoursesOfLecturer(Integer lecturerId) {
        String sql = sqlQueries.getProperty("select.courses.of.lecturer");
        return getFromQueryWithId(lecturerId, sql);
    }

    @Override
    public List<Course> getAllCoursesOfStudent(Integer studentId) {
        String sql = sqlQueries.getProperty("select.courses.of.student");
        return getFromQueryWithId(studentId, sql);
    }

}
