package com.robbypark.android.idleidea.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import static com.robbypark.android.idleidea.model.SQLiteHelper.COLUMN_TIME;


/**
 * Created by Robby on 8/14/2017.
 * DAO data access object
 * performs CRUD operations on ideas table by interfacing with SQLiteHelper
 */
public class IdeaDataSource {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_TITLE, SQLiteHelper.COLUMN_NOTES, COLUMN_TIME};

    public IdeaDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public void insertIdea(String title, String notes, long time) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, title);
        values.put(SQLiteHelper.COLUMN_NOTES, notes);
        values.put(SQLiteHelper.COLUMN_TIME, time);

        long insertId = database.insert(SQLiteHelper.TABLE_IDEAS, null, values);
    }

    private Idea getIdea(Cursor cursor) {
        return new Idea(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getLong(3));
    }

    public Idea getIdea(long id) {
        // TODO: query DB for idea
        return null;
    }

    public void deleteIdea(Idea idea) {
        long id = idea.getId();
        database.delete(SQLiteHelper.TABLE_IDEAS, SQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void updateIdeaTitle(Idea idea, String newTitle) {
        long id = idea.getId();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, newTitle);
        database.update(SQLiteHelper.TABLE_IDEAS, values, SQLiteHelper.COLUMN_ID + " = ?",
                new String[]{Long.toString(id)});
    }

    public void updateIdeaNotes(Idea idea, String notes) {
        long id = idea.getId();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_NOTES, notes);
        database.update(SQLiteHelper.TABLE_IDEAS, values, SQLiteHelper.COLUMN_ID + " = ?",
                new String[]{Long.toString(id)});
    }

    public ArrayList<Idea> getAllIdeas() {
        ArrayList<Idea> ideas = new ArrayList<>();

        Cursor cursor = getCursor();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Idea idea = getIdea(cursor);
            ideas.add(idea);
            cursor.moveToNext();
        }
        cursor.close();

        return ideas;
    }

    // used by CursorAdapter
    public Cursor getCursor() {
        Cursor cursor = database.query(SQLiteHelper.TABLE_IDEAS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

}
