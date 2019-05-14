package com.danarossa.database.posgres;

import com.danarossa.database.PersistException;
import com.danarossa.database.PostgresDabFactory;
import com.danarossa.database.daointerfaces.IStudentMarkDao;
import com.danarossa.entities.StudentMark;
import com.danarossa.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentMarkDao extends AbstractGenericDao<StudentMark, Integer> implements IStudentMarkDao {
    private final String STUDENTS = "STUDENTS";

    public StudentMarkDao(PostgresDabFactory.PostgresConnectionPool connectionPool) {
        super(connectionPool, "student_mark_sql.properties");
    }

    @Override
    protected void setId(PreparedStatement statement, Integer id) throws SQLException {
        statement.setInt(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, StudentMark entity) throws SQLException {
        setFields(statement, entity, 1);
    }

    private void setFields(PreparedStatement statement, StudentMark entity, int startIndex) throws SQLException {
        statement.setInt(startIndex, entity.getStudentId());
        statement.setInt(startIndex + 1, entity.getRealizedCourseId());
        statement.setDouble(startIndex + 2, entity.getMark());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, StudentMark entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setInt(4, entity.getId());
    }

    @Override
    protected Integer parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getInt("student_course_id");
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
        String COURSE_ID = "COURSE_ID";
        String STUDENT_COURSE_ID = "STUDENT_" + COURSE_ID;
        Integer id = rs.getInt(STUDENT_COURSE_ID);
        User student = UserDao.parseUser(rs);
        Integer realizedCourseId = rs.getInt("realized_course_id");
        Double mark = rs.getDouble("MARK");
        return new StudentMark(id, student, realizedCourseId, mark);
    }

    @Override
    public List<StudentMark> getStudentMarksForRealizedCourse(Integer realizedCourseId) {
        String sql = sqlQueries.getProperty("select.all.for.realized.course");
        return getFromQueryWithId(realizedCourseId, sql);
    }

    @Override
    public List<StudentMark> getStudentMarksForStudent(Integer studentId) {
        String sql = sqlQueries.getProperty("select.all.for.student");
        return getFromQueryWithId(studentId, sql);
    }
}
