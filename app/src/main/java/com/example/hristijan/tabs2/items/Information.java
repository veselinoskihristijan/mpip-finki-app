package com.example.hristijan.tabs2.items;

/**
 * Created by Hristijan on 8/14/2016.
 */
public class Information {

    private String ID;
    private String title;
    private String content;
    private String date;

    public Information(String ID, String title, String content, String date) {
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
