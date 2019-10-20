package com.robbypark.android.idleidea.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.robbypark.android.idleidea.R;
import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.model.IdeaDataSource;
import com.robbypark.android.idleidea.presenter.MainContract;
import com.robbypark.android.idleidea.presenter.MainPresenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener {

    private final String TAG = "MainActivity";
    private final String TAG_ID = "id";

    private IdeasAdapter adapter;
    private ListView listView;
    private MainPresenter presenter;

    // Lifecycle callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fab);

        presenter = new MainPresenter(IdeaDataSource.getInstance());
        presenter.attachView(this);

        // FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open IdeaActivity with blank idea
                presenter.onFabClick();
            }
        });
    }

    @Override
    protected void onResume() {
        presenter.refreshListView();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    // MainContract.View
    @Override
    public void showIdeaActivity(Idea idea) {
        Intent intent = new Intent(getApplicationContext(), IdeaActivity.class);
        if(idea != null) {
            Bundle bundle = new Bundle();
            bundle.putLong(TAG_ID, idea.getId());
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void showIdeaList(ArrayList<Idea> ideas) {
        // ListView
        adapter = new IdeasAdapter(this, ideas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open IdeaActivity
                Idea idea = (Idea) parent.getAdapter().getItem(position);
                presenter.onIdeaListClick(idea);
            }
        });
    }

    @Override
    public void updateIdeaList(ArrayList<Idea> ideas) {
        adapter.clear();
        adapter.addAll(ideas);
        adapter.notifyDataSetChanged();
    }

    // Checkbox click
    @Override
    public void onClick(View view) {
        long id = (Long) view.getTag();
        Log.d(TAG, "" + id);
        presenter.onIdeaCheckboxClick(id);
    }
}
