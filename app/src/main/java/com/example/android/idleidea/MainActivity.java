package com.example.android.idleidea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private ListView listView;

    IdeaDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new IdeaDataSource(this);
        dataSource.open();

        Date date = new Date();
        long time = date.getTime();

        List<Idea> ideas = dataSource.getAllIdeas();

        IdeaAdapter adapter = new IdeaAdapter(MainActivity.this, R.layout.list_item, ideas);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

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
