package com.example.hristijan.tabs2.items;

/**
 * Created by Hristijan on 8/4/2016.
 */
public class Subject {

    private String ID;
    private String name;
    private int semester;
    private boolean compulsory;
    private String description;
    private int credits;
    private String classes;
    private String subjectType;

    public Subject() {
    }

    public Subject(String ID, String name, int semester, boolean compulsory, String description, int credits, String classes) {
        this.ID = ID;
        this.name = name;
        this.semester = semester;
        this.compulsory = compulsory;
        this.description = description;
        this.credits = credits;
        this.classes = classes;
    }

    public Subject(String ID, String name, int semester, boolean compulsory, String description, int credits, String classes, String subjectType) {
        this.ID = ID;
        this.name = name;
        this.semester = semester;
        this.compulsory = compulsory;
        this.description = description;
        this.credits = credits;
        this.classes = classes;
        this.subjectType = subjectType;
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

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public boolean isCompulsory() {
        return compulsory;
    }

    public void setCompulsory(boolean compulsory) {
        this.compulsory = compulsory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", semester=" + semester +
                ", compulsory=" + compulsory +
                ", description='" + description + '\'' +
                ", credits=" + credits +
                ", classes='" + classes + '\'' +
                ", subjectType='" + subjectType + '\'' +
                '}';
    }
}
