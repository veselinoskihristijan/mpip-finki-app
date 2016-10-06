package com.example.hristijan.tabs2.items;

/**
 * Created by Hristijan on 8/4/2016.
 */
public class SubjectType {

    private String ID;
    private String name;

    public SubjectType(String ID, String name) {
        this.ID = ID;
        this.name = name;
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
}
