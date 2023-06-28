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
        repository.create(new User("charlie", "ababa"));
        repository.create(new User("george", "31415"));
        System.out.println(repository.find_by_id(1));
        System.out.println(repository.find_by_id(2));
        repository.delete(2);
        System.out.println(repository.find_by_id(1));
        System.out.println(repository.find_by_id(2));
        repository.update(1, new User("jerry", "abba"));
        System.out.println(repository.find_by_id(1));
        System.out.println(repository.find_by_id(2));
    }
}