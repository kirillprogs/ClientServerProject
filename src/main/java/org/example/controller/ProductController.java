package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.repository.ProductRepository;

public class ProductController {

    private ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    public void findId(HttpExchange exchange) {
        System.out.println("here find");
    }

    public void create(HttpExchange exchange) {
        System.out.println("here create");
    }

    public void update(HttpExchange exchange) {
        System.out.println("here update");
    }

    public void delete(HttpExchange exchange) {
        System.out.println("here delete");
    }
}