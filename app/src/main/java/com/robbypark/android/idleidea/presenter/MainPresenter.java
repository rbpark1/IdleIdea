package com.robbypark.android.idleidea.presenter;

import android.util.Log;

import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.model.IdeaDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class MainPresenter implements MainContract.Presenter {

    private IdeaDataSource dataSource;
    private MainContract.View view;

    public MainPresenter(IdeaDataSource dataSource) {
        // instantiate stuff
        this.dataSource = dataSource;
        this.dataSource.open();

        this.view.showIdeaList(sortIdeas(this.dataSource.getAllIdeas()));
    }

    // Sort: first compare isDone, then compare priority
    private ArrayList<Idea> sortIdeas(ArrayList<Idea> ideas) {
        Collections.sort(ideas, new Comparator<Idea>(){

            public int compare(Idea i1, Idea i2)
            {
                // Compare isDone
                int doneCompare;
                if(i1.isDone() == i2.isDone()) {
                    doneCompare = 0;
                } else if(i1.isDone() && !i2.isDone()) {
                    doneCompare = 1;
                } else {
                    doneCompare = -1;
                }

                if(doneCompare != 0) return doneCompare;

                // Compare priority
                Integer p1 = i1.getPriority();
                Integer p2 = i2.getPriority();
                return p2.compareTo(p1);
            }
        });

        return ideas;
    }

    @Override
    public void onIdeaListClick(Idea idea){
        view.showIdeaActivity(idea);
    }

    @Override
    public void onIdeaCheckboxClick(long id) {

        Idea idea = dataSource.getIdea(id);
        idea.setDone(!idea.isDone());
        idea.setEndTime(new Date().getTime()); // current time
        dataSource.updateIdea(idea);
        refreshListView();
        Log.d("MainPresenter", "Checkbox clicked");
    }

    @Override
    public void onFabClick() {
        view.showIdeaActivity(null);
    }

    @Override
    public void refreshListView() {
        view.updateIdeaList(sortIdeas(dataSource.getAllIdeas()));
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        dataSource.close();
    }
}
