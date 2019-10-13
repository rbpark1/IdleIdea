package com.robbypark.android.idleidea.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.robbypark.android.idleidea.R;
import com.robbypark.android.idleidea.TimeUtils;
import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IdeasAdapter extends ArrayAdapter<Idea> {

    private static class ViewHolder {
        TextView title;
        TextView timeString;
        CheckBox checkBox;
        ImageView statusImage;
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

            viewHolder.title =  convertView.findViewById(R.id.tvTitle);
            viewHolder.timeString =  convertView.findViewById(R.id.tvTimeString);
            viewHolder.checkBox = convertView.findViewById(R.id.itemCheckBox);
            viewHolder.statusImage = convertView.findViewById(R.id.statusImageView);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(idea == null) {
            // Error
            return convertView;
        }

        // Populate the data from the data object via the viewHolder object into the template view.
        viewHolder.title.setText(idea.getTitle());

        if(idea.isDone()) {
            viewHolder.title.setPaintFlags(viewHolder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.timeString.setText(getTimeString(idea));
        } else {
            viewHolder.title.setPaintFlags(viewHolder.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            String timeString = "You thought of this " + DateUtils.getRelativeTimeSpanString(idea.getTime()).toString() + ".";
            viewHolder.timeString.setText(timeString);
        }

        // priority image
        switch(idea.getPriority()) {
            case Constants.LOW_PRIORITY:
                // Green
                viewHolder.statusImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.color_green));
                break;
            case Constants.MED_PRIORITY:
                // Yellow
                viewHolder.statusImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.color_yellow));
                break;
            case Constants.HIGH_PRIORITY:
                // Red
                viewHolder.statusImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.color_red));
                break;
            default:
                break;
        }


        viewHolder.checkBox.setChecked(idea.isDone());
        viewHolder.checkBox.setTag(idea.getId());
        viewHolder.checkBox.setOnClickListener((MainActivity) this.getContext());

        // Return the completed view to render on screen
        return convertView;
    }

    // Creates a time string for completed idea
    // EX: "9/9/19 - 9/12/19. Total: 10m10d10h"
    private String getTimeString(Idea idea) {
        return new SimpleDateFormat("MM/dd/yy").format(new Date(idea.getTime()))
                + " - "
                + new SimpleDateFormat("MM/dd/yy").format(new Date(idea.getEndTime()))
                + ". Total: "
                + TimeUtils.timeAgo(idea.getEndTime() - idea.getTime());
    }

}
