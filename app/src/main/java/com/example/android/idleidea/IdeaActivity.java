package com.example.android.idleidea;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

/**
 * Created by Robby on 8/17/2017.
 */

public class IdeaActivity extends Activity {

    private static final String TAG_TITLE = "title";

    private EditText editTextTitle;
    private Button buttonSave;
    private IdeaDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);
        dataSource = new IdeaDataSource(this);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.open();
                dataSource.createIdea(editTextTitle.getText().toString(), (new Date()).getTime());
                dataSource.close();
            }
        });

    }
}
