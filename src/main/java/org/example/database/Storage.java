package org.example.database;

import lombok.Getter;
import org.example.controller.GroupController;
import org.example.controller.ProductController;
import org.example.controller.StoreController;
import org.example.repository.GroupRepository;
import org.example.repository.ProductRepository;
import org.example.repository.StoreRepository;
import org.example.repository.UserRepository;

import java.sql.*;

@Getter
public class Storage {

    private final Connection connection;
    private final StoreController storeController;
    private final GroupController groupController;
    private final ProductController productController;
    private final UserRepository userRepository;

    public Storage() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:storage.db");
        create_database();
        StoreRepository storeRepository = new StoreRepository(connection);
        GroupRepository groupRepository = new GroupRepository(connection);
        ProductRepository productRepository = new ProductRepository(connection);
        this.userRepository = new UserRepository(connection);
        storeController = new StoreController(storeRepository);
        groupController = new GroupController(groupRepository);
        productController = new ProductController(productRepository, groupRepository);
    }

    private void create_database() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "PRAGMA foreign_keys=ON";
        statement.execute(sql);
        sql = "" +
                "CREATE TABLE IF NOT EXISTS groups (" +
                "    name        VARCHAR NOT NULL," +
                "    description VARCHAR NOT NULL," +
                "    PRIMARY KEY (name)" +
                ")";
        statement.execute(sql);
        sql = "" +
                "CREATE TABLE IF NOT EXISTS products (" +
                "    name        VARCHAR NOT NULL," +
                "    group_name  INTEGER NOT NULL," +
                "    description VARCHAR NOT NULL," +
                "    amount      REAL    NOT NULL," +
                "    price       REAL    NOT NULL," +
                "    PRIMARY KEY (name)," +
                "    FOREIGN KEY (group_name) REFERENCES groups (name)" +
                "        ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        statement.execute(sql);
        sql = "" +
                "CREATE TABLE IF NOT EXISTS users (" +
                "    name VARCHAR NOT NULL," +
                "    role VARCHAR NULL," +
                "    password VARCHAR NOT NULL," +
                "    PRIMARY KEY (name)" +
                ")";
        statement.execute(sql);
        statement.close();
    }
}