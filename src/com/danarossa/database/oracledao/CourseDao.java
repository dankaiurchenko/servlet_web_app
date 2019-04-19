package com.danarossa.database.oracledao;

import com.danarossa.database.OracleDaoFactory;
import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.ICourseDao;
import com.danarossa.entities.Course;
import com.danarossa.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao extends AbstractGenericDao<Course, Long> implements ICourseDao {

    private static final String COURSE_ID = "COURSE_ID";
    private static final String TITLE = "TITLE";
    private static final String NUMBER_OF_CREDITS = "NUMBER_OF_CREDITS";
    private static final String NUMBER_OF_HOURS = "NUMBER_OF_HOURS";
    private static final String HOURS_FOR_LECTURES = "HOURS_FOR_LECTURES";
    private static final String HOURS_FOR_PRACTICE = "HOURS_FOR_PRACTICE";
    private static final String HOURS_FOR_HOME_STUDY = "HOURS_FOR_HOME_STUDY";
    private static final String COURSE_NEXT_PRIMARY_KEY = "COURSE_NEXT_PRIMARY_KEY";

    public CourseDao(OracleDaoFactory.OracleConnectionPool connectionPool) {
        super(connectionPool, "course_sql.properties");
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Course entity) throws SQLException {
        statement.setLong(1, entity.getId());
        setFields(statement, entity, 2);
    }

    private void setFields(PreparedStatement statement, Course entity, int startIndex) throws SQLException {
        statement.setString(startIndex, entity.getTitle());
        statement.setInt(startIndex + 1, entity.getNumberOfCredits());
        statement.setInt(startIndex + 2, entity.getNumberOfHours());
        statement.setInt(startIndex + 3, entity.getHoursForLectures());
        statement.setInt(startIndex + 4, entity.getHoursForPractice());
        statement.setInt(startIndex + 5, entity.getHoursForHomeStudy());
        statement.setLong(startIndex + 6, entity.getLecturerId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Course entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setLong(8, entity.getId());
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(COURSE_NEXT_PRIMARY_KEY);
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
        Long course_id = rs.getLong(COURSE_ID);
        String title = rs.getString(TITLE);
        int number_of_credits = rs.getInt(NUMBER_OF_CREDITS);
        int number_of_hours = rs.getInt(NUMBER_OF_HOURS);
        int hours_for_lectures = rs.getInt(HOURS_FOR_LECTURES);
        int hours_for_practice = rs.getInt(HOURS_FOR_PRACTICE);
        int hours_for_home_study = rs.getInt(HOURS_FOR_HOME_STUDY);
        User lecturer = UserDao.parseUser(rs);
        return new Course(course_id, title, number_of_credits,
                number_of_hours, hours_for_lectures,
                hours_for_practice, hours_for_home_study, lecturer);
    }

    @Override
    public List<Course> getAllCoursesOfLecturer(Long lecturerId) {
        String sql = sqlQueries.getProperty("select.courses.of.lecturer");
        return getFromQueryWithId(lecturerId, sql);
    }

    @Override
    public List<Course> getAllCoursesOfStudent(Long studentId) {
        String sql = sqlQueries.getProperty("select.courses.of.student");
        return getFromQueryWithId(studentId, sql);
    }

}
