package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoreRepository {

    private final Connection connection;
    private final PreparedStatement all_products;
    private final PreparedStatement all_groups;
    private final PreparedStatement all_group_products;
    private final PreparedStatement all_price;
    private final PreparedStatement all_group_price;

    private final static String sql_all_products =
            "";
    private final static String sql_all_groups =
            "";
    private final static String sql_all_products_group =
            "";
    private final static String sql_all_price =
            "";
    private final static String sql_all_price_group =
            "";

    public StoreRepository(Connection connection) throws SQLException {
        this.connection = connection;
        all_products = connection.prepareStatement(sql_all_products);
        all_groups = connection.prepareStatement(sql_all_groups);
        all_group_products = connection.prepareStatement(sql_all_products_group);
        all_price = connection.prepareStatement(sql_all_price);
        all_group_price = connection.prepareStatement(sql_all_price_group);
    }
}