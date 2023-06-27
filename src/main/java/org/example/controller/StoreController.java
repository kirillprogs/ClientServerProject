package org.example.controller;

import org.example.repository.StoreRepository;

public class StoreController {

    private final StoreRepository storeRepository;

    public StoreController(StoreRepository repository) {
        this.storeRepository = repository;
    }
}