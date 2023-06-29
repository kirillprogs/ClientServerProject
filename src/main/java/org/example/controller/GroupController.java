package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.entity.Group;
import org.example.network.Response;
import org.example.network.Server;
import org.example.repository.GroupRepository;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class GroupController {

    private final GroupRepository repository;

    public GroupController(GroupRepository repository) {
        this.repository = repository;
    }

    public void findId(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != 4) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        String name = array[3];
        Group group = repository.find_by_name(name);
        if (group == null)
            Server.sendResponse(exchange, 404, Response.INCORRECT_DATA_REQUESTED);
        else {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            Server.sendResponse(exchange, 200, group.toJSON().toString());
        }
    }

    public void set(HttpExchange exchange, boolean change) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != (change ? 4 : 3)) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        try {
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            Group group = new Group(new String(bytes));
            Group found = repository.find_by_name(group.getName());
            if (found != null) {
                if (!change || !found.getName().equals(array[3])) {
                    Server.sendResponse(exchange, 401, "Group with the same name already exists");
                }
            }
            if (change)
                repository.update(array[3], group);
            else
                repository.create(group);
            Server.sendResponse(exchange, 204);
        } catch (JSONException e) {
            Server.sendResponse(exchange, 501, Response.JSON_FORMAT_ERROR);
        } catch (IOException e) {
            Server.sendResponse(exchange, 501, Response.READ_WRITE_ERROR);
        }
    }

    public void delete(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != 4) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        String name = array[3];
        Group group = repository.find_by_name(name);
        if (group == null)
            Server.sendResponse(exchange, 404, Response.INCORRECT_DATA_REQUESTED);
        else {
            repository.delete(name);
            Server.sendResponse(exchange, 204);
        }
    }

    public void getAll(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != 4) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        List<Group> list = repository.list_by_criteria("%", null);
        if (list == null) {
            Server.sendResponse(exchange, 501, Response.SQL_ERROR);
            return;
        }
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        Server.sendResponse(exchange, 200, ControllerUtilities.groupListToJSON(list).toString());
    }
}