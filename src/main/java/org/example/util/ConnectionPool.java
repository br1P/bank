package org.example.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/bank2");
        config.setUsername("root"); //need to hide
        config.setPassword("12345678"); //need to hide
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public static DataSource getDataSource() {
        return dataSource;
    }
    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}