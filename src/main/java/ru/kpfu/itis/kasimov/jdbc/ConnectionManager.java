package ru.kpfu.itis.kasimov.jdbc;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static BlockingQueue<Connection> pool;
    private static final int DEFAULT_POOL_SIZE = 10;

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
            var proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close") ?
                            pool.add((Connection) proxy) :
                            method.invoke(connection, args)
            );
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
        String PROD_DB_HOST = System.getenv("PROD_DB_HOST");
        String PROD_DB_PORT = System.getenv("PROD_DB_PORT");
        String PROD_DB_PASSWORD = System.getenv("PROD_DB_PASSWORD");
        String PROD_DB_NAME = System.getenv("PROD_DB_NAME");
        String PROD_DB_USERNAME = System.getenv("PROD_DB_USERNAME");

        System.out.println("PROD_DB_HOST: " + PROD_DB_HOST);
        System.out.println("PROD_DB_PORT: " + PROD_DB_PORT);
        System.out.println("PROD_DB_NAME: " + PROD_DB_NAME);
        System.out.println("PROD_DB_USERNAME: " + PROD_DB_USERNAME);
        System.out.println("PROD_DB_PASSWORD: " + PROD_DB_PASSWORD);

        try {
            Connection connection = DriverManager.getConnection(
                    String.format("jdbc:postgresql://%s:%s/%s", PROD_DB_HOST, PROD_DB_PORT, PROD_DB_NAME),
                    PROD_DB_USERNAME,
                    PROD_DB_PASSWORD
            );
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish a connection to the database", e);
        }
    }

    private ConnectionManager() {
    }
}
