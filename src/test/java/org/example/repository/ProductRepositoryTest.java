package org.example.repository;

import org.example.database.Storage;
import org.example.entity.Group;
import org.example.entity.Product;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

class ProductRepositoryTest {

    @Test
    void test() throws SQLException, ClassNotFoundException {
        File file = new File("storage.db");
        if (file.delete())
            System.out.println("Database file removed");
        Storage storage = new Storage();
        GroupRepository groupRepository = new GroupRepository(storage.getConnection());
        groupRepository.create(new Group("Sea products", "Taken from deeps"));
        ProductRepository repository = new ProductRepository(storage.getConnection());
        repository.create(new Product("Fish", "Sea products", 1.5, 150.00));
        repository.create(new Product("Apple", "Sea products", 1.5, 150.00));
        repository.delete("Fish");
        repository.update("Apple",
                new Product("Meat", "Sea products", "Fresh chicken", 0.5, 2.5));
        System.out.println(repository.find_by_name("Meat"));
    }
}