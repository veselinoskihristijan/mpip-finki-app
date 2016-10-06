package com.example.hristijan.tabs2.items;

/**
 * Created by Hristijan on 8/4/2016.
 */
public class News {

    private String ID;
    private String title;
    private String content;
    private String date;
    private String imageUrl;

    public News(String ID, String title, String content, String date, String imageUrl) {
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.date = date;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return ID + ". " + title;
    }
}
