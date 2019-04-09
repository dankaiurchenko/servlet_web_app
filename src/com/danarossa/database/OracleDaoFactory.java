package com.danarossa.database;

import com.danarossa.database.daointerfaces.ITransaction;
import com.danarossa.database.oracledao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OracleDaoFactory implements AbstractDaoFactory {

    static private final OracleConnectionPool oracleConnectionPool = new OracleConnectionPool();

    @Override
    public AccountDao getAccountDao() {
        return new AccountDao(oracleConnectionPool);
    }

    @Override
    public CourseDao getCourseDao() {
        return new CourseDao(oracleConnectionPool);
    }

    @Override
    public LecturerDao getLecturerDao() {
        return new LecturerDao(oracleConnectionPool);
    }

    @Override
    public RealizedCourseDao getRealizedCourseDao() {
        return new RealizedCourseDao(oracleConnectionPool);
    }

    @Override
    public StudentDao getStudentDao() {
        return new StudentDao(oracleConnectionPool);
    }

    @Override
    public StudentMarkDao getStudentMarkDao() {
        return new StudentMarkDao(oracleConnectionPool);
    }

    @Override
    public ITransaction getTransaction() {
        return new Transaction(oracleConnectionPool);
    }

    public void closeAllConnections(){
        try {
            oracleConnectionPool.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistException("Error while closing connections", e);
        }
    }

    @SuppressWarnings("FieldCanBeLocal")
    public static class OracleConnectionPool {
        private static final int MAX_POOL_SIZE = 30;
        //TODO
        private List<Connection> connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        private List<Connection> usedConnections = new ArrayList<>();
        private static int INITIAL_POOL_SIZE = 10;
        private final String DATABASE_URL = "jdbc:oracle:thin:@77.47.220.222:1521:XE";
        private final String DATABASE_USER = "my_servlet_db";
        private final String DATABASE_PASSWORD = "my_servlet_db";


        private OracleConnectionPool() {
            try {
                for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                    connectionPool.add(createConnection());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        private Connection createConnection() throws SQLException {
            return DriverManager.getConnection(DATABASE_URL,
                    DATABASE_USER, DATABASE_PASSWORD);
        }

        public Connection getConnection() {
            if (connectionPool.isEmpty()) {
                if (usedConnections.size() < MAX_POOL_SIZE) {
                    try {
                        connectionPool.add(createConnection());
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                } else {
                    throw new RuntimeException(
                            "Maximum pool size reached, no available connections!");
                }
            }

            Connection connection = connectionPool
                    .remove(connectionPool.size() - 1);
            usedConnections.add(connection);
            return connection;
        }

        public boolean releaseConnection(Connection connection) {
            connectionPool.add(connection);
            return usedConnections.remove(connection);
        }

        void shutdown() throws SQLException {
            usedConnections.forEach(this::releaseConnection);
            for (Connection c : connectionPool) {
                c.close();
            }
            connectionPool.clear();
        }
    }


}
