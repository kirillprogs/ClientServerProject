package org.example.repository;

import org.example.database.Storage;
import org.example.entity.Group;
import org.example.repository.GroupRepository;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

class GroupRepositoryTest {

    @Test
    void test() throws SQLException, ClassNotFoundException {
        File file = new File("storage.db");
        if (file.delete())
            System.out.println("Database file removed");
        Storage storage = new Storage();
        GroupRepository repository = new GroupRepository(storage.getConnection());
        repository.create(new Group("Meat"));
        repository.create(new Group("Fish"));
        List<Group> list = repository.list_by_criteria("%", null);
        for (Group group : list)
            System.out.println(group);
    }
}