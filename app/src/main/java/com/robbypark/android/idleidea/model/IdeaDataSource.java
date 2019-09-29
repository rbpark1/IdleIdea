package com.robbypark.android.idleidea.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.robbypark.android.idleidea.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.robbypark.android.idleidea.model.SQLiteHelper.COLUMN_TIME;


/**
 * Created by Robby on 8/14/2017.
 * DAO data access object
 * performs CRUD operations on ideas table by interfacing with SQLiteHelper
 */
public class IdeaDataSource {

    private static IdeaDataSource instance = null;

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_TITLE, SQLiteHelper.COLUMN_NOTES, COLUMN_TIME};

    // Future: dependency injection Context
    public static IdeaDataSource getInstance(Context context)
    {
        if (instance == null)
            instance = new IdeaDataSource(context);

        return instance;
    }

    private IdeaDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        if(database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
    }

    public void close() {
        dbHelper.close();
    }


    public void insertIdea(Idea idea) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, idea.getTitle());
        values.put(SQLiteHelper.COLUMN_NOTES, idea.getNotes());
        values.put(SQLiteHelper.COLUMN_TIME, idea.getTime());

        long insertId = database.insert(SQLiteHelper.TABLE_IDEAS, null, values);
    }

    // query DB for idea
    public Idea getIdea(long id) {
        String table = SQLiteHelper.TABLE_IDEAS;
        String[] columns = allColumns;
        String selection = SQLiteHelper.COLUMN_ID + " = " + id;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return getIdeaFromCursor(cursor);
        }

        return null;
    }

    public boolean containsIdea(Idea idea) {
        return getIdea(idea.getId()) != null;
    }

    private Idea getIdeaFromCursor(Cursor cursor) {
        return new Idea(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getLong(3));
    }

    public void deleteIdea(Idea idea) {
        long id = idea.getId();
        database.delete(SQLiteHelper.TABLE_IDEAS, SQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void updateIdea(Idea idea) {
        long id = idea.getId();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, idea.getTitle());
        values.put(SQLiteHelper.COLUMN_NOTES, idea.getNotes());
        database.update(SQLiteHelper.TABLE_IDEAS, values, SQLiteHelper.COLUMN_ID + " = ?",
                new String[]{Long.toString(id)});
    }

    public ArrayList<Idea> getAllIdeas() {
        ArrayList<Idea> ideas = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_IDEAS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ideas.add(getIdeaFromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return ideas;
    }

}
