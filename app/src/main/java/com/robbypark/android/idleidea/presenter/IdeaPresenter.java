package com.robbypark.android.idleidea.presenter;

import android.content.Context;

import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.model.IdeaDataSource;

import java.util.Date;

public class IdeaPresenter implements IdeaContract.Presenter{

    private IdeaDataSource mDataSource;
    private IdeaContract.View mView;
    private Idea mIdea;

    public IdeaPresenter(Context context, IdeaContract.View view) {
        mDataSource = IdeaDataSource.getInstance(context);
        mDataSource.open();
        mView = view;
    }

    @Override
    public void loadIdea(long id) {
        // get Idea with id
        mIdea = mDataSource.getIdea(id);
        mView.showIdea(mIdea);
    }

    @Override
    public void loadNewIdea() {
        // create new Idea
        mIdea = new Idea();
        mIdea.setTime(new Date().getTime());
        mView.showIdea(mIdea);
    }

    @Override
    public void onDestroy() {
        // Do nothing
    }

    @Override
    public void deleteIdea() {
        mDataSource.deleteIdea(mIdea);
        mView.closeView();
    }

    @Override
    public void updateIdea(String title, String notes, int priority) {
        if(title.equals("")) {
           return;
        }
        mIdea.setTitle(title);
        mIdea.setNotes(notes);
        mIdea.setPriority(priority);
        // Update or insert into DB depending on if it is a new or existing idea
        if(mDataSource.containsIdea(mIdea)) {
            mDataSource.updateIdea(mIdea);
        } else {
            mDataSource.insertIdea(mIdea);
        }

        mView.closeView();
    }
}
