package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Product {

    private String name;
    private String group_name;
    private String description;
    private double amount;
    private double price;

    public Product(String name, String group_name, double amount, double price) {
        this(name, group_name, "", amount, price);
    }

    public Product(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        name = object.getString("name");
        group_name = object.getString("group_name");
        description = object.getString("description");
        amount = object.getDouble("amount");
        price = object.getDouble("price");
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("name", name)
                .put("group_name", group_name)
                .put("description", description)
                .put("amount", amount)
                .put("price", price);
    }
}