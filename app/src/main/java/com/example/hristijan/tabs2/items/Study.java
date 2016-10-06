package com.example.hristijan.tabs2.items;

/**
 * Created by Hristijan on 8/16/2016.
 */
public class Study {

    int ID;
    String name;
    String description;
    int level;

    public Study(String name, String description, int level) {
        this.name = name;
        this.description = description;
        this.level = level;
    }

    public Study(int ID, String name, String description, int level) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.level = level;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Study{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                '}';
    }
}
