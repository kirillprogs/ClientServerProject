package org.example.repository;

import org.example.entity.Group;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class GroupRepository {

    private final Connection connection;
    private final PreparedStatement create_group;
    private final PreparedStatement update_group;
    private final PreparedStatement delete_group;
    private final PreparedStatement find_group_by_id;

    private final static String sql_create_group =
            "INSERT INTO groups (name, description) VALUES (?, ?)";
    private final static String sql_update_group =
            "UPDATE groups SET name=?, description=? WHERE name=?";
    private final static String sql_delete_group =
            "DELETE FROM groups WHERE name=?";
    private final static String sql_find_by_id =
            "SELECT * FROM groups WHERE name=?";

    public GroupRepository(Connection connection) throws SQLException {
        this.connection = connection;
        create_group = connection.prepareStatement(sql_create_group);
        update_group = connection.prepareStatement(sql_update_group);
        delete_group = connection.prepareStatement(sql_delete_group);
        find_group_by_id = connection.prepareStatement(sql_find_by_id);
    }

    public void create(Group group) throws IllegalArgumentException {
        try {
            create_group.setString(1, group.getName());
            create_group.setString(2, group.getDescription());
            create_group.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        try {
            delete_group.setString(1, name);
            delete_group.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String name, Group group) {
        try {
            update_group.setString(1, group.getName());
            update_group.setString(2, group.getDescription());
            update_group.setString(3, name);
            update_group.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Group find_by_name(String name) {
        try {
            find_group_by_id.setString(1, name);
            ResultSet set = find_group_by_id.executeQuery();
            if (set.next())
                return new Group(
                        set.getString("name"),
                        set.getString("description")
                );
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Group> list_by_criteria(String name_pattern, String description_pattern) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM groups WHERE TRUE");

            if (name_pattern != null)
                sql.append(" AND name LIKE '").append(name_pattern).append("'");
            if (description_pattern != null)
                sql.append(" AND description LIKE '").append(description_pattern).append("'");

            Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery(sql.toString());
            List<Group> list = new LinkedList<>();
            while (set.next())
                list.add(new Group(
                        set.getString("name"),
                        set.getString("description")
                ));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}