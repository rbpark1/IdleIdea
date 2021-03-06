package com.robbypark.android.idleidea.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Robby on 8/14/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_IDEAS = "ideas";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DONE = "done";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_ENDTIME = "endtime";

    private static final String DATABASE_NAME = "ideas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + TABLE_IDEAS + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_TITLE + " text not null, "
                    + COLUMN_NOTES + " text not null, "
                    + COLUMN_TIME + " integer, "
                    + COLUMN_PRIORITY + " integer, "
                    + COLUMN_ENDTIME + " integer, "
                    + COLUMN_DONE + " integer);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IDEAS);
        onCreate(db);
    }
}
