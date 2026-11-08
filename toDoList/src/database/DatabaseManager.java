package database;

import java.sql.*;

public class DatabaseManager {
    private static String url = "jdbc:sqlite:tasks.db";

    public static void setUrl(String newUrl) { url = newUrl; }   // lets tests use a temp DB

    public static Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(url);
        try (Statement st = c.createStatement()) { st.execute("PRAGMA foreign_keys = ON"); }
        return c;
    }

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
                    is_complete INTEGER NOT NULL DEFAULT 0)""");
        } catch (SQLException e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }
}
