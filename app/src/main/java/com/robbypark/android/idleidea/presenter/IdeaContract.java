package com.robbypark.android.idleidea.presenter;

import com.robbypark.android.idleidea.model.Idea;

public interface IdeaContract {
    interface View {
        void showIdea(Idea idea);
        void closeView();
    }

    interface Presenter {
        void attachView(IdeaContract.View view);
        void detachView();
        void loadIdea(long id);
        void loadNewIdea();
        void deleteIdea();
        void updateIdea(String title, String notes, int priority);
    }
}
