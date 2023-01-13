package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reminderapp.adapter.ListAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    Button dynamicButton, holidayButton;
    TextView textView;

    String curYear, curMonth, curDay, curDate, curDate2;


    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    NewEvent newEvent;
    HolidayActivity holidayActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView2);
        calendarView = findViewById(R.id.calendar);
        dynamicButton = findViewById(R.id.dynamicButton);
        holidayButton = findViewById(R.id.holidayButton);
        recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);

        textView.setVisibility(View.INVISIBLE);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        curDate = sdf.format(c.getTime());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                curDay = String.valueOf(dayOfMonth);
                curMonth = String.valueOf(month+1);
                curYear = String.valueOf(year);
                curDate = curDay + "-" + curMonth + "-" + curYear;

                onButtonShowPopupWindowClick(view, curYear, curMonth, curDay, curDate);
            }
        });

        {
            dynamicButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(curDate.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Select a date", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, NewEvent.class);
                        intent.putExtra("CURDATE", curDate);;
                        startActivity(intent);
                    }
                }
            });

            holidayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(curDate.isEmpty()){
                        Toast.makeText(MainActivity.this, "Select a date", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(MainActivity.this, HolidayActivity.class);
                        intent.putExtra("curdate", curDate);
                        startActivity(intent);
                    }
                }
            });
        }

        boolean var = createLayout();
        if(var==true)
        {
            textView.setVisibility(View.VISIBLE);
            createLayout();
        }

    }

    private boolean createLayout()
    {
        boolean flag=false;
        if(!newEvent.eventModelList.isEmpty()) {

            flag=true;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            Log.d("SIZE", String.valueOf(newEvent.eventModelList.size()));

            listAdapter = new ListAdapter(newEvent.eventModelList, MainActivity.this);

            recyclerView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();

        }
        return flag;
    }

    private void onButtonShowPopupWindowClick(View view, String inYear, String inMonth, String inDay, String curDate) {

        String year = inYear;
        String month = inMonth;
        String day = inDay;
        String date = curDate;

        int countEvents=0;
        int countHolidays=0;

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_calender_event, null);

        TextView countDisplay = popupView.findViewById(R.id.countDisplay);
        TextView holidayDisplay = popupView.findViewById(R.id.holidayCount);

        // create the popup window
        int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        for(int i=0;i<newEvent.eventModelList.size();i++)
        {
            if(date.equals(newEvent.eventModelList.get(i).getDate()))
            {
                countEvents++;
                countDisplay.setText("number of events: " + countEvents);
            }
        }
        countDisplay.setText("number of events: " + countEvents);

        for(int i=0;i<holidayActivity.holidayList.size();i++)
        {
            if(date.equals(holidayActivity.holidayList.get(i).getDate()))
            {
                countHolidays++;
                holidayDisplay.setText("number of holidays: "+countHolidays);
            }
        }
        holidayDisplay.setText("number of holidays: " + countHolidays);

//         dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

    }

}