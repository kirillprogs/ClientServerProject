package org.example.controller;

import org.example.repository.GroupRepository;

public class GroupController {

    private GroupRepository repository;

    public GroupController(GroupRepository repository) {
        this.repository = repository;
    }
}