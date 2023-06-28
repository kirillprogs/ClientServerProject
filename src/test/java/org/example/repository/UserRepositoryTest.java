package org.example.repository;

import org.example.database.Storage;
import org.example.entity.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

class UserRepositoryTest {
    @Test
    void test() throws SQLException, ClassNotFoundException {
        File file = new File("storage.db");
        if (file.delete())
            System.out.println("Database file removed");
        Storage storage = new Storage();
        UserRepository repository = new UserRepository(storage.getConnection());
        repository.create(new User("charlie", "manager", "ababa"));
        repository.create(new User("george", "customer", "31415"));
        System.out.println(repository.find_by_name("charlie"));
        System.out.println(repository.find_by_name("george"));
        repository.delete("george");
        System.out.println(repository.find_by_name("charlie"));
        System.out.println(repository.find_by_name("george"));
        repository.update("charlie", new User("jerry", "customer", "abba"));
        System.out.println(repository.find_by_name("jerry"));
        System.out.println(repository.find_by_name("charlie"));
    }
}