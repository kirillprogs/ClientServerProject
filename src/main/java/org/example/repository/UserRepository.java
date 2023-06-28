package org.example.repository;

import org.example.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private final PreparedStatement create_user;
    private final PreparedStatement update_user;
    private final PreparedStatement delete_user;
    private final PreparedStatement find_user_by_name;

    private final static String sql_create_user =
            "INSERT INTO users (name, password) VALUES (?, ?)";
    private final static String sql_update_user =
            "UPDATE users SET name=?, password=? WHERE name=?";
    private final static String sql_delete_user =
            "DELETE FROM users WHERE name=?";
    private final static String sql_find_by_id =
            "SELECT * FROM users WHERE name=?";

    public UserRepository(Connection connection) throws SQLException {
        create_user = connection.prepareStatement(sql_create_user);
        update_user = connection.prepareStatement(sql_update_user);
        delete_user = connection.prepareStatement(sql_delete_user);
        find_user_by_name = connection.prepareStatement(sql_find_by_id);
    }

    public void create(User user) {
        try {
            create_user.setString(1, user.getName());
            create_user.setString(2, user.getPassword());
            create_user.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        try {
            delete_user.setString(1, name);
            delete_user.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String name, User user) {
        try {
            update_user.setString(1, user.getName());
            update_user.setString(2, user.getPassword());
            update_user.setString(3, name);
            update_user.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User find_by_name(String name) {
        try {
            find_user_by_name.setString(1, name);
            ResultSet set = find_user_by_name.executeQuery();
            if (set.next())
                return new User(
                        set.getString("name"),
                        set.getString("password")
                );
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}