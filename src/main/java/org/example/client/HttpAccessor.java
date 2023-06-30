package org.example.client;

import org.example.entity.Group;
import org.example.entity.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;

public class HttpAccessor {

    private final HttpClient httpClient;
    private String token = "";
    private final String prefix;

    public HttpAccessor() {
        httpClient = HttpClient.newHttpClient();
        prefix = "http://localhost:12369";
    }

    public void logout() {
        this.token = "";
    }

    public void login(String username, String password) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/login/"))
                    .header("Username", username)
                    .header("Password", password)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200)
                throw new Exception(response.body());
            token = response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public void register(String username, String password) {

    }

    public Product findProductId(String name) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/good/" + name + "/"))
                    .header("Authorization", token)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
                return new Product(response.body());
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public void createProduct(String name, String group_name,
                              String description, double amount, double price) throws Exception {
        try {
            String json = new Product(name, group_name, description, amount, price).toJSON().toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/good/"))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204)
                return;
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public void updateProduct(String id, String name, String group_name,
                              String description, double amount, double price) throws Exception {
        try {
            String json = new Product(name, group_name, description, amount, price).toJSON().toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/good/" + id + "/"))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204)
                return;
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public void deleteProduct(String name) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/good/" + name + "/"))
                    .header("Authorization", token)
                    .DELETE()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204)
                return;
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public Group findGroupId(String name) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/group/" + name + "/"))
                    .header("Authorization", token)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
                return new Group(response.body());
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public void createGroup(String name, String description) throws Exception {
        try {
            String json = new Group(name, description).toJSON().toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/group/"))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204)
                return;
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public void updateGroup(String id, String name, String description) throws Exception {
        try {
            String json = new Group(name, description).toJSON().toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/group/" + id + "/"))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204)
                return;
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public void deleteGroup(String name) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/group/" + name + "/"))
                    .header("Authorization", token)
                    .DELETE()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204)
                return;
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public List<Product> allProducts(String group_name) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/goods/all/" + (group_name != null ? group_name : "")))
                    .header("Authorization", token)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<Product> list = new LinkedList<>();
                JSONArray array = new JSONArray(response.body());
                for (int i = 0; i < array.length(); ++i)
                    list.add(new Product(array.getJSONObject(i).toString()));
                return list;
            }
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public List<Group> allGroups() throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/group/all/"))
                    .header("Authorization", token)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<Group> list = new LinkedList<>();
                JSONArray array = new JSONArray(response.body());
                for (int i = 0; i < array.length(); ++i)
                    list.add(new Group(array.getJSONObject(i).toString()));
                return list;
            }
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public double value(String group_name) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/value/" + (group_name == null ? "store/" : "group/" + group_name)))
                    .header("Authorization", token)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
                return new JSONObject(response.body()).getDouble("value");
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public void change(String name, double amount) throws Exception {
        try {
            String toSend = new JSONObject()
                    .put("name", name).put("amount", Math.abs(amount))
                    .toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prefix + "/api/operate/" + (amount >= 0 ? "increase/" : "decrease/")))
                    .header("Authorization", token)
                    .POST(HttpRequest.BodyPublishers.ofString(toSend))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204)
                return;
            if (response.statusCode() == 403)
                throw new Exception("Unauthorized");
            throw new Exception(response.body());
        } catch (JSONException | URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error processing query: see log");
        }
    }

    public void decrease(String name, double amount) {

    }

    public List<Product> search(List<String> group_ids, String name_pattern,
                                String description_pattern,
                                double amount_lower, double amount_upper,
                                double price_lower, double price_upper) {

        return null;
    }
}