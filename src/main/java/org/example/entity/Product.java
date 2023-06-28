package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Product {

    private int id;
    private int group_id;
    private String name;
    private String description;
    private double amount;
    private double price;

    public Product(String name, int group_id, double amount, double price) {
        this(name, group_id, "", amount, price);
    }

    public Product(String name, int group_id, String description, double amount, double price) {
        this(0, group_id, name, description, amount, price);
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("id", id)
                .put("group_id", group_id)
                .put("name", name)
                .put("description", description)
                .put("amount", amount)
                .put("price", price);
    }
}