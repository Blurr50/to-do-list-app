package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static String url = "jdbc:sqlite:tasks.db";

    public static void setUrl(String newUrl) { url = newUrl; }   // lets tests use a temp DB

    public static Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(url);
        try (Statement st = c.createStatement()) { st.execute("PRAGMA foreign_keys = ON"); }
        return c;
    }

    /** Creates the tables on first run, and upgrades databases made by older versions of the app. */
    public static void initialize() {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS teams (
                    id          INTEGER PRIMARY KEY AUTOINCREMENT,
                    invite_code TEXT NOT NULL UNIQUE)""");
            st.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id            INTEGER PRIMARY KEY AUTOINCREMENT,
                    username      TEXT NOT NULL UNIQUE,
                    password_hash TEXT NOT NULL,
                    salt          TEXT NOT NULL,
                    team_id       INTEGER NOT NULL REFERENCES teams(id))""");
            st.execute("""
                CREATE TABLE IF NOT EXISTS tasks (
                    id          INTEGER PRIMARY KEY AUTOINCREMENT,
                    title       TEXT NOT NULL,
                    description TEXT,
                    created_by  INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                    assigned_to INTEGER REFERENCES users(id) ON DELETE SET NULL,
                    team_id     INTEGER NOT NULL REFERENCES teams(id),
                    status      TEXT NOT NULL DEFAULT 'TODO',
                    due_date    TEXT,
                    priority    INTEGER NOT NULL DEFAULT 2)""");

            // Older databases: add the columns that did not exist yet.
            boolean statusAdded = addColumnIfMissing(c, "tasks", "status", "TEXT NOT NULL DEFAULT 'TODO'");
            addColumnIfMissing(c, "tasks", "due_date", "TEXT");
            addColumnIfMissing(c, "tasks", "priority", "INTEGER NOT NULL DEFAULT 2");
            // The old is_complete flag becomes the DONE status (only once, when status is first added).
            if (statusAdded && hasColumn(c, "tasks", "is_complete")) {
                st.execute("UPDATE tasks SET status = 'DONE' WHERE is_complete = 1");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    private static boolean hasColumn(Connection c, String table, String column) throws SQLException {
        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("PRAGMA table_info(" + table + ")")) {
            while (rs.next()) {
                if (column.equalsIgnoreCase(rs.getString("name"))) return true;
            }
        }
        return false;
    }

    /** Returns true if the column had to be added. */
    private static boolean addColumnIfMissing(Connection c, String table, String column, String definition)
            throws SQLException {
        if (hasColumn(c, table, column)) return false;
        try (Statement st = c.createStatement()) {
            st.execute("ALTER TABLE " + table + " ADD COLUMN " + column + " " + definition);
        }
        return true;
    }
}
