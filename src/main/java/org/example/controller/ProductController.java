package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.entity.Product;
import org.example.network.Response;
import org.example.network.Server;
import org.example.repository.ProductRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
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
        Product product = repository.find_by_name(name);
        if (product == null)
            Server.sendResponse(exchange, 404, Response.INCORRECT_DATA_REQUESTED);
        else {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            Server.sendResponse(exchange, 200, product.toJSON().toString());
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
            Product product = new Product(
                    object.getString("name"),
                    object.getString("group_name"),
                    object.getString("description"),
                    object.getDouble("amount"),
                    object.getDouble("price")
            );
            repository.create(product);
            Server.sendResponse(exchange, 201, "");
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
            Product found = repository.find_by_name(id);
            if (found == null) {
                Server.sendResponse(exchange, 404, "No product with this name");
                return;
            }
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            JSONObject object = new JSONObject(new String(bytes));
            Product product = new Product(
                    object.getString("name"),
                    object.getString("group_name"),
                    object.getString("description"),
                    object.getDouble("amount"),
                    object.getDouble("price")
            );
            repository.update(id, product);
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
        Product product = repository.find_by_name(name);
        if (product == null)
            Server.sendResponse(exchange, 404, Response.INCORRECT_DATA_REQUESTED);
        else {
            repository.delete(name);
            Server.sendResponse(exchange, 204);
        }
    }
}