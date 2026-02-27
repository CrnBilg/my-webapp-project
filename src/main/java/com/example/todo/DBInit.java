package com.example.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInit {
    private static final String DB_URL = "jdbc:sqlite:todo.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, completed INTEGER NOT NULL)");
            }
            System.out.println("Initialized DB at: " + new java.io.File("todo.db").getAbsolutePath());
        } catch (SQLException e) {
            System.err.println("Failed to initialize DB: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
