package com.robbypark.android.idleidea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.robbypark.android.idleidea.MySQLiteHelper.COLUMN_TIME;

/**
 * Created by Robby on 8/14/2017.
 * DAO data access object
 * performs CRUD operations on ideas table
 */

public class IdeaDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_NOTES, COLUMN_TIME};

    public IdeaDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Idea createIdea(String title, String notes, long time) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_NOTES, notes);
        values.put(MySQLiteHelper.COLUMN_TIME, time);
        long insertId = database.insert(MySQLiteHelper.TABLE_IDEAS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_IDEAS, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Idea newIdea = cursorToIdea(cursor);
        cursor.close();
        return newIdea;
    }

    private Idea cursorToIdea(Cursor cursor) {
        Idea idea = new Idea();
        idea.setId(cursor.getLong(0));
        idea.setTitle(cursor.getString(1));
        idea.setNotes(cursor.getString(2));
        idea.setTime(cursor.getLong(3));
        return idea;
    }

    public void deleteIdea(Idea idea) {
        long id = idea.getId();
        database.delete(MySQLiteHelper.TABLE_IDEAS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void updateIdea(Idea idea, String newTitle, String newNotes) {
        long id = idea.getId();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, newTitle);
        values.put(MySQLiteHelper.COLUMN_NOTES, newNotes);
        database.update(MySQLiteHelper.TABLE_IDEAS, values, MySQLiteHelper.COLUMN_ID + " = ?",
                new String[]{Long.toString(id)});
    }

    public List<Idea> getAllIdeas() {
        List<Idea> ideas = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_IDEAS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Idea idea = cursorToIdea(cursor);
            ideas.add(idea);
            cursor.moveToNext();
        }
        cursor.close();
        return ideas;
    }

    public Cursor getCursor() {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_IDEAS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

}
