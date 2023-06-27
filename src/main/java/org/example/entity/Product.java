package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product {

    private int id;
    private int group_id;
    private String name;
    private String description;
    private double amount;
    private double price;

    public Product(String name, double amount, double price) {
        this(name, "", amount, price);
    }

    public Product(String name, String description, double amount, double price) {
        this(0, 0, name, description, amount, price);
    }

    public Product(int id, int group_id, String name, String description, double amount, double price) {
        this.id = id;
        this.group_id = group_id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
    }
}