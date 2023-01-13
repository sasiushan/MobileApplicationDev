package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.reminderapp.adapter.HolidayAdapter;
import com.example.reminderapp.model.HolidayModel;

import java.util.ArrayList;
import java.util.List;

public class HolidayActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button submitButton, backButton;
    EditText title;
    TextView date, textView;
    LinearLayoutManager linearLayoutManager;
    HolidayAdapter holidayAdapter;
    private static Bundle mBundleRecyclerViewStateHoliday;
    private final String KEY_RECYCLER_STATE = "recycler_state";

    String titleInput, dateInput;
    String returnedDate;

    public static List<HolidayModel> holidayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);

        textView = findViewById(R.id.textView3);
        submitButton = findViewById(R.id.holidaySubmitButton);
        backButton = findViewById(R.id.holidayBackButton);
        title = findViewById(R.id.holidayTitle);
        date = findViewById(R.id.holidayDate);
        recyclerView = findViewById(R.id.recyclerview2);
        linearLayoutManager = new LinearLayoutManager(this);

        textView.setVisibility(View.INVISIBLE);
        Bundle bundle11 = getIntent().getExtras();
        if(bundle11!=null)
        {
            returnedDate = bundle11.getString("curdate");
            date.setText(returnedDate);
        }

        boolean var = createRecyclerViewLayout();
        if(var==true) {

            textView.setVisibility(View.VISIBLE);
            createRecyclerViewLayout();
        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!title.getText().toString().isEmpty() && !date.getText().toString().isEmpty()) {
                    if (title.getText().toString().length() >= 0 && date.getText().toString().length() >= 0) {
                        titleInput = title.getText().toString();
                        dateInput = date.getText().toString();

                        HolidayModel holidayModel = new HolidayModel(titleInput, dateInput);
                        holidayList.add(holidayModel);

                        createRecyclerViewLayout();
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HolidayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean createRecyclerViewLayout()
    {
        boolean flag=false;

        if(!holidayList.isEmpty())
        {
            flag = true;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            Log.d("SIZE", String.valueOf(holidayList.size()));

            holidayAdapter = new HolidayAdapter(holidayList, this);

            recyclerView.setAdapter(holidayAdapter);
            holidayAdapter.notifyDataSetChanged();
        }
        return flag;
    }
}
