package com.danarossa.database.concretedao;

import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.ICourseDao;
import com.danarossa.entities.Course;
import com.danarossa.entities.Lecturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao extends AbstractGenericDao<Course, Long> implements ICourseDao {


    private final String course_id = "COURSE_ID";
    private final String title = "TITLE";
    private final String number_of_credits = "NUMBER_OF_CREDITS";
    private final String number_of_hours = "NUMBER_OF_HOURS";
    private final String hours_for_lectures = "HOURS_FOR_LECTURES";
    private final String hours_for_practice = "HOURS_FOR_PRACTICE";
    private final String hours_for_home_study = "HOURS_FOR_HOME_STUDY";
    private final String lecturer_id = "LECTURER_ID";
    private final String coursesTable = "COURSES";
    private final String course_next_primary_key = "course_next_primary_key";

    public CourseDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectByIdQuery() {
        return getBasicSelectQuery() + " where " + course_id + " = 1;";
    }

    private String getBasicSelectQuery() {
        String name = "NAME";
        String surname = "SURNAME";
        String birthday = "BIRTHDAY";
        String position = "POSITION";
        String hireDate = "HIRE_DATE";
        String lecturersTable = "LECTURERS";
        return "select " + course_id + ", " + title + ", " + number_of_credits + ", " + number_of_hours + ", " + hours_for_lectures + ", " + hours_for_practice + ",\n" +
                "       " + hours_for_home_study + ", " + lecturer_id + ",  " + name + ", " + surname + ", " + birthday + ", " + position + ", " + hireDate + ", " + "\n" +
                "from " + coursesTable + " join " + lecturersTable + " using (" + lecturer_id + ")";
    }

    @Override
    protected String getSelectQuery() {
        return getBasicSelectQuery() + " ;";
    }

    @Override
    protected String getInsertQuery() {
        return "insert into courses(" + course_id + ", " + title + ", " + number_of_credits + ", " + number_of_hours + ", " +
                hours_for_lectures + ", " +
                hours_for_practice + ",\n " +
                hours_for_home_study + ", " + lecturer_id + ")\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);\n";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete  from " + coursesTable + " where " + course_id + " = ?;";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + coursesTable + " set " + title + " =?, " + number_of_credits + " = ? , " + number_of_hours + " = ?, " + hours_for_lectures + " = ?, " + hours_for_practice + " = ? ,\n" +
                hours_for_home_study + " = ?, " + lecturer_id + " = ? where " + course_id + " = ?;";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select " + coursesTable + "_SQ.nextval as " + course_next_primary_key + " from dual;";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Course entity) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getTitle());
        statement.setInt(3, entity.getNumberOfCredits());
        statement.setInt(4, entity.getNumberOfHours());
        statement.setInt(5, entity.getHoursForLectures());
        statement.setInt(6, entity.getHoursForPractice());
        statement.setInt(7, entity.getHoursForHomeStudy());
        statement.setLong(8, entity.getLecturerId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Course entity) throws SQLException {
        statement.setString(1, entity.getTitle());
        statement.setInt(2, entity.getNumberOfCredits());
        statement.setInt(3, entity.getNumberOfHours());
        statement.setInt(4, entity.getHoursForLectures());
        statement.setInt(5, entity.getHoursForPractice());
        statement.setInt(6, entity.getHoursForHomeStudy());
        statement.setLong(7, entity.getLecturerId());
        statement.setLong(7, entity.getId());
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(course_next_primary_key);
        } else throw new PersistException("No value returned!");
    }

    @Override
    protected List<Course> parseResultSet(ResultSet rs) throws SQLException {
        List<Course> list = new ArrayList<>();
        while (rs.next()) {
            Long course_id = rs.getLong(this.course_id);
            String title = rs.getString(this.title);
            int number_of_credits = rs.getInt(this.number_of_credits);
            int number_of_hours = rs.getInt(this.number_of_hours);
            int hours_for_lectures = rs.getInt(this.hours_for_lectures);
            int hours_for_practice = rs.getInt(this.hours_for_practice);
            int hours_for_home_study = rs.getInt(this.hours_for_home_study);
            Lecturer lecturer = LecturerDao.parseLecturer(rs);
            Course course = new Course(course_id, title, number_of_credits, number_of_hours,hours_for_lectures,
                    hours_for_practice, hours_for_home_study, lecturer);
            list.add(course);
        }
        return list;
    }

}
