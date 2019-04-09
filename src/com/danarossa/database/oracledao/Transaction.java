package com.danarossa.database.oracledao;

import com.danarossa.database.OracleDaoFactory;
import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.ITransaction;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction implements ITransaction, AutoCloseable{

    private final Connection connection;
    private OracleDaoFactory.OracleConnectionPool connectionPool;

    private AccountDao accountDao = null;
    private CourseDao courseDao = null;
    private LecturerDao lecturerDao = null;
    private RealizedCourseDao realizedCourseDao = null;
    private StudentDao studentDao = null;
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
    public AccountDao getAccountDao() {
        if(accountDao == null){
            accountDao = new AccountDao(connectionPool);
        }
        return accountDao;
    }

    @Override
    public CourseDao getCourseDao() {
        if (courseDao == null) {
            courseDao = new CourseDao(connectionPool);
        }
        return courseDao;
    }

    @Override
    public LecturerDao getLecturerDao() {
        if (lecturerDao == null) {
            lecturerDao = new LecturerDao(connectionPool);
        }
        return lecturerDao;
    }

    @Override
    public RealizedCourseDao getRealizedCourseDao() {
        if (realizedCourseDao == null) {
            realizedCourseDao = new RealizedCourseDao(connectionPool);
        }
        return realizedCourseDao;
    }

    @Override
    public StudentDao getStudentDao() {
        if (studentDao == null) {
            studentDao = new StudentDao(connectionPool);
        }
        return studentDao;
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
