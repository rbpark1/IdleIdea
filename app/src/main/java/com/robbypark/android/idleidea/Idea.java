package com.robbypark.android.idleidea;

/**
 * Created by Robby on 8/14/2017.
 */

public class Idea {

    private long id;
    private String title;
    private String notes;
    private long time;

    public Idea(){
    }

    public Idea(long id, String title, long time) {
        this.id = id;
        this.title = title;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String toString() {
        return id + " " + title + " " + time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
