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
    private final String COURSES = "COURSES";
    private final String STUDENTS = "STUDENTS";
    private final String STUDENTS_COURSES = STUDENTS + "_COURSES";
    private final String STUDENT_COURSE_NEXT_PRIMARY_KEY = "STUDENT_COURSE_NEXT_PRIMARY_KEY";
    private final String MARK = "MARK";
    private final String COURSE_ID = "COURSE_ID";
    private final String STUDENT_COURSE_ID = "STUDENT_" + COURSE_ID;
    private final String STUDENT_ID = "STUDENT_ID";
    private final String REALIZED_COURSE_ID = "REALIZED_COURSE_ID";

    public StudentMarkDao(OracleDaoFactory.OracleConnectionPool connectionPool) {
        super(connectionPool);
    }

    private String getBasicSelectQuery() {
       return "select STUDENT_COURSE_ID, MARK,\n" +
               "       COURSE_ID, title, NUMBER_OF_CREDITS, NUMBER_OF_HOURS,\n" +
               "       HOURS_FOR_LECTURES, HOURS_FOR_PRACTICE, HOURS_FOR_HOME_STUDY,\n" +
               "       s.USER_ID, s.name, s.SURNAME, s.DATE_ENTERED,  s.password, s.email, s.role, \n" +
               "       l.USER_ID, l.name, l.SURNAME, l.DATE_ENTERED, l.role, l.password, l.email, \n" +
               "       REALIZED_COURSE_ID, r.START_DATE, r.END_DATE, r.EXAM_DATE, r.STATUS\n" +
               "from STUDENTS_COURSES join REALIZED_COURSES r using(REALIZED_COURSE_ID) join COURSES using (course_id)\n" +
               "join users l on courses.LECTURER_ID = l.USER_ID join users s on STUDENTs_COURSEs.student_id = s.USER_ID ";
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
                STUDENT_ID + ", " + REALIZED_COURSE_ID + ", " + MARK + ")" +
                "values ( ?, ?, ?, ?)";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from " + STUDENTS + "_" + COURSES + " where " + STUDENT_COURSE_ID + " = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + STUDENTS + "_" + COURSES + " set " + STUDENT_ID + " = ? , " +
                REALIZED_COURSE_ID + " = ?, " + MARK + " = ? where " + STUDENT_COURSE_ID + " = ?";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select " + STUDENTS_COURSES + "_SQ.nextval as " +
                STUDENT_COURSE_NEXT_PRIMARY_KEY + " from dual";
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
        User student = UserDao.parseUser(rs);
        RealizedCourse realizedCourse = RealizedCourseDao.parseRealizedCourse(rs);
        Double mark = rs.getDouble(MARK);
        return new StudentMark(id, student, realizedCourse, mark);
    }

    @Override
    public List<StudentMark> getStudentMarksForRealizedCourse(Long realizedCourseId) {
        String sql = getBasicSelectQuery() + "where " + REALIZED_COURSE_ID + " = ?";
        return getFromQueryWithId(realizedCourseId, sql);
    }

    @Override
    public List<StudentMark> getStudentMarksForStudent(Long studentId) {
        String sql = getBasicSelectQuery() + "where " + STUDENT_ID + " = ?";
        return getFromQueryWithId(studentId, sql);
    }
}
