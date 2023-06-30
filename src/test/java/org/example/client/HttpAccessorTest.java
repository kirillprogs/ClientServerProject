package org.example.client;

import org.example.entity.Group;
import org.example.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class HttpAccessorTest {

    @Test
    void productTest() throws Exception {
        HttpAccessor accessor = new HttpAccessor();
        accessor.login("jerry", "abba");
        accessor.findProductId("Beef");
//        accessor.findProductId("be");
//        accessor.findProductId("bebe/abe/");
        accessor.createProduct("Chicken1", "Meat", "Yet yesterday", 19, 145.2);
        System.out.println(accessor.findProductId("Chicken"));
        accessor.updateProduct("Chicken1", "Turkey", "Meat", "Yet yesterday", 19, 145.2);
        System.out.println(accessor.findProductId("Turkey"));
        accessor.deleteProduct("Turkey");
        try {
            System.out.println(accessor.findProductId("Turkey"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (Product product : accessor.allProducts(null))
            System.out.println(product);
        System.out.println(accessor.findProductId("Beef"));
        accessor.change("Beef", 4);
        System.out.println(accessor.findProductId("Beef"));
        accessor.change("Beef", -3);
        System.out.println(accessor.findProductId("Beef"));
    }

    @Test
    void groupTest() throws Exception {
        HttpAccessor accessor = new HttpAccessor();
        accessor.login("jerry", "abba");
        accessor.findGroupId("Meat");
        accessor.createGroup("Home", "Utensils");
        accessor.updateGroup("Home", "Home", "Utensils, cutlery and crockery");
        accessor.deleteGroup("Home");
        for (Group group : accessor.allGroups())
            System.out.println(group);
    }

    @Test
    void storeTest() throws Exception {
        HttpAccessor accessor = new HttpAccessor();
        accessor.login("jerry", "abba");
        System.out.println(accessor.value("Meat")); // sum in Meat
        System.out.println(accessor.value(null));   // sum in store
    }

    @Test
    void searchTest() throws Exception {
        HttpAccessor accessor = new HttpAccessor();
        accessor.login("jerry", "abba");
        List<String> group_ids = new LinkedList<>();
        group_ids.add("Meat");
        for (Product product : accessor.search(group_ids, "Po%", "%"
                , -1, -1, -1, -1))
            System.out.println(product);
    }
}