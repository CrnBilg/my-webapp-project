package com.example.todo;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Task entity.
 */
public class TaskDAO {

    private static final String DB_PATH = new File("todo.db").getAbsolutePath();
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    static {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to register SQLite driver", e);
        }
    }

    public TaskDAO() {
        init();
    }

    private void init() {
        String createTableSql =
                "CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "completed INTEGER NOT NULL)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSql);

        } catch (SQLException e) {
            throw new IllegalStateException("Error initializing database", e);
        }
    }

    public List<Task> findAll() {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT id, title, completed FROM tasks ORDER BY id";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("completed") != 0
                );
                list.add(task);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Error retrieving tasks", e);
        }

        return list;
    }

    public Task findById(int id) {
        String sql = "SELECT id, title, completed FROM tasks WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Task(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getInt("completed") != 0
                    );
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Error finding task by id", e);
        }

        return null;
    }

    public Task create(Task task) {
        String sql = "INSERT INTO tasks(title, completed) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, task.getTitle());
            ps.setInt(2, task.isCompleted() ? 1 : 0);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    task.setId(keys.getInt(1));
                }
            }

            return task;

        } catch (SQLException e) {
            throw new IllegalStateException("Error creating task", e);
        }
    }

    public boolean update(Task task) {
        String sql = "UPDATE tasks SET title = ?, completed = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, task.getTitle());
            ps.setInt(2, task.isCompleted() ? 1 : 0);
            ps.setInt(3, task.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new IllegalStateException("Error updating task", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new IllegalStateException("Error deleting task", e);
        }
    }
}