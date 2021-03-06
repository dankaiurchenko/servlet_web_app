package com.danarossa.database;

import com.danarossa.database.daointerfaces.Transaction;
import com.danarossa.database.posgres.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresDabFactory implements AbstractDaoFactory {

    static private final PostgresConnectionPool POSTGRES_CONNECTION_POOL = new PostgresConnectionPool();
    @Override
    public CourseDaoPostgres getCourseDao() {
        return new CourseDaoPostgres(POSTGRES_CONNECTION_POOL);
    }

    @Override
    public UserDaoPostgres getUserDao() {
        return new UserDaoPostgres(POSTGRES_CONNECTION_POOL);
    }

    @Override
    public RealizedCourseDaoPostgres getRealizedCourseDao() {
        return new RealizedCourseDaoPostgres(POSTGRES_CONNECTION_POOL);
    }

    @Override
    public StudentMarkDaoPostgres getStudentMarkDao() {
        return new StudentMarkDaoPostgres(POSTGRES_CONNECTION_POOL);
    }

    public Transaction getTransaction() {
        return new TransactionPostgres(POSTGRES_CONNECTION_POOL);
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
        private static final int INITIAL_POOL_SIZE = 20;
        private final List<Connection> connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        private final List<Connection> usedConnections = new ArrayList<>();
        private final String DATABASE_URL = "jdbc:postgresql://localhost:5432/my_servlet_project";
        private final String DATABASE_USER = "postgres";
        private final String DATABASE_PASSWORD = "djljghjdjl26";


        private PostgresConnectionPool() {
            try {
                for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                    connectionPool.add(createConnection());
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        private Connection createConnection() throws SQLException, ClassNotFoundException {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DATABASE_URL,
                    DATABASE_USER, DATABASE_PASSWORD);
        }

        public Connection getConnection() {
            if (connectionPool.isEmpty()) {
                if (usedConnections.size() < MAX_POOL_SIZE) {
                    try {
                        connectionPool.add(createConnection());
                    } catch (SQLException | ClassNotFoundException e) {
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
