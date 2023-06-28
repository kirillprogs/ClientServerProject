package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreRepository {

    private final Connection connection;
    private final PreparedStatement all_price;
    private final PreparedStatement all_price_group;

    private final static String sql_all_price =
            "SELECT SUM(amount * price) AS value FROM products";
    private final static String sql_all_price_group =
            "SELECT SUM(amount * price) AS value FROM products WHERE group_name=?";

    public StoreRepository(Connection connection) throws SQLException {
        this.connection = connection;
        all_price = connection.prepareStatement(sql_all_price);
        all_price_group = connection.prepareStatement(sql_all_price_group);
    }

    public double all_price() {
        try {
            ResultSet value = all_price.executeQuery();
            if (value.next())
                return Double.parseDouble(value.getString("value"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public double all_price_group(String name) {
        try {
            all_price_group.setString(1, name);
            ResultSet value = all_price_group.executeQuery();
            if (value.next())
                return Double.parseDouble(value.getString("value"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}