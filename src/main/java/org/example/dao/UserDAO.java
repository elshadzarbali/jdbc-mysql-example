package org.example.dao;

import org.example.entity.User;
import org.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // ************************************
    // ----------Create Operations---------
    // ************************************

    // adding User to database and retrieving it
    public User addUser(User user) {
        String query = "INSERT INTO users (username, password, email, join_date) VALUES (?, ?, ?, ?)";

        // closing Connection object means also closing Statement and ResultSet object, but it is good
        // practice to close every closeable objects explicitly.
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, user.getUsername());
            prepStatement.setString(2, user.getPassword());
            prepStatement.setString(3, user.getEmail());
            prepStatement.setDate(4, new Date(user.getJoinDate().getTime()));
            prepStatement.executeUpdate();

            // Retrieving generated ID and setting it to the user object
            try (ResultSet generatedKeys = prepStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
            return user;
        } catch (SQLException e) {
            DBUtil.handleSQLException(e);
        }
        return null;
    }

    // adding User to database
    public boolean addUser2(User user) {
        String query = "INSERT INTO users (username, password, email, join_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {

            prepStatement.setString(1, user.getUsername());
            prepStatement.setString(2, user.getPassword());
            prepStatement.setString(3, user.getEmail());
            prepStatement.setDate(4, new Date(user.getJoinDate().getTime()));
            if (prepStatement.executeUpdate() == 1) return true;
        } catch (SQLException e) {
            DBUtil.handleSQLException(e);
        }
        return false;
    }

    // ************************************
    // -----------Read Operations----------
    // ************************************

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = getUserFromResultSet(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            DBUtil.handleSQLException(e);
        }

        return users;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        User user = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setInt(1, id);

            try (ResultSet rs = prepStatement.executeQuery()) {
                if (rs.next()) {
                    user = getUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            DBUtil.handleSQLException(e);
        }
        return user;
    }

    // ************************************
    // ----------Update Operations---------
    // ************************************

    public boolean updateUser(User user) {
        String query = "UPDATE user SET username = ?, password = ?, email = ?, join_date = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setString(1, user.getUsername());
            prepStatement.setString(2, user.getPassword());
            prepStatement.setString(3, user.getEmail());
            prepStatement.setDate(4, new Date(user.getJoinDate().getTime()));
            prepStatement.setInt(5, user.getId());
            if (prepStatement.executeUpdate() == 1) return true;
        } catch (SQLException e) {
            DBUtil.handleSQLException(e);
        }
        return false;
    }

    // ************************************
    // ----------Delete Operations---------
    // ************************************

    public boolean deleteUserById(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setInt(1, id);
            if (prepStatement.executeUpdate() == 1) return true;
        } catch (SQLException e) {
            DBUtil.handleSQLException(e);
        }
        return false;
    }

    public boolean deleteAllUsers() {
        String query = "DELETE FROM users";
        try (Connection conn = DBUtil.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            DBUtil.handleSQLException(e);
        }
        return false;
    }

    // ************************************
    // -----------Helper Methods-----------
    // ************************************

    // helper method to create User according to given ResultSet object
    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setJoinDate(rs.getDate("join_date"));
        return user;
    }
}
