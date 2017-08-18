package com.example.android.idleidea;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Robby on 8/16/2017.
 * NO LONGER BEING USED
 *
 */

public class IdeaAdapter extends ArrayAdapter {

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Idea> ideas;


    public IdeaAdapter(@NonNull Context context, @LayoutRes int resource, List<Idea> ideas) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.ideas = ideas;
    }

    @Override
    public int getCount() {
        return ideas.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Idea currentIdea = ideas.get(position);

        viewHolder.tvTitle.setText(currentIdea.getTitle());
        viewHolder.tvTimeSince.setText(String.valueOf(currentIdea.getTime()));

        return convertView;
    }

    private class ViewHolder {
        final TextView tvTitle;
        final TextView tvTimeSince;

        public ViewHolder(View v) {
            this.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            this.tvTimeSince = (TextView) v.findViewById(R.id.tvTimeString);
        }


    }
}
