package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.repository.StoreRepository;

public class StoreController {

    private final StoreRepository storeRepository;

    public StoreController(StoreRepository repository) {
        this.storeRepository = repository;
    }

    public void storeValue(HttpExchange exchange) {

    }

    public void groupValue(HttpExchange exchange) {

    }
}