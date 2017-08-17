package com.example.android.idleidea;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Robby on 8/17/2017.
 */

public class IdeaActivity extends Activity {

    private EditText editTextTitle;
    private Button buttonAdd;
    private IdeaDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

    }
}
