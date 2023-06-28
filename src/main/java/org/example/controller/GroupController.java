package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.entity.Group;
import org.example.network.Response;
import org.example.network.Server;
import org.example.repository.GroupRepository;
import org.json.JSONException;
import org.json.JSONObject;

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

    public void create(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != 3) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        try {
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            JSONObject object = new JSONObject(new String(bytes));
            Group group = new Group(
                    object.getString("name"),
                    object.getString("description")
            );
            repository.create(group);
            Server.sendResponse(exchange, 201, "Group successfully created");
        } catch (JSONException e) {
            Server.sendResponse(exchange, 501, Response.JSON_FORMAT_ERROR);
        } catch (IOException e) {
            Server.sendResponse(exchange, 501, Response.READ_WRITE_ERROR);
        }
    }

    public void update(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != 4) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        try {
            String id = array[3];
            Group found = repository.find_by_name(id);
            if (found == null) {
                Server.sendResponse(exchange, 404, "No group with this name");
                return;
            }
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            JSONObject object = new JSONObject(new String(bytes));
            Group group = new Group(
                    object.getString("name"),
                    object.getString("description")
            );
            repository.update(id, group);
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