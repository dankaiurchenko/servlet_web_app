package com.danarossa.database;

import com.danarossa.database.daointerfaces.ITransaction;
import com.danarossa.database.posgres.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresDabFactory implements AbstractDaoFactory {

    static private final PostgresConnectionPool POSTGRES_CONNECTION_POOL = new PostgresConnectionPool();
    @Override
    public CourseDao getCourseDao() {
        return new CourseDao(POSTGRES_CONNECTION_POOL);
    }

    @Override
    public UserDao getUserDao() {
        return new UserDao(POSTGRES_CONNECTION_POOL);
    }

    @Override
    public RealizedCourseDao getRealizedCourseDao() {
        return new RealizedCourseDao(POSTGRES_CONNECTION_POOL);
    }

    @Override
    public StudentMarkDao getStudentMarkDao() {
        return new StudentMarkDao(POSTGRES_CONNECTION_POOL);
    }

    public ITransaction getTransaction() {
        return new Transaction(POSTGRES_CONNECTION_POOL);
    }

    public void closeAllConnections(){
        try {
            POSTGRES_CONNECTION_POOL.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistException("Error while closing connections", e);
        }
    }

    @SuppressWarnings("FieldCanBeLocal")
    public static class PostgresConnectionPool {
        private static final int MAX_POOL_SIZE = 30;
        //TODO
        private final List<Connection> connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        private final List<Connection> usedConnections = new ArrayList<>();
        private static final int INITIAL_POOL_SIZE = 10;
        private final String DATABASE_URL = "jdbc:postgresql://localhost:5432/my_servlet_project";
        private final String DATABASE_USER = "postgres";
        private final String DATABASE_PASSWORD = "djljghjdjl26";


        private PostgresConnectionPool() {
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

        public void releaseConnection(Connection connection) {
            connectionPool.add(connection);
            usedConnections.remove(connection);
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
