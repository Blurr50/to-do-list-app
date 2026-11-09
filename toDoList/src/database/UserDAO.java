package database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;
import model.User;

public class UserDAO {

    /** Joins the team with this invite code (creating it if new). False if username is taken. */
    public boolean register(String username, String password, String inviteCode) {
        try (Connection c = DatabaseManager.getConnection()) {
            c.setAutoCommit(false);
            try {
                int teamId = findOrCreateTeam(c, inviteCode);
                String salt = newSalt();
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO users(username, password_hash, salt, team_id) VALUES(?,?,?,?)")) {
                    ps.setString(1, username);
                    ps.setString(2, hash(password, salt));
                    ps.setString(3, salt);
                    ps.setInt(4, teamId);
                    ps.executeUpdate();
                }
                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                if (e.getMessage().contains("UNIQUE")) return false;
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Returns the logged-in User, or null if the credentials are wrong. */
    public User login(String username, String password) {
        String sql = """
            SELECT u.id, u.password_hash, u.salt, u.team_id, t.invite_code
            FROM users u JOIN teams t ON t.id = u.team_id WHERE u.username = ?""";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getString("password_hash").equals(hash(password, rs.getString("salt")))) {
                    return new User(rs.getInt("id"), username, rs.getInt("team_id"), rs.getString("invite_code"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean inviteCodeExists(String code) {
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT 1 FROM teams WHERE invite_code = ?")) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findOrCreateTeam(Connection c, String code) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("SELECT id FROM teams WHERE invite_code = ?")) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        }
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT INTO teams(invite_code) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, code);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { keys.next(); return keys.getInt(1); }
        }
    }

    private static String newSalt() {
        byte[] b = new byte[16];
        new SecureRandom().nextBytes(b);
        return Base64.getEncoder().encodeToString(b);
    }

    private static String hash(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(md.digest(password.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}