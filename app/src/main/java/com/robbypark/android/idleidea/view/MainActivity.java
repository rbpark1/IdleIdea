package com.robbypark.android.idleidea.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.robbypark.android.idleidea.R;
import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.presenter.MainContract;
import com.robbypark.android.idleidea.presenter.MainPresenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private final String TAG = "MainActivity";
    private final String TAG_ID = "id";

    private IdeasAdapter mAdapter;
    private ListView mListView;
    private MainPresenter mPresenter;

    // Lifecycle callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fab);

        mPresenter = new MainPresenter(getApplicationContext(), this);

        // FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open IdeaActivity with blank idea
                mPresenter.onFabClick();
            }
        });
    }

    @Override
    protected void onResume() {
        mPresenter.refreshListView();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
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
        mAdapter = new IdeasAdapter(this, ideas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open IdeaActivity
                Idea idea = (Idea) parent.getAdapter().getItem(position);
                mPresenter.onIdeaListClick(idea);
            }
        });
    }

    @Override
    public void updateIdeaList(ArrayList<Idea> ideas) {
        mAdapter.clear();
        mAdapter.addAll(ideas);
        mAdapter.notifyDataSetChanged();
    }

}
