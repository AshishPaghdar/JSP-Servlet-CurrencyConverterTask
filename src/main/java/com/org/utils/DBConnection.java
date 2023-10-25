package com.org.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConnection {
    private static Connection con;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (con != null) {
            return con;
        }

        Properties properties = new Properties();
        try {
            // Load the properties file from the resources directory
            properties.load(new FileInputStream("src/main/resources/properties/app.properties"));

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return con;
    }
}
