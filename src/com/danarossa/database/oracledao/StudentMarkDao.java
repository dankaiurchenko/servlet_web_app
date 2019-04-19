package com.danarossa.database.oracledao;

import com.danarossa.database.OracleDaoFactory;
import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.IStudentMarkDao;
import com.danarossa.entities.RealizedCourse;
import com.danarossa.entities.StudentMark;
import com.danarossa.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentMarkDao extends AbstractGenericDao<StudentMark, Long> implements IStudentMarkDao {
    private final String STUDENTS = "STUDENTS";

    public StudentMarkDao(OracleDaoFactory.OracleConnectionPool connectionPool) {
        super(connectionPool, "student_mark_sql.properties");
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, StudentMark entity) throws SQLException {
        statement.setLong(1, entity.getId());
        setFields(statement, entity,2);
    }

    private void setFields(PreparedStatement statement, StudentMark entity, int startIndex) throws SQLException {
        statement.setLong(startIndex, entity.getStudentId());
        statement.setLong(startIndex + 1, entity.getRealizedCourseId());
        statement.setDouble(startIndex + 2, entity.getMark());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, StudentMark entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setLong(4, entity.getId());
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            String STUDENT_COURSE_NEXT_PRIMARY_KEY = "STUDENT_COURSE_NEXT_PRIMARY_KEY";
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
        String COURSE_ID = "COURSE_ID";
        String STUDENT_COURSE_ID = "STUDENT_" + COURSE_ID;
        Long id = rs.getLong(STUDENT_COURSE_ID);
        User student = UserDao.parseUser(rs);
        RealizedCourse realizedCourse = RealizedCourseDao.parseRealizedCourse(rs);
        String MARK = "MARK";
        Double mark = rs.getDouble(MARK);
        return new StudentMark(id, student, realizedCourse, mark);
    }

    @Override
    public List<StudentMark> getStudentMarksForRealizedCourse(Long realizedCourseId) {
        String sql = sqlQueries.getProperty("select.all.for.realized.course");
        return getFromQueryWithId(realizedCourseId, sql);
    }

    @Override
    public List<StudentMark> getStudentMarksForStudent(Long studentId) {
        String sql = sqlQueries.getProperty("select.all.for.student");
        return getFromQueryWithId(studentId, sql);
    }
}
