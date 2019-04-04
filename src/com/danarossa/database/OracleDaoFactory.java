package com.danarossa.database;

import com.danarossa.database.concretedao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class OracleDaoFactory implements AbstractDaoFactory {

    static private OracleConnectionPool oracleConnectionPool = new OracleConnectionPool();

    @Override
    public AccountDao getAccountDao() {
        return new AccountDao(oracleConnectionPool.getConnection());
    }

    @Override
    public CourseDao getCourseDao() {
        return new CourseDao(oracleConnectionPool.getConnection());
    }

    @Override
    public LecturerDao getLecturerDao() {
        return new LecturerDao(oracleConnectionPool.getConnection());
    }

    @Override
    public RealizedCourseDao getRealizedCourseDao() {
        return new RealizedCourseDao(oracleConnectionPool.getConnection());
    }

    @Override
    public StudentDao getStudentDao() {
        return new StudentDao(oracleConnectionPool.getConnection());
    }

    @Override
    public StudentMarkDao getStudentMarkDao() {
        return null;
    }

    @SuppressWarnings("FieldCanBeLocal")
    static class OracleConnectionPool {

        private final String DATABASE_URL = "jdbc:oracle:thin:@77.47.220.222:1521:XE";
        private final String DATABASE_USER = "my_servlet_db";
        private final String DATABASE_PASSWORD = "my_servlet_db";

        private LinkedList<Connection> connections = new LinkedList<>();

        private OracleConnectionPool() {
//            try {
//                this.connections.add(DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD));
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.exit(-1);
//            }
        }

        Connection getConnection() {
//            if(connections.size() >=1 ){
//                return connections.removeFirst();
//            }
//            else return null;
            try {
                return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
            return null;
        }

        void returnConnection(Connection connection) {
            connections.add(connection);
        }
    }

}
