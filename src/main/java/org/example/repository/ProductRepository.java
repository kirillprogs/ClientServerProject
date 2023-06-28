package org.example.repository;

import org.example.entity.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductRepository {

    private final Connection connection;
    private final PreparedStatement create_product;
    private final PreparedStatement update_product;
    private final PreparedStatement delete_product;
    private final PreparedStatement find_product_by_id;

    private final static String sql_create_product =
            "INSERT INTO products (name, group_name, description, amount, price) VALUES (?, ?, ?, ?, ?)";
    private final static String sql_update_product =
            "UPDATE products SET name=?, group_name=?, description=?, amount=?, price=? WHERE name=?";
    private final static String sql_delete_product =
            "DELETE FROM products WHERE name=?";
    private final static String sql_find_by_id =
            "SELECT * FROM products WHERE name=?";

    public ProductRepository(Connection connection) throws SQLException {
        this.connection = connection;
        create_product = connection.prepareStatement(sql_create_product);
        update_product = connection.prepareStatement(sql_update_product);
        delete_product = connection.prepareStatement(sql_delete_product);
        find_product_by_id = connection.prepareStatement(sql_find_by_id);
    }

    public void create(Product product) {
        try {
            create_product.setString(1, product.getName());
            create_product.setString(2, product.getGroup_name());
            create_product.setString(3, product.getDescription());
            create_product.setDouble(4, product.getAmount());
            create_product.setDouble(5, product.getPrice());
            create_product.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        try {
            delete_product.setString(1, name);
            delete_product.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String name, Product product) {
        try {
            update_product.setString(1, product.getName());
            update_product.setString(2, product.getGroup_name());
            update_product.setString(3, product.getDescription());
            update_product.setDouble(4, product.getAmount());
            update_product.setDouble(5, product.getPrice());
            update_product.setString(6, name);
            update_product.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product find_by_name(String name) {
        try {
            find_product_by_id.setString(1, name);
            ResultSet set = find_product_by_id.executeQuery();
            if (set.next())
                return new Product(
                        set.getString("name"),
                        set.getString("group_name"),
                        set.getString("description"),
                        set.getDouble("amount"),
                        set.getDouble("price")
                );
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> list_by_criteria(List<String> group_ids, String name_pattern,
                                          String description_pattern,
                                          double amount_lower, double amount_upper,
                                          double price_lower, double price_upper) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE TRUE");

            if (name_pattern != null)
                sql.append(" AND name LIKE '").append(name_pattern).append("'");
            if (description_pattern != null)
                sql.append(" AND description LIKE '").append(description_pattern).append("'");

            if (group_ids != null && !group_ids.isEmpty()) {
                sql.append(" AND group_id IN (");
                for (String group_id : group_ids)
                    sql.append("'").append(group_id).append("'").append(",");
                sql.deleteCharAt(sql.length() - 1);
                sql.append(")");
            }

            if (amount_lower >= 0 && amount_upper >= 0)
                sql.append(" AND amount BETWEEN ").append(amount_lower).append(" AND ").append(amount_upper);
            else if (amount_lower >= 0)
                sql.append(" AND amount >= ").append(amount_lower);
            else if (amount_upper >= 0)
                sql.append(" AND amount <= ").append(amount_upper);

            if (price_lower >= 0 && price_upper >= 0)
                sql.append(" AND price BETWEEN ").append(price_lower).append(" AND ").append(price_upper);
            else if (price_lower >= 0)
                sql.append(" AND price >= ").append(price_lower);
            else if (price_upper >= 0)
                sql.append(" AND price <= ").append(price_upper);

            Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery(sql.toString());
            List<Product> list = new LinkedList<>();
            while (set.next())
                list.add(new Product(
                        set.getString("name"),
                        set.getString("group_name"),
                        set.getString("description"),
                        set.getDouble("amount"),
                        set.getDouble("price")
                ));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}