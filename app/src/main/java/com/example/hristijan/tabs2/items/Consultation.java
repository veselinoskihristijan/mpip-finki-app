package com.example.hristijan.tabs2.items;

import java.util.ArrayList;

/**
 * Created by Hristijan on 8/4/2016.
 */
public class Consultation {

    private String ID;
    private String staffID;
    private String staffImageUrl;
    private String staffFirstName;
    private String staffLastName;
    private String roomID;
    private String roomName;
    private String roomDescription;
    private String dateFrom;
    private String dateTo;
    private String email;
    private int dayInWeek;

    public Consultation(String ID, String staffImageUrl, String staffFirstName, String staffLastName, String roomName, String roomDescription, String dateFrom, String dateTo, String email) {
        this.ID = ID;
        this.staffImageUrl = staffImageUrl;
        this.staffFirstName = staffFirstName;
        this.staffLastName = staffLastName;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.email = email;
    }

    public Consultation(String ID, String staffID, String roomID, String dateFrom, String dateTo, int dayInWeek) {
        this.ID = ID;
        this.email = staffID;
        this.staffID = staffID;
        this.roomID = roomID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dayInWeek = dayInWeek;
    }

    public String getEmail(){
        return email;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
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
        return "Consultation{" +
                "ID=" + ID +
                ", staffID='" + staffID + '\'' +
                ", staffImageUrl='" + staffImageUrl + '\'' +
                ", staffFirstName='" + staffFirstName + '\'' +
                ", staffLastName='" + staffLastName + '\'' +
                ", roomID='" + roomID + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomDescription='" + roomDescription + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", email='" + email + '\'' +
                ", dayInWeek=" + dayInWeek +
                '}';
    }
}
