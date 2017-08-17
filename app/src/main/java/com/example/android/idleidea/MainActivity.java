package com.example.android.idleidea;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private ListView listView;
    private List<Idea> ideas;

    IdeaDataSource dataSource;
    IdeaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new IdeaDataSource(this);
        dataSource.open();

        long time = getTime();

        ideas = dataSource.getAllIdeas();
        adapter = new IdeaAdapter(MainActivity.this, R.layout.list_item, ideas);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

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

    private long getTime(){
        Date date = new Date();
        return date.getTime();
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }


}
