package database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import model.User;

public class UserDAO {

    public enum RegisterResult { OK, USERNAME_TAKEN, NO_SUCH_TEAM, CODE_TAKEN }

    // letters and digits that are easy to read out loud (no 0/O or 1/I)
    private static final String CODE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /** Creates an account on an EXISTING team (the invite code must belong to a team). */
    public RegisterResult registerAndJoin(String username, String password, String inviteCode) {
        String code = normalize(inviteCode);
        try (Connection c = DatabaseManager.getConnection()) {
            Integer teamId = findTeam(c, code);
            if (teamId == null) return RegisterResult.NO_SUCH_TEAM;
            return insertUser(c, username, password, teamId) ? RegisterResult.OK : RegisterResult.USERNAME_TAKEN;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Creates a brand-new team with this invite code, plus the account of its first member. */
    public RegisterResult registerNewTeam(String username, String password, String inviteCode) {
        String code = normalize(inviteCode);
        try (Connection c = DatabaseManager.getConnection()) {
            c.setAutoCommit(false);
            try {
                if (usernameTaken(c, username)) return RegisterResult.USERNAME_TAKEN;
                if (findTeam(c, code) != null) return RegisterResult.CODE_TAKEN;
                int teamId;
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO teams(invite_code) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, code);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) { keys.next(); teamId = keys.getInt(1); }
                }
                if (!insertUser(c, username, password, teamId)) {
                    c.rollback();
                    return RegisterResult.USERNAME_TAKEN;
                }
                c.commit();
                return RegisterResult.OK;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Returns the logged-in User, or null if the credentials are wrong. Usernames ignore upper/lower case. */
    public User login(String username, String password) {
        String sql = """
            SELECT u.id, u.username, u.password_hash, u.salt, u.team_id, t.invite_code
            FROM users u JOIN teams t ON t.id = u.team_id WHERE u.username = ? COLLATE NOCASE""";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username == null ? "" : username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getString("password_hash").equals(hash(password, rs.getString("salt")))) {
                    return new User(rs.getInt("id"), rs.getString("username"), rs.getInt("team_id"),
                            rs.getString("invite_code"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /** Everyone on the team (including the caller). The first member (lowest id) is the team creator. */
    public List<User> getTeamMembers(int teamId) {
        String sql = """
            SELECT u.id, u.username, u.team_id, t.invite_code
            FROM users u JOIN teams t ON t.id = u.team_id
            WHERE u.team_id = ? ORDER BY u.id""";
        List<User> members = new ArrayList<>();
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    members.add(new User(rs.getInt("id"), rs.getString("username"),
                            rs.getInt("team_id"), rs.getString("invite_code")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }

    public boolean inviteCodeExists(String code) {
        try (Connection c = DatabaseManager.getConnection()) {
            return findTeam(c, normalize(code)) != null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** A new random 6-character invite code that no team uses yet. */
    public String generateInviteCode() {
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) sb.append(CODE_CHARS.charAt(RANDOM.nextInt(CODE_CHARS.length())));
            if (!inviteCodeExists(sb.toString())) return sb.toString();
        }
    }

    private static String normalize(String code) {
        return code == null ? "" : code.trim().toUpperCase();
    }

    private Integer findTeam(Connection c, String code) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("SELECT id FROM teams WHERE invite_code = ?")) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? rs.getInt(1) : null; }
        }
    }

    private boolean usernameTaken(Connection c, String username) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("SELECT 1 FROM users WHERE username = ? COLLATE NOCASE")) {
            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    /** Returns false if the username is already taken. */
    private boolean insertUser(Connection c, String username, String password, int teamId) throws SQLException {
        if (usernameTaken(c, username)) return false;
        String salt = newSalt();
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT INTO users(username, password_hash, salt, team_id) VALUES(?,?,?,?)")) {
            ps.setString(1, username.trim());
            ps.setString(2, hash(password, salt));
            ps.setString(3, salt);
            ps.setInt(4, teamId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE")) return false;
            throw e;
        }
    }

    private static String newSalt() {
        byte[] b = new byte[16];
        RANDOM.nextBytes(b);
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
