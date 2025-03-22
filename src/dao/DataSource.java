package dao;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    Dotenv dotenv = Dotenv.load();
    private final static int defaultPort = 5432;
    private final String host = dotenv.get("DB_HOST");
    private final String user = dotenv.get("DB_USER");
    private final String password = dotenv.get("DB_PASSWORD");
    private final String database = dotenv.get("DB_NAME");
    private final String jdbcUrl;

    public DataSource() {
        jdbcUrl = "jdbc:postgresql://" + host + ":" + defaultPort + "/" + database;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}