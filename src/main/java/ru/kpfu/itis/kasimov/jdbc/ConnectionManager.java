package ru.kpfu.itis.kasimov.jdbc;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static final int DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;

    static {
        loadDriver();
        initConnectionPool();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConnectionPool() {
        int size = DEFAULT_POOL_SIZE;
        pool = new ArrayBlockingQueue<>(size);

        for (int i = 0; i < size; i++) {
            Connection connection = open();
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close") ?
                            pool.add((Connection) proxy) :
                            method.invoke(connection, args));
            pool.add(proxyConnection);
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection open() {
        String host = System.getenv("PROD_DB_HOST");
        String port = System.getenv("PROD_DB_PORT");
        String dbName = System.getenv("PROD_DB_NAME");
        String username = System.getenv("PROD_DB_USERNAME");
        String password = System.getenv("PROD_DB_PASSWORD");

        if (host == null || port == null || dbName == null || username == null || password == null) {
            throw new RuntimeException("One or more required environment variables are missing for database connection");
        }

        try {
            String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish a database connection", e);
        }
    }

    private ConnectionManager() {
    }
}
