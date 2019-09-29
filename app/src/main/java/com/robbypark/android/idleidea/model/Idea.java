package com.robbypark.android.idleidea.model;

/**
 * Created by Robby on 8/14/2017.
 */

public class Idea {

    private long id;
    private String title;
    private String notes;
    private long time;
    private int priority;
    private boolean isDone;
    private long endTime;

    public Idea () {

    }

    public Idea(long id, String title, String notes, long time, boolean isDone, int priority, long endTime) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.time = time;
        this.isDone = isDone;
        this.priority = priority;
        this.endTime = 0;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
