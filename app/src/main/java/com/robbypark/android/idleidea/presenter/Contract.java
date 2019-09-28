package com.robbypark.android.idleidea.presenter;

import com.robbypark.android.idleidea.model.Idea;

import java.util.List;

public interface Contract {
    interface MainView {
        void showIdeaActivity(Idea idea);
        void showIdeaList(List<Idea> ideas);
    }

    interface Presenter {
        void ideaListClick();  // TODO

    }
}
