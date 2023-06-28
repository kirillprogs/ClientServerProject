package org.example.controller;

import org.example.entity.Product;
import org.example.entity.Group;
import org.json.JSONArray;

import java.util.List;

public class ControllerUtilities {

    public static JSONArray productListToJSON(List<Product> list) {
        JSONArray result = new JSONArray();
        for (Product value : list)
            result.put(value.toJSON());
        return result;
    }

    public static JSONArray groupListToJSON(List<Group> list) {
        JSONArray result = new JSONArray();
        for (Group value : list)
            result.put(value.toJSON());
        return result;
    }
}