package com.robbypark.android.idleidea.presenter;

import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.model.IdeaDataSource;
import java.util.Date;

public class IdeaPresenter implements IdeaContract.Presenter{

    private IdeaDataSource dataSource;
    private IdeaContract.View view;
    private Idea idea;

    public IdeaPresenter(IdeaDataSource dataSource) {
        this.dataSource = dataSource;
        dataSource.open();
    }

    @Override
    public void loadIdea(long id) {
        // get Idea with id
        idea = dataSource.getIdea(id);
        view.showIdea(idea);
    }

    @Override
    public void attachView(IdeaContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void loadNewIdea() {
        // create new Idea
        idea = new Idea();
        idea.setTime(new Date().getTime());
        view.showIdea(idea);
    }

    @Override
    public void deleteIdea() {
        dataSource.deleteIdea(idea);
        view.closeView();
    }

    @Override
    public void updateIdea(String title, String notes, int priority) {
        if(title.equals("")) {
           return;
        }

        idea.setTitle(title);
        idea.setNotes(notes);
        idea.setPriority(priority);

        // Update or insert into DB depending on if it is a new or existing idea
        if(dataSource.containsIdea(idea)) {
            dataSource.updateIdea(idea);
        } else {
            dataSource.insertIdea(idea);
        }

        view.closeView();
    }
}
