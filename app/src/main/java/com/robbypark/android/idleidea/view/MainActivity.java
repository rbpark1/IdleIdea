package com.robbypark.android.idleidea.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.robbypark.android.idleidea.R;
import com.robbypark.android.idleidea.presenter.Presenter;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

//    private CursorAdapter adapter;
    private IdeasAdapter adapter;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = Presenter.getInstance(getApplicationContext());

        // ListView
        ListView listView = (ListView) findViewById(R.id.listView);
//        adapter = new IdeaCursorAdapter(this, presenter.getCursor());
        adapter = new IdeasAdapter(this, presenter.getAllIdeas());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open IdeaActivity
                presenter.openIdeaActivity(position);
            }
        });

        // FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open IdeaActivity with blank idea
                presenter.openBlankIdeaActivity();
            }
        });
    }

    @Override
    protected void onResume() {
//        adapter.changeCursor(presenter.getCursor());
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
