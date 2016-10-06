package com.example.hristijan.tabs2.items;

/**
 * Created by Hristijan on 8/4/2016.
 */
public class Staff {

    private String categoryID;
    private String level;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String email;
    private String resume;

    public Staff(String email, String categoryID, String level, String firstName, String lastName, String imageUrl, String resume) {
        this.email = email;
        this.categoryID = categoryID;
        this.level = level;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.resume = resume;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
