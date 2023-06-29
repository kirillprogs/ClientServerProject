package org.example.database;

import org.example.entity.Group;
import org.example.entity.Product;
import org.example.repository.GroupRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

class StorageTest {
    @Test
    void test() throws SQLException, ClassNotFoundException {
        File file = new File("storage.db");
        if (file.delete())
            System.out.println("Database file removed");
        Storage storage = new Storage();
        ProductRepository productRepository = new ProductRepository(storage.getConnection());
        GroupRepository groupRepository = new GroupRepository(storage.getConnection());
        groupRepository.create(new Group("Meat", "All meat products in there"));
        groupRepository.create(new Group("Fish", "All fish products in there"));
        groupRepository.create(new Group("Fruit", "Fresh and juicy fruits"));
        productRepository.create(new Product("Poultry", "Meat", "Yet yesterday it was running", 5, 146.3));
        productRepository.create(new Product("Beef", "Meat", "Very tasty if well roasted", 3, 315.2));
        productRepository.create(new Product("Pork", "Meat", "Good for soup boiling", 4, 213.384));
        productRepository.create(new Product("Tuna", "Fish", "Canned", 2, 69.7));
        productRepository.create(new Product("Apple", "Fruit", "Beautiful red", 28, 2.0));
        productRepository.create(new Product("Melon", "Fruit", "Wonderful scent", 7, 45.7));
    }
}