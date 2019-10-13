package com.robbypark.android.idleidea.presenter;

import com.robbypark.android.idleidea.model.Idea;

import java.util.ArrayList;

public interface MainContract {
    interface View {
        void showIdeaActivity(Idea idea);
        void showIdeaList(ArrayList<Idea> ideas);
        void updateIdeaList(ArrayList<Idea> ideas);
    }

    interface Presenter {
        void onIdeaListClick(Idea idea);
        void onIdeaCheckboxClick(long id);
        void onFabClick();
        void refreshListView();
        void onDestroy();
    }
}

