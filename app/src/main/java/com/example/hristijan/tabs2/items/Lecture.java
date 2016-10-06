package com.example.hristijan.tabs2.items;

import java.util.Date;

/**
 * Created by Hristijan on 8/4/2016.
 */
public class Lecture {

    private String ID;
    private String subjectID;
    private String subjectName;
    private String subjectTypeID;
    private String subjectTypeName;
    private String staffID;
    private String staffImageUrl;
    private String staffFirstName;
    private String staffLastName;
    private String classroomID;
    private String classroomName;
    private String classroomDescription;
    private String dateFrom;
    private String dateTo;
    private int dayInWeek;

    public Lecture(String ID, String subjectName, String subjectTypeName, String staffImageUrl, String staffLastName, String staffFirstName, String classroomName, String classroomDescription, String dateFrom, String dateTo) {
        this.ID = ID;
        this.subjectName = subjectName;
        this.subjectTypeName = subjectTypeName;
        this.staffImageUrl = staffImageUrl;
        this.staffLastName = staffLastName;
        this.staffFirstName = staffFirstName;
        this.classroomDescription = classroomDescription;
        this.classroomName = classroomName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Lecture(String ID, String subjectID, String subjectTypeID, String staffID, String classroomID, String dateFrom, String dateTo, int dayInWeek) {
        this.ID = ID;
        this.subjectID = subjectID;
        this.subjectTypeID = subjectTypeID;
        this.staffID = staffID;
        this.classroomID = classroomID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dayInWeek = dayInWeek;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectTypeID() {
        return subjectTypeID;
    }

    public void setSubjectTypeID(String subjectTypeID) {
        this.subjectTypeID = subjectTypeID;
    }

    public String getSubjectTypeName() {
        return subjectTypeName;
    }

    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffImageUrl() {
        return staffImageUrl;
    }

    public void setStaffImageUrl(String staffImageUrl) {
        this.staffImageUrl = staffImageUrl;
    }

    public String getStaffFirstName() {
        return staffFirstName;
    }

    public void setStaffFirstName(String staffFirstName) {
        this.staffFirstName = staffFirstName;
    }

    public String getStaffLastName() {
        return staffLastName;
    }

    public void setStaffLastName(String staffLastName) {
        this.staffLastName = staffLastName;
    }

    public String getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(String classroomID) {
        this.classroomID = classroomID;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getClassroomDescription() {
        return classroomDescription;
    }

    public void setClassroomDescription(String classroomDescription) {
        this.classroomDescription = classroomDescription;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public int getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(int dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    @Override
    public String toString() {
        return getID() + ", ден " + getDayInWeek();
        //return getStaffLastName() + " " + getStaffFirstName() + ", " + getSubjectName() + " - " + getSubjectTypeName() + ", во " + getClassroomName() + ", време: " + getDateFrom() + " - " + getDateTo();
    }
}
