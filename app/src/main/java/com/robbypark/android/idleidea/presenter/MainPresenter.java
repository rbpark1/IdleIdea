package com.robbypark.android.idleidea.presenter;

import android.content.Context;
import android.util.Log;

import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.model.IdeaDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainPresenter implements MainContract.Presenter {

    private IdeaDataSource mDataSource;
    private MainContract.View mView;

    public MainPresenter(Context context, MainContract.View view) {
        // instantiate stuff
        mDataSource = IdeaDataSource.getInstance(context);
        mDataSource.open();
        mView = view;

        mView.showIdeaList(sortIdeas(mDataSource.getAllIdeas()));
    }

    private ArrayList<Idea> sortIdeas(ArrayList<Idea> ideas) {
        Collections.sort(ideas, new Comparator<Idea>(){

            public int compare(Idea i1, Idea i2)
            {
                if(i1.isDone() && i2.isDone() || !i1.isDone() && !i2.isDone()) {
                    return 0;
                } else if(i1.isDone() && !i2.isDone()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return ideas;
    }

    @Override
    public void onIdeaListClick(Idea idea){
        mView.showIdeaActivity(idea);
    }

    @Override
    public void onIdeaCheckboxClick(long id) {

        Idea idea = mDataSource.getIdea(id);
        idea.setDone(!idea.isDone());
        mDataSource.updateIdea(idea);
        refreshListView();
        Log.d("MainPresenter", "Checkbox clicked");
    }

    @Override
    public void onFabClick() {
        mView.showIdeaActivity(null);
    }

    @Override
    public void refreshListView() {
        mView.updateIdeaList(sortIdeas(mDataSource.getAllIdeas()));
    }

    @Override
    public void onDestroy() {
        mDataSource.close();
    }
}
