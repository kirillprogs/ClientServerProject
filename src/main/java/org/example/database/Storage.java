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
    //    private final StoreController storeController;
    private final GroupController groupController;
    private final ProductController productController;
    private final UserRepository userRepository;

    public Storage() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:storage.db");
//        Class.forName("org.postgresql.Driver");
//        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/store",
//                "user", "password");
        create_database();
//        StoreRepository storeRepository = new StoreRepository(connection);
        GroupRepository groupRepository = new GroupRepository(connection);
        ProductRepository productRepository = new ProductRepository(connection);
        this.userRepository = new UserRepository(connection);
//        storeController = new StoreController(storeRepository);
        groupController = new GroupController(groupRepository);
        productController = new ProductController(productRepository);
    }

    private void create_database() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "" +
                "CREATE TABLE IF NOT EXISTS groups (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    name VARCHAR NOT NULL," +
                "    description VARCHAR NOT NULL," +
                "    UNIQUE (name)" +
                ")";
        statement.execute(sql);
        sql = "" +
                "CREATE TABLE IF NOT EXISTS products (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    group_id INTEGER NOT NULL," +
                "    name VARCHAR NOT NULL," +
                "    description VARCHAR NOT NULL," +
                "    amount REAL NOT NULL," +
                "    price REAL NOT NULL," +
                "    FOREIGN KEY (group_id) REFERENCES groups (id)" +
                "        ON UPDATE CASCADE ON DELETE CASCADE," +
                "    UNIQUE (name)" +
                ")";
        statement.execute(sql);
        sql = "" +
                "CREATE TABLE IF NOT EXISTS users (" +
                "    name VARCHAR PRIMARY KEY NOT NULL," +
                "    password VARCHAR NOT NULL" +
                ")";
        statement.execute(sql);
        statement.close();
    }
}