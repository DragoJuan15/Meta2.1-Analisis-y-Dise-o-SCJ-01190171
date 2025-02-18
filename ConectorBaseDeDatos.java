package com.example.meta1_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorBaseDeDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/personas";
    private static final String USER = "root";
    private static final String PASSWORD = "Marciano15.";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}