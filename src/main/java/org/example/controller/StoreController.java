package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.network.Response;
import org.example.network.Server;
import org.example.repository.StoreRepository;
import org.json.JSONObject;

public class StoreController {

    private final StoreRepository repository;

    public StoreController(StoreRepository repository) {
        this.repository = repository;
    }

    public void storeValue(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != 4) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        double value = repository.all_price();
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        Server.sendResponse(exchange, 200, new JSONObject().put("value", value).toString());
    }

    public void groupValue(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != 5) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        double value = repository.all_price_group(array[4]);
        if (value == -1) {
            Server.sendResponse(exchange, 501, "Error");
        } else {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            Server.sendResponse(exchange, 200, new JSONObject().put("value", value).toString());
        }
    }
}