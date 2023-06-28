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
    private final PreparedStatement find_user_by_id;

    private final static String sql_create_user =
            "INSERT INTO users (name, password) VALUES (?, ?)";
    private final static String sql_update_user =
            "UPDATE users SET name=?, password=? WHERE id=?";
    private final static String sql_delete_user =
            "DELETE FROM users WHERE id=?";
    private final static String sql_find_by_id =
            "SELECT * FROM users WHERE id=?";

    public UserRepository(Connection connection) throws SQLException {
        create_user = connection.prepareStatement(sql_create_user);
        update_user = connection.prepareStatement(sql_update_user);
        delete_user = connection.prepareStatement(sql_delete_user);
        find_user_by_id = connection.prepareStatement(sql_find_by_id);
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

    public void delete(int id) {
        try {
            delete_user.setInt(1, id);
            delete_user.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, User user) {
        try {
            update_user.setString(1, user.getName());
            update_user.setString(2, user.getPassword());
            update_user.setInt(3, id);
            update_user.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User find_by_id(int id) {
        try {
            find_user_by_id.setInt(1, id);
            ResultSet set = find_user_by_id.executeQuery();
            if (set.next())
                return new User(
                        id,
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