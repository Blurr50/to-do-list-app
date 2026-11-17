package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Task;

public class TaskDAO {

    private static final String SELECT = """
        SELECT t.id, t.title, t.description, t.created_by, cu.username AS creator,
               t.assigned_to, au.username AS assignee, t.status, t.due_date, t.priority
        FROM tasks t
        JOIN users cu ON cu.id = t.created_by
        LEFT JOIN users au ON au.id = t.assigned_to
        """;

    /** Creates a task. Returns the new id, or -1 if the assignee is not on the given team. */
    public int addTask(String title, String description, int createdBy, Integer assignedTo, int teamId,
                       String dueDate, int priority, String status) {
        String sql = """
            INSERT INTO tasks(title, description, created_by, assigned_to, team_id, due_date, priority, status)
            VALUES(?,?,?,?,?,?,?,?)""";
        try (Connection c = DatabaseManager.getConnection()) {
            if (assignedTo != null && !isOnTeam(c, assignedTo, teamId)) return -1;
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, title);
                ps.setString(2, description);
                ps.setInt(3, createdBy);
                setNullableInt(ps, 4, assignedTo);
                ps.setInt(5, teamId);
                ps.setString(6, dueDate);
                ps.setInt(7, priority);
                ps.setString(8, status);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) { return keys.next() ? keys.getInt(1) : -1; }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Tasks created by OR assigned to this user ("My tasks"). */
    public List<Task> getTasksForUser(int userId) {
        return query(SELECT + "WHERE t.created_by = ? OR t.assigned_to = ? ORDER BY t.id", userId, userId);
    }

    /** Every task of the team ("Team to-do list"). */
    public List<Task> getTasksForTeam(int teamId) {
        return query(SELECT + "WHERE t.team_id = ? ORDER BY t.id", teamId);
    }

    /** The creator or the assignee may change a task's status (e.g. tick it off). */
    public boolean setStatus(int taskId, int userId, String status) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ? AND (created_by = ? OR assigned_to = ?)";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, taskId);
            ps.setInt(3, userId);
            ps.setInt(4, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Only the creator may edit. The assignee must be on the task's team. */
    public boolean editTask(int taskId, int userId, String title, String description, Integer assignedTo,
                            String dueDate, int priority, String status) {
        String sql = """
            UPDATE tasks SET title = ?, description = ?, assigned_to = ?, due_date = ?, priority = ?, status = ?
            WHERE id = ?""";
        try (Connection c = DatabaseManager.getConnection()) {
            if (!isCreator(c, taskId, userId)) return false;
            if (assignedTo != null && !assigneeFitsTask(c, taskId, assignedTo)) return false;
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.setString(2, description);
                setNullableInt(ps, 3, assignedTo);
                ps.setString(4, dueDate);
                ps.setInt(5, priority);
                ps.setString(6, status);
                ps.setInt(7, taskId);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Only the creator may assign. assigneeId = null means unassigned; the assignee must be on the team. */
    public boolean assign(int taskId, int userId, Integer assigneeId) {
        try (Connection c = DatabaseManager.getConnection()) {
            if (!isCreator(c, taskId, userId)) return false;
            if (assigneeId != null && !assigneeFitsTask(c, taskId, assigneeId)) return false;
            try (PreparedStatement ps = c.prepareStatement("UPDATE tasks SET assigned_to = ? WHERE id = ?")) {
                setNullableInt(ps, 1, assigneeId);
                ps.setInt(2, taskId);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Only the creator may delete. */
    public boolean deleteTask(int taskId, int userId) {
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM tasks WHERE id = ? AND created_by = ?")) {
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Task> query(String sql, int... params) {
        List<Task> tasks = new ArrayList<>();
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setInt(i + 1, params[i]);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int assigned = rs.getInt("assigned_to");
                    Integer assignedTo = rs.wasNull() ? null : assigned;   // check right after the read
                    tasks.add(new Task(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
                            rs.getInt("created_by"), rs.getString("creator"),
                            assignedTo, rs.getString("assignee"),
                            rs.getString("status"), rs.getString("due_date"), rs.getInt("priority")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    private boolean isCreator(Connection c, int taskId, int userId) throws SQLException {
        return exists(c, "SELECT 1 FROM tasks WHERE id = ? AND created_by = ?", taskId, userId);
    }

    private boolean assigneeFitsTask(Connection c, int taskId, int userId) throws SQLException {
        return exists(c, "SELECT 1 FROM tasks t JOIN users u ON u.team_id = t.team_id WHERE t.id = ? AND u.id = ?",
                taskId, userId);
    }

    private boolean isOnTeam(Connection c, int userId, int teamId) throws SQLException {
        return exists(c, "SELECT 1 FROM users WHERE id = ? AND team_id = ?", userId, teamId);
    }

    private boolean exists(Connection c, String sql, int a, int b) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, a);
            ps.setInt(2, b);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    private static void setNullableInt(PreparedStatement ps, int index, Integer value) throws SQLException {
        if (value == null) ps.setNull(index, Types.INTEGER); else ps.setInt(index, value);
    }
}

