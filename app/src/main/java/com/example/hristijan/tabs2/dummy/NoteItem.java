package com.example.hristijan.tabs2.dummy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hristijan on 7/31/2016.
 */
public class NoteItem {

    private int ID;
    private String title;
    private String content;
    private boolean done;
    private Date date;
    Calendar c;
    SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    public NoteItem(){}

    public NoteItem(String title, String content, boolean done) {
        this.title = title;
        this.content = content;
        this.done = done;
    }

    public NoteItem(int ID, String title, String content, boolean done, Date date) {
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.done = done;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDate() {
        return date;
    }

    public String getDateParsed(){
        return df.format(date);
    }

    @Override
    public String toString() {
        return "Note date created: " + getDate().toString() + " - " + getDateParsed() + "; done: " + isDone();
    }
}
