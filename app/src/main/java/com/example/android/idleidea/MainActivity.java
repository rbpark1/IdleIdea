package com.example.android.idleidea;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private final String TAG_ID = "id";

    private ListView listView;
    private List<Idea> ideas;

    private IdeaDataSource dataSource;
    private CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new IdeaDataSource(this);
        dataSource.open();

        long time = getTime();

        ideas = dataSource.getAllIdeas();
        adapter = new IdeaCursorAdapter(this, dataSource.getCursor());

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: success");
                int id_to_search = position;
                Bundle bundle = new Bundle();
                bundle.putInt(TAG_ID, id_to_search);
                Intent intent = new Intent(getApplicationContext(), IdeaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IdeaActivity.class);
                startActivity(intent);
            }
        });

// test code
//        dataSource.createIdea("work on projects", time);
//        dataSource.createIdea("listen to music", time);
//        Log.d(TAG, "onCreate: " + dataSource.getAllIdeas().toString());
//
//        List<Idea> ideas = dataSource.getAllIdeas();
//        if(ideas.size() != 0){
//            dataSource.deleteIdea(ideas.get(0));
//        }
//
//        ideas = dataSource.getAllIdeas();
//        if(ideas.size() != 0)
//        dataSource.updateIdea(ideas.get(0), "study");
//
//        Log.d(TAG, "onCreate: " + dataSource.getAllIdeas().toString());
//
//        this.deleteDatabase("ideas.db");
    }

    private long getTime() {
        Date date = new Date();
        return date.getTime();
    }

    @Override
    protected void onResume() {
        dataSource.open();
        adapter.changeCursor(dataSource.getCursor());
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }


}
