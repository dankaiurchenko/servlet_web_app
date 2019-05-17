package com.danarossa.database.posgres;

import com.danarossa.database.PersistException;
import com.danarossa.database.PostgresDabFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionPostgres implements com.danarossa.database.daointerfaces.Transaction, AutoCloseable{

    private final Connection connection;
    private final PostgresDabFactory.PostgresConnectionPool connectionPool;
    private CourseDaoPostgres courseDaoPostgres = null;
    private UserDaoPostgres userDaoPostgres = null;
    private RealizedCourseDaoPostgres realizedCourseDaoPostgres = null;
    private StudentMarkDaoPostgres studentMarkDaoPostgres = null;

    public TransactionPostgres(PostgresDabFactory.PostgresConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.connection = this.connectionPool.getConnection();
    }

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new PersistException("Cannot start transaction!");
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new PersistException("Error while commit");
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new PersistException("Error while rollback");
        }
    }


    @Override
    public void close() {
        connectionPool.releaseConnection(connection);
    }

    @Override
    public com.danarossa.database.daointerfaces.CourseDao getCourseDao() {
        return null;
    }

    @Override
    public com.danarossa.database.daointerfaces.UserDao getUserDao() {
        return null;
    }

    @Override
    public com.danarossa.database.daointerfaces.RealizedCourseDao getRealizedCourseDao() {
        return null;
    }

    @Override
    public com.danarossa.database.daointerfaces.StudentMarkDao getStudentMarkDao() {
        return null;
    }
}
