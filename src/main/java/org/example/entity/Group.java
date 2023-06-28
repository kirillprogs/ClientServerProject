package org.example.entity;

import lombok.*;
import org.json.JSONObject;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Group {

    private String name;
    private String description;

    public Group(String name) {
        this(name, "");
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("name", name)
                .put("description", description);
    }
}