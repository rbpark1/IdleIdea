package com.robbypark.android.idleidea.view;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.robbypark.android.idleidea.R;
import com.robbypark.android.idleidea.model.Idea;

import java.util.ArrayList;

public class IdeasAdapter extends ArrayAdapter<Idea> {
    private static class ViewHolder {
        TextView title;
        TextView timeString;
    }

    public IdeasAdapter(Context context, ArrayList<Idea> ideas) {
        super(context, R.layout.list_item, ideas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Idea idea = getItem(position);
        ViewHolder viewHolder;

        // check if view is being reused, else inflate new view
        if(convertView == null) {
            // inflate brand new view
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.timeString = (TextView) convertView.findViewById(R.id.tvTimeString);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object into the template view.
        viewHolder.title.setText(idea.getTitle());
        String timeString = "You thought of this " + DateUtils.getRelativeTimeSpanString(idea.getTime()).toString() + ".";
        viewHolder.timeString.setText(timeString);

        // Return the completed view to render on screen
        return convertView;
    }
}
