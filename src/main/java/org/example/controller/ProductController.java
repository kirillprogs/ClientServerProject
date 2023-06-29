package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.entity.Group;
import org.example.entity.Product;
import org.example.network.Response;
import org.example.network.Server;
import org.example.repository.GroupRepository;
import org.example.repository.ProductRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ProductController {

    private final ProductRepository repository;
    private final GroupRepository groupRepository;

    public ProductController(ProductRepository repository, GroupRepository groupRepository) {
        this.repository = repository;
        this.groupRepository = groupRepository;
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
            Server.sendResponse(exchange, 404, "No product with this name");
        else {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            Server.sendResponse(exchange, 200, product.toJSON().toString());
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
            Product product = new Product(new String(bytes));
            Product found = repository.find_by_name(product.getName());
            if (found != null) {
                if (!change || !found.getName().equals(array[3])) {
                    Server.sendResponse(exchange, 403, "Product with this name already exists");
                    return;
                }
            }
            Group group = groupRepository.find_by_name(product.getGroup_name());
            if (group == null) {
                Server.sendResponse(exchange, 403, "Cannot create product: no such group");
                return;
            }
            if (product.getPrice() < 0 || product.getAmount() < 0) {
                Server.sendResponse(exchange, 403, "Price and amount cannot be negative");
                return;
            }
            if (change)
                repository.update(array[3], product);
            else
                repository.create(product);
            Server.sendResponse(exchange, 204);
        } catch (JSONException e) {
            Server.sendResponse(exchange, 400, Response.JSON_FORMAT_ERROR);
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

    public void getAll(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length > 5) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        List<Product> list = null;
        if (array.length == 4) {
            list = repository.list_by_criteria(null, "%", "%",
                    -1, -1, -1, -1);
        } else if (array.length == 5) {
            List<String> ids = new LinkedList<>();
            ids.add(array[4]);
            list = repository.list_by_criteria(ids, "%", "%",
                    -1, -1, -1, -1);
        }
        if (list == null) {
            Server.sendResponse(exchange, 501, Response.SQL_ERROR);
            return;
        }
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        Server.sendResponse(exchange, 200, ControllerUtilities.productListToJSON(list).toString());
    }

    public void changeAmount(HttpExchange exchange, boolean increase) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != 4) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        try {
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            JSONObject object = new JSONObject(new String(bytes));
            String name = object.getString("name");
            double amount = object.getDouble("amount");
            if (amount < 0) {
                Server.sendResponse(exchange, 400,
                        "Value to increase or decrease must be non-negative");
                return;
            }
            Product product = repository.find_by_name(name);
            if (product == null) {
                Server.sendResponse(exchange, 404, "No such product in the database");
                return;
            }
            if (increase) {
                repository.increase(name, amount);
            } else {
                if (product.getAmount() < amount) {
                    Server.sendResponse(exchange, 400, "Insufficient amount of product");
                    return;
                }
                repository.decrease(name, amount);
            }
            Server.sendResponse(exchange, 204);
        } catch (JSONException e) {
            Server.sendResponse(exchange, 400, Response.JSON_FORMAT_ERROR);
        } catch (IOException e) {
            Server.sendResponse(exchange, 501, Response.READ_WRITE_ERROR);
        }
    }

    public void search(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] array = path.split("/");
        if (array.length != 3) {
            Server.sendResponse(exchange, 400, Response.URL_PARAMETERS_INCORRECT);
            return;
        }
        try {
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            JSONObject object = new JSONObject(new String(bytes));
            String name = object.has("name") ? object.getString("name") : null;
            String description = object.has("description") ? object.getString("description") : null;
            double amountLower = object.has("amount_lower") ? object.getDouble("amount_lower") : -1;
            double amountUpper = object.has("amount_upper") ? object.getDouble("amount_upper") : -1;
            double priceLower = object.has("price_lower") ? object.getDouble("price_lower") : -1;
            double priceUpper = object.has("price_upper") ? object.getDouble("price_upper") : -1;
            List<String> groups = new LinkedList<>();
            if (object.has("groups")) {
                JSONArray arrayGroups = object.getJSONArray("groups");
                for (int i = 0; i < arrayGroups.length(); i++)
                    groups.add(arrayGroups.getString(i));
            }
            List<Product> list = repository.list_by_criteria(groups, name, description
                    , amountLower, amountUpper, priceLower, priceUpper);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            Server.sendResponse(exchange, 200, ControllerUtilities.productListToJSON(list).toString());
        } catch (IOException e) {
            Server.sendResponse(exchange, 501, Response.READ_WRITE_ERROR);
        } catch (JSONException e) {
            Server.sendResponse(exchange, 400, Response.JSON_FORMAT_ERROR);
        }
    }
}