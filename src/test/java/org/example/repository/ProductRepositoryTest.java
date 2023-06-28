package org.example.repository;

import org.example.database.Storage;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
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
        ProductRepository repository = new ProductRepository(storage.getConnection());
        repository.create(new Product("Fish", 1.5, 150.00));
        repository.delete(2);
        repository.update(5, new Product(-1, 5, "Meat", "Fresh chicken", 0.5, 2.5));
        System.out.println(repository.find_by_id(5));
    }
}