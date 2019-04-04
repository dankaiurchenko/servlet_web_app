package com.danarossa.database.concretedao;

import com.danarossa.database.daointerfaces.IStudentMarkDao;
import com.danarossa.entities.StudentMark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentMarkDao extends AbstractGenericDao<StudentMark, Long> implements IStudentMarkDao {
    public StudentMarkDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectByIdQuery() {
        return null;
    }

    @Override
    protected String getSelectQuery() {
        return null;
    }

    @Override
    protected String getInsertQuery() {
        return null;
    }

    @Override
    protected String getDeleteQuery() {
        return null;
    }

    @Override
    protected String getUpdateQuery() {
        return null;
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return null;
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, StudentMark entity) throws SQLException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, StudentMark entity) throws SQLException {

    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected List<StudentMark> parseResultSet(ResultSet rs) throws SQLException {
        return null;
    }

}
