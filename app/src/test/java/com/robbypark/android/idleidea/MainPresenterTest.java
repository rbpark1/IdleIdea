package com.robbypark.android.idleidea;

import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.model.IdeaDataSource;
import com.robbypark.android.idleidea.presenter.IdeaContract;
import com.robbypark.android.idleidea.presenter.IdeaPresenter;
import com.robbypark.android.idleidea.presenter.MainContract;
import com.robbypark.android.idleidea.presenter.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainContract.View view;

    @Mock
    IdeaDataSource dataSource;

    @InjectMocks
    private MainPresenter presenter;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter(dataSource);
        presenter.attachView(view);
    }

    @Test
    public void main_testInit() {
        verify(view).showIdeaList(any(ArrayList.class));
    }

    @Test
    public void main_callRefreshListView() {
        long time = System.currentTimeMillis();
        Idea ideaA = new Idea(1, "Test", "Test", time, true, 0, 0);
        Idea ideaB = new Idea(1, "Test", "Test", time, false, 1, 0);
        Idea ideaC = new Idea(1, "Test", "Test", time, false, 2, 0);
        ArrayList<Idea> ideas = new ArrayList<>();
        ideas.add(ideaA);
        ideas.add(ideaC);
        ideas.add(ideaB);

        when(dataSource.getAllIdeas()).thenReturn(ideas);
        presenter.refreshListView();
        verify(view).showIdeaList(any(ArrayList.class));
    }

    @Test
    public void main_callOnIdeaCheckboxClick() {
        long time = System.currentTimeMillis();
        Idea idea = new Idea(1, "Test", "Test", time, true, 0, 0);
        when(dataSource.getIdea(1)).thenReturn(idea);
        presenter.onIdeaCheckboxClick(1);
        verify(dataSource).updateIdea(idea);
    }

    @Test
    public void main_callOnIdeaListClick(){
        long time = System.currentTimeMillis();
        Idea idea = new Idea(1, "Test", "Test", time, true, 0, 0);

        presenter.onIdeaListClick(idea);
        verify(view).showIdeaActivity(idea);
    }

    @Test
    public void main_callOnFabClick(){
        presenter.onFabClick();
        verify(view).showIdeaActivity(null);
    }

    @Test
    public void main_callDetachView(){
        presenter.detachView();
        verify(dataSource).close();
    }
}
