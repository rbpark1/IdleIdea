package com.example.android.idleidea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

/**
 * Created by Robby on 8/17/2017.
 * Activity used to view details of an idea.
 * Also used to create an activity.
 */

public class IdeaActivity extends AppCompatActivity {

    private static final String TAG_TITLE = "title";

    private EditText editTextTitle;
    private Button buttonSave;
    private IdeaDataSource dataSource;
    private int value;
    private List<Idea> list;
    private Idea idea;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        dataSource = new IdeaDataSource(this);
        dataSource.open();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("id");

            list = dataSource.getAllIdeas();
            idea = list.get(value);
            editTextTitle.setText(idea.getTitle());
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = editTextTitle.getText().toString();
                    if (!text.equals("")) {
                        dataSource.updateIdea(list.get(value), text);
                    }
                    finish();
                }
            });
        } else {
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = editTextTitle.getText().toString();
                    if (!text.equals("")) {
                        dataSource.createIdea(editTextTitle.getText().toString(), (new Date()).getTime());
                    }
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.idea_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menuDelete:
                dataSource.deleteIdea(idea);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}