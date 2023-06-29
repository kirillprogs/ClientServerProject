package org.example.client;

import org.example.entity.Product;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    void groupTest() throws Exception {
        HttpAccessor accessor = new HttpAccessor();
        accessor.login("jerry", "abba");
        accessor.findGroupId("Meat");
        accessor.createGroup("Home", "Utensils");
        accessor.updateGroup("Home", "Home", "Utensils, cutlery and crockery");
        accessor.deleteGroup("Home");
    }

    @Test
    void storeTest() {

    }

    @Test
    void valueTest() {

    }

    @Test
    void searchTest() {

    }
}