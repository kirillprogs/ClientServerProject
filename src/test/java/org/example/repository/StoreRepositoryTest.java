package org.example.repository;

import org.example.database.Storage;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class StoreRepositoryTest {

    @Test
    void test() throws SQLException, ClassNotFoundException {
        Storage storage = new Storage();
        StoreRepository repository = new StoreRepository(storage.getConnection());
        System.out.println(repository.all_price());
        System.out.println(repository.all_price_group("Fruit"));
    }
}