package org.example.client;

import org.example.entity.Group;
import org.example.entity.Product;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpAccessor {

    private HttpClient httpClient;
    private String token;

    public HttpAccessor() {
        httpClient = HttpClient.newHttpClient();
    }

    public void login(String username, String password) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:12369/login/"))
                    .header("Username", username)
                    .header("Password", password)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (URISyntaxException e) {
            System.err.println("Incorrect URI");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void register(String username, String password) {

    }

    public Product findProductId(String name) {
        return null;
    }

    public void createProduct(String name, String group_name,
                              String description, double amount, double price) {

    }

    public void updateProduct(String id, String name, String group_name,
                              String description, double amount, double price) {

    }

    public void deleteProduct(String name) {

    }

    public Group findGroupId(String name) {
        return null;
    }

    public void createGroup(String name, String description) {

    }

    public void updateGroup(String id, String name, String description) {

    }

    public void deleteGroup(String name) {

    }

    public List<Product> allProducts() {
        return null;
    }

    public List<Product> allProductsInGroup(String group_name) {
        return null;
    }

    public List<Group> allGroups() {
        return null;
    }

    public double storeValue() {
        return 0;
    }

    public double groupValue(String group_name) {
        return 0;
    }

    public void increase(String name, double amount) {

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