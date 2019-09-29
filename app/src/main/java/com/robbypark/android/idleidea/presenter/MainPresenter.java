package com.robbypark.android.idleidea.presenter;

import android.content.Context;

import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.model.IdeaDataSource;


public class MainPresenter implements MainContract.Presenter {

    private IdeaDataSource mDataSource;
    private MainContract.View mView;

    public MainPresenter(Context context, MainContract.View view) {
        // instantiate stuff
        mDataSource = IdeaDataSource.getInstance(context);
        mDataSource.open();
        mView = view;

        mView.showIdeaList(mDataSource.getAllIdeas());
    }

    @Override
    public void onIdeaListClick(Idea idea){
        mView.showIdeaActivity(idea);
    }

    @Override
    public void onFabClick() {
        mView.showIdeaActivity(null);
    }

    @Override
    public void refreshListView() {
        mView.updateIdeaList(mDataSource.getAllIdeas());
    }

    @Override
    public void onDestroy() {
        mDataSource.close();
    }


}
