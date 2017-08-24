package com.robbypark.android.idleidea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Robby on 8/17/2017.
 * Activity used to view details of an idea.
 * Also used to create an activity.
 */

public class IdeaActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextNotes;
    private Button buttonSave;
    private TextView textViewTime;
    private IdeaDataSource dataSource;
    private Idea idea;
    private Bundle extras;
    private Date date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextNotes = (EditText) findViewById(R.id.editTextNotes);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        textViewTime = (TextView) findViewById(R.id.textViewTime);

        dataSource = new IdeaDataSource(this);
        dataSource.open();

        extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("id");
            List<Idea> list = dataSource.getAllIdeas();
            idea = list.get(value);
            editTextTitle.setText(idea.getTitle());
            editTextNotes.setText(idea.getNotes());
            textViewTime.setText("Created on " + new SimpleDateFormat("MM/dd/yyyy").format(new Date(idea.getTime())));

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = editTextTitle.getText().toString();
                    String notes = editTextNotes.getText().toString();
                    dataSource.updateIdea(idea, title, notes);
                    finish();
                }
            });
        } else {//create new idea
            date = new Date();
            textViewTime.setText("Created on " + new SimpleDateFormat("MM/dd/yyyy").format(date));
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = editTextTitle.getText().toString();
                    String notes = editTextNotes.getText().toString();
                    if(!title.equals("")){
                        dataSource.createIdea(title, notes, date.getTime());
                    }//do not save a blank idea

                    finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(extras != null) {
            getMenuInflater().inflate(R.menu.idea_menu, menu);
        }
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