package com.example.hristijan.tabs2.items;

/**
 * Created by Hristijan on 8/4/2016.
 */
public class Classroom {

    private String ID;
    private String name;
    private String description;

    public Classroom(String ID, String name, String description) {
        this.ID = ID;
        this.name = name;
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
