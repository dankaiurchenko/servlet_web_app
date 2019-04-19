package com.danarossa.database.oracledao;

import com.danarossa.database.OracleDaoFactory;
import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.ITransaction;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction implements ITransaction, AutoCloseable{

    private final Connection connection;
    private OracleDaoFactory.OracleConnectionPool connectionPool;
    private CourseDao courseDao = null;
    private UserDao userDao = null;
    private RealizedCourseDao realizedCourseDao = null;
    private UserDao studentDao = null;
    private StudentMarkDao studentMarkDao = null;

    public Transaction(OracleDaoFactory.OracleConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.connection = this.connectionPool.getConnection();
    }

    @Override
    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new PersistException("Cannot start transaction!");
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new PersistException("Error while commit");
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new PersistException("Error while rollback");
        }
    }


    @Override
    public CourseDao getCourseDao() {
        if (courseDao == null) {
            courseDao = new CourseDao(connectionPool);
        }
        return courseDao;
    }

    @Override
    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao(connectionPool);
        }
        return userDao;
    }

    @Override
    public RealizedCourseDao getRealizedCourseDao() {
        if (realizedCourseDao == null) {
            realizedCourseDao = new RealizedCourseDao(connectionPool);
        }
        return realizedCourseDao;
    }


    @Override
    public StudentMarkDao getStudentMarkDao() {
        if (studentMarkDao == null) {
            studentMarkDao = new StudentMarkDao(connectionPool);
        }
        return studentMarkDao;
    }


    @Override
    public void close() throws Exception {
        connectionPool.releaseConnection(connection);
    }
}
