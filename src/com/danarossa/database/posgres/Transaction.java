package com.danarossa.database.posgres;

import com.danarossa.database.PersistException;
import com.danarossa.database.PostgresDabFactory;
import com.danarossa.database.daointerfaces.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction implements ITransaction, AutoCloseable{

    private final Connection connection;
    private final PostgresDabFactory.PostgresConnectionPool connectionPool;
    private CourseDao courseDao = null;
    private UserDao userDao = null;
    private RealizedCourseDao realizedCourseDao = null;
    private StudentMarkDao studentMarkDao = null;

    public Transaction(PostgresDabFactory.PostgresConnectionPool connectionPool) {
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
    public ICourseDao getCourseDao() {
        return null;
    }

    @Override
    public IUserDao getUserDao() {
        return null;
    }

    @Override
    public IRealizedCourseDao getRealizedCourseDao() {
        return null;
    }

    @Override
    public IStudentMarkDao getStudentMarkDao() {
        return null;
    }
}
