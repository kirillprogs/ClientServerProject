package org.example.entity;

import lombok.*;
import org.json.JSONObject;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Group {

    private int id;
    private String name;
    private String description;

    public Group(String name) {
        this(name, "");
    }

    public Group(String name, String description) {
        this(0, name, description);
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("description", description);
    }
}