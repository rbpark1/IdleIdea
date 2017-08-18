package com.example.android.idleidea;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

/**
 * Created by Robby on 8/17/2017.
 */

public class IdeaActivity extends Activity {

    private static final String TAG_TITLE = "title";

    private EditText editTextTitle;
    private Button buttonSave;
    private IdeaDataSource dataSource;
    private int value;
    private List<Idea> list;

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
            Idea idea = list.get(value);
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
                        dataSource.open();
                        dataSource.createIdea(editTextTitle.getText().toString(), (new Date()).getTime());
                        dataSource.close();
                    }
                    finish();
                }
            });
        }
        dataSource.close();
    }
}