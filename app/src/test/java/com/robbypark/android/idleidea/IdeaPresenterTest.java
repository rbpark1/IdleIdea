package com.robbypark.android.idleidea;

import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.model.IdeaDataSource;
import com.robbypark.android.idleidea.presenter.IdeaContract;
import com.robbypark.android.idleidea.presenter.IdeaPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class IdeaPresenterTest {

    @Mock
    IdeaContract.View ideaView;

    @Mock
    IdeaDataSource dataSource;

    @InjectMocks
    private IdeaPresenter presenter;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new IdeaPresenter(dataSource);
        presenter.attachView(ideaView);
    }

    @Test
    public void idea_callLoadIdea() {
        Idea idea = new Idea();
        when(dataSource.getIdea(1)).thenReturn(idea);

        presenter.loadIdea(1);
        verify(ideaView).showIdea(idea);
    }

    @Test
    public void idea_callDeleteIdea() {
        presenter.deleteIdea();
        verify(dataSource).deleteIdea(any(Idea.class));
        verify(ideaView).closeView();
    }

    @Test
    public void idea_callLoadNewIdea() {
        presenter.loadNewIdea();
        verify(ideaView).showIdea(any(Idea.class));
    }


    @Test
    public void idea_updateIdea_doesContainIdea() {
        long time = System.currentTimeMillis();
        Idea idea = new Idea(1, "Test", "Test", time, false, 0, 0);

        when(dataSource.containsIdea(idea)).thenReturn(true);
        when(dataSource.getIdea(1)).thenReturn(idea);

        presenter.loadIdea(1);
        presenter.updateIdea("Test", "Test", 0);
        verify(dataSource).updateIdea(idea);
    }

    @Test
    public void idea_updateIdea_doesNotContainIdea() {
        long time = System.currentTimeMillis();
        Idea idea = new Idea(1, "Test", "Test", time, false, 0, 0);

        when(dataSource.containsIdea(idea)).thenReturn(false);
        when(dataSource.getIdea(1)).thenReturn(idea);

        presenter.loadIdea(1);
        presenter.updateIdea("Test", "Test", 0);
        verify(dataSource).insertIdea(idea);
    }

    @Test
    public void idea_updateIdea_emptyTitle() {
        presenter.updateIdea("", "Test", 0);
        verify(ideaView, never()).closeView();
    }

    @Test
    public void idea_callDetachView() {
        presenter.detachView();
        verify(dataSource).close();
    }

}
