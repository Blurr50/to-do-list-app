package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Task;

public class TaskDAO {

    public int addTask(String title, String description, int createdBy, Integer assignedTo, int teamId) {
        String sql = "INSERT INTO tasks(title, description, created_by, assigned_to, team_id) VALUES(?,?,?,?,?)";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, createdBy);
            if (assignedTo == null) ps.setNull(4, Types.INTEGER); else ps.setInt(4, assignedTo);
            ps.setInt(5, teamId);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { return keys.next() ? keys.getInt(1) : -1; }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Tasks created by or assigned to this user. */
    public List<Task> getTasksForUser(int userId) {
        String sql = """
            SELECT id, title, description, created_by, assigned_to, is_complete
            FROM tasks WHERE created_by = ? OR assigned_to = ? ORDER BY is_complete, id""";
        List<Task> tasks = new ArrayList<>();
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int assigned = rs.getInt("assigned_to");
                    tasks.add(new Task(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
                            rs.getInt("created_by"), rs.wasNull() ? null : assigned,
                            rs.getInt("is_complete") == 1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public boolean setComplete(int taskId, boolean done) {
        return exec("UPDATE tasks SET is_complete = ? WHERE id = ?", done ? 1 : 0, taskId);
    }

    public boolean assign(int taskId, int userId) {
        return exec("UPDATE tasks SET assigned_to = ? WHERE id = ?", userId, taskId);
    }

    public boolean deleteTask(int taskId) {
        return exec("DELETE FROM tasks WHERE id = ?", taskId);
    }

    private boolean exec(String sql, int... params) {
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setInt(i + 1, params[i]);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

