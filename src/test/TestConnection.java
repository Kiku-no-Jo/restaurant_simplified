package test;

import dao.DataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestConnection {
    @Test
    public void testConnection() {
        Dotenv dotenv = Dotenv.load();

        String host = dotenv.get("DB_HOST");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        String database = dotenv.get("DB_NAME");

        System.out.println(host + ":" + user + ":" + password + ":" + database);
    }

    @Test
    public void testDatabaseConnection() {
        DataSource dataSource = new DataSource();

        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Database connection should not be null!");
            assertFalse(connection.isClosed(), "Database connection should be open!");
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }
}

