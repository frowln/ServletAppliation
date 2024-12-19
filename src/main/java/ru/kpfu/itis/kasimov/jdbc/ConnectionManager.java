package ru.kpfu.itis.kasimov.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection getConnection() {
        String PROD_DB_HOST = System.getenv("PROD_DB_HOST");
        String PROD_DB_PORT = System.getenv("PROD_DB_PORT");
        String PROD_DB_PASSWORD = System.getenv("PROD_DB_PASSWORD");
        String PROD_DB_NAME = System.getenv("PROD_DB_NAME");
        String PROD_DB_USERNAME = System.getenv("PROD_DB_USERNAME");

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://%s:%s/%s".formatted(PROD_DB_HOST, PROD_DB_PORT, PROD_DB_NAME),
                    PROD_DB_USERNAME,
                    PROD_DB_PASSWORD
            );
            System.out.println("Соединение с базой данных установлено!");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка подключения к базе данных!");
            System.err.println("PROD_DB_HOST: " + PROD_DB_HOST);
            System.err.println("PROD_DB_PORT: " + PROD_DB_PORT);
            System.err.println("PROD_DB_PASSWORD: " + PROD_DB_PASSWORD);
            System.err.println("PROD_DB_NAME: " + PROD_DB_NAME);
            System.err.println("PROD_DB_USERNAME: " + PROD_DB_USERNAME);
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
        // Закрытый конструктор, чтобы предотвратить создание экземпляра класса
    }
}
