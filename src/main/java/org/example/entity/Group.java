package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Group {

    private int id;
    private String name;
    private String description;

    public Group(String name) {
        this(0, name);
    }

    public Group(int id, String name) {
        this(id, name, "");
    }

    public Group(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}