package com.example.android.idleidea;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Robby on 8/17/2017.
 */

public class IdeaCursorAdapter extends CursorAdapter {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final long DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final long MONTH_MILLIS = 30 * DAY_MILLIS;
    private static final long YEAR_MILLIS = 12 * MONTH_MILLIS;

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
        String timeString = timeAgo(time);
        tvTitle.setText(title);
        tvTimeString.setText(timeString);
    }

    public String timeAgo(long startTime) {
        Date date = new Date();
        long now = date.getTime();
        long diff = now - startTime;

        String output = "It has been ";

        while (diff >= MINUTE_MILLIS) {
            if (diff < 60 * MINUTE_MILLIS) {
                if (diff / MINUTE_MILLIS == 1) {
                    output += "1 minute";
                } else {
                    output += diff / MINUTE_MILLIS + " minutes";
                }
                diff %= MINUTE_MILLIS;

            } else if (diff < 24 * HOUR_MILLIS) {
                if (diff / HOUR_MILLIS == 1) {
                    output += "1 hour";
                } else {
                    output += diff / HOUR_MILLIS + " hours";
                }
                diff %= HOUR_MILLIS;
            } else if (diff < 30 * DAY_MILLIS) {
                if (diff / DAY_MILLIS == 1) {
                    output += "1 day";
                } else {
                    output += diff / DAY_MILLIS + " days";
                }
                diff %= DAY_MILLIS;
            } else if (diff < 12 * MONTH_MILLIS) {
                if (diff / MONTH_MILLIS == 1) {
                    output += "1 month";
                } else {
                    output += diff / MONTH_MILLIS + " months";
                }
                diff %= MONTH_MILLIS;
            } else {
                if (diff / YEAR_MILLIS == 1) {
                    output += "1 year";
                } else {
                    output += diff / YEAR_MILLIS + " years";
                }
                diff %= YEAR_MILLIS;
            }
            output += " ";
        }

        output += "since you thought of this idea.";
        return output;
    }

}
