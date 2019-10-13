package com.robbypark.android.idleidea.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.robbypark.android.idleidea.R;
import com.robbypark.android.idleidea.model.Idea;
import com.robbypark.android.idleidea.Constants;
import com.robbypark.android.idleidea.presenter.IdeaContract;
import com.robbypark.android.idleidea.presenter.IdeaPresenter;


/**
 * Created by Robby on 8/17/2017.
 * Activity used to view details of an idea.
 * Also used to create an activity.
 */

public class IdeaActivity extends AppCompatActivity implements IdeaContract.View {

    private EditText editTextTitle;
    private EditText editTextNotes;
    private Button buttonSave;
    private TextView textViewTime;
    private RadioGroup radioGroup;

    private IdeaPresenter mPresenter;
    private Bundle extras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextNotes = findViewById(R.id.editTextNotes);
        buttonSave = findViewById(R.id.buttonSave);
        textViewTime = findViewById(R.id.textViewTime);
        radioGroup = findViewById(R.id.radioGroup);

        mPresenter = new IdeaPresenter(this);

        extras = getIntent().getExtras();
        if (extras != null) {
            long id = extras.getLong("id");
            mPresenter.loadIdea(id);

        } else {
            mPresenter.loadNewIdea();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (extras != null) {
            getMenuInflater().inflate(R.menu.idea_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuDelete:
                mPresenter.deleteIdea();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showIdea(Idea idea) {
        editTextTitle.setText(idea.getTitle());
        editTextNotes.setText(idea.getNotes());
        textViewTime.setText("Created on " + new SimpleDateFormat("MM/dd/yyyy").format(new Date(idea.getTime())));

        int priority = idea.getPriority();
        switch(priority) {
            case Constants.LOW_PRIORITY:
                radioGroup.check(R.id.radioButtonLow);
                break;
            case Constants.MED_PRIORITY:
                radioGroup.check(R.id.radioButtonMed);
                break;
            case Constants.HIGH_PRIORITY:
                radioGroup.check(R.id.radioButtonHigh);
                break;
            default:
                break;
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String title = editTextTitle.getText().toString();

                if(title.length() < 1) {
                    Toast.makeText(v.getContext(), "Please enter a title.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                String notes = editTextNotes.getText().toString();

                int priority = -1;
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButtonLow:
                        priority = Constants.LOW_PRIORITY;
                        break;
                    case R.id.radioButtonMed:
                        priority = Constants.MED_PRIORITY;

                        break;
                    case R.id.radioButtonHigh:
                        priority = Constants.HIGH_PRIORITY;
                        break;
                    default:
                        // Error
                        break;
                }

                mPresenter.updateIdea(title, notes, priority);
            }
        });
    }

    @Override
    public void closeView() {
        finish();
    }
}