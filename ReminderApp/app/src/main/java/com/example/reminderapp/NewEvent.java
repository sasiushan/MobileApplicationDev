package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reminderapp.model.EventModel;

import java.util.ArrayList;
import java.util.List;

public class NewEvent extends AppCompatActivity {

    EditText title, startTime, endTime, note;
    TextView date;
    Button button;

    String titleInput, inputStartTime, inputEndTime, inputNote;
    String recievedDate;

    public static List<EventModel> eventModelList = new ArrayList<>();

    public NewEvent()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            recievedDate = bundle.getString("CURDATE");
        }

        title = findViewById(R.id.titleInput1);
        date = findViewById(R.id.dateInput1);
        startTime = findViewById(R.id.startTimeInput1);
        endTime = findViewById(R.id.endTimeInput1);
        note = findViewById(R.id.noteInput1);
        button = findViewById(R.id.submitButton);

        date.setText(recievedDate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!title.getText().toString().isEmpty() && !startTime.getText().toString().isEmpty() && !endTime.getText().toString().isEmpty() && !note.getText().toString().isEmpty())
                {
                    if(title.getText().toString().length()>=0 && startTime.getText().toString().length()>=0 && endTime.getText().toString().length()>=0 && note.getText().toString().length()>=0) {

                        titleInput = title.getText().toString();
                        inputStartTime = startTime.getText().toString();
                        inputEndTime = endTime.getText().toString();
                        inputNote = note.getText().toString();

                        EventModel eventModel = new EventModel(titleInput, recievedDate, inputStartTime, inputEndTime, inputNote);
                        eventModelList.add(eventModel);

                        Intent intent = new Intent(NewEvent.this, MainActivity.class);
                        intent.putExtra("FLAG", 1);

                        intent.putExtra("TITLE",titleInput);
                        intent.putExtra("DATE",recievedDate);
                        intent.putExtra("startTime",inputStartTime);
                        intent.putExtra("endTime", inputEndTime);
                        intent.putExtra("note",inputNote);
                        startActivity(intent);
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(), "Enter complete details", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

}