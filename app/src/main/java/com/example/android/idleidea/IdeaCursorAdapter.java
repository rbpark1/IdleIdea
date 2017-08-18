package com.example.android.idleidea;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Robby on 8/17/2017.
 */

public class IdeaCursorAdapter extends CursorAdapter {

    public IdeaCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvTimeString = (TextView) view.findViewById(R.id.tvTimeString);
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        long time = cursor.getLong(cursor.getColumnIndexOrThrow("time"));
        tvTitle.setText(title);
        tvTimeString.setText(String.valueOf(time));
    }
}
