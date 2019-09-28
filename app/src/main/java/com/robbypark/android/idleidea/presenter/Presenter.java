package com.robbypark.android.idleidea.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.model.IdeaDataSource;
import com.robbypark.android.idleidea.view.IdeaActivity;


// singleton presenter class
public class Presenter {
    private static Presenter instance = null;
    private IdeaDataSource dataSource;
    private final String TAG_ID = "id";
    private Context mContext;


    // private constructor
    private Presenter(Context context) {
        // instantiate stuff
        this.mContext = context;
        dataSource = new IdeaDataSource(context);
        dataSource.open();
    }

    public static Presenter getInstance(Context context) {
        if(instance == null) {
            instance = new Presenter(context);
        }
        return instance;
    }

    public Idea getIdea(long id) {
        return dataSource.getIdea(id);
    }

    public ArrayList<Idea> getAllIdeas() {
        return dataSource.getAllIdeas();
    }

    public void updateIdeaTitle(Idea idea, String title) {
        dataSource.updateIdeaTitle(idea, title);
    }

    public void updateIdeaNotes(Idea idea, String notes) {
        dataSource.updateIdeaNotes(idea, notes);
    }

    public void deleteIdea(Idea idea) {
        dataSource.deleteIdea(idea);
    }

    public void insertIdea(String title, String notes, long time) {
        dataSource.insertIdea(title, notes, time);
    }

    public Cursor getCursor() {
        return dataSource.getCursor();
    }

    public void openIdeaActivity(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_ID, position);
        Intent intent = new Intent(mContext, IdeaActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public void openBlankIdeaActivity() {
        Intent intent = new Intent(mContext, IdeaActivity.class);
        mContext.startActivity(intent);
    }

}
