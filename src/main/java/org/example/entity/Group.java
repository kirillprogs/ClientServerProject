package org.example.entity;

import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Group {

    private String name;
    private String description;

    public Group(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        name = object.getString("name");
        description = object.getString("description");
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("name", name)
                .put("description", description);
    }
}