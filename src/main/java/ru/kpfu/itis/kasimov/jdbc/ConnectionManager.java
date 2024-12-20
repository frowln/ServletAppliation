package ru.kpfu.itis.kasimov.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;


public class ConnectionManager {
    private static DataSource dataSource;

    static {
        try {
            Properties baseProperties = new Properties();
            try (InputStream baseInput = ConnectionManager.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (baseInput == null) {
                    throw new RuntimeException("Base configuration file not found: application.properties");
                }
                baseProperties.load(baseInput);
            }

            String activeProfile = baseProperties.getProperty("active.profile", "dev");
            String profileFile = "application-" + activeProfile + ".properties";

            Properties profileProperties = new Properties();
            try (InputStream profileInput = ConnectionManager.class.getClassLoader().getResourceAsStream(profileFile)) {
                if (profileInput == null) {
                    throw new RuntimeException("Profile configuration file not found: " + profileFile);
                }
                profileProperties.load(profileInput);
            }

            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setURL(profileProperties.getProperty("database.url"));
            ds.setUser(profileProperties.getProperty("database.username"));
            ds.setPassword(profileProperties.getProperty("database.password"));

            dataSource = ds;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}