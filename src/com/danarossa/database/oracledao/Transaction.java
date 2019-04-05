package com.danarossa.database.oracledao;

import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.ITransaction;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction implements ITransaction, AutoCloseable{

    private final Connection connection;

    private AccountDao accountDao = null;
    private CourseDao courseDao = null;
    private LecturerDao lecturerDao = null;
    private RealizedCourseDao realizedCourseDao = null;
    private StudentDao studentDao = null;
    private StudentMarkDao studentMarkDao = null;

    public Transaction(Connection connection) {
        this.connection = connection;
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
            accountDao = new AccountDao(connection);
        }
        return accountDao;
    }

    @Override
    public CourseDao getCourseDao() {
        if (courseDao == null) {
            courseDao = new CourseDao(connection);
        }
        return courseDao;
    }

    @Override
    public LecturerDao getLecturerDao() {
        if (lecturerDao == null) {
            lecturerDao = new LecturerDao(connection);
        }
        return lecturerDao;
    }

    @Override
    public RealizedCourseDao getRealizedCourseDao() {
        if (realizedCourseDao == null) {
            realizedCourseDao = new RealizedCourseDao(connection);
        }
        return realizedCourseDao;
    }

    @Override
    public StudentDao getStudentDao() {
        if (studentDao == null) {
            studentDao = new StudentDao(connection);
        }
        return studentDao;
    }

    @Override
    public StudentMarkDao getStudentMarkDao() {
        if (studentMarkDao == null) {
            studentDao = new StudentDao(connection);
        }
        return studentMarkDao;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
