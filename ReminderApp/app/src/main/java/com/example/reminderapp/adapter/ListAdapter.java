package com.example.reminderapp.adapter;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapp.Notification;
import com.example.reminderapp.R;
import com.example.reminderapp.model.EventModel;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<EventModel> eventModelList;
    Context context;
    String timeTonotify;

    private MaterialTimePicker materialTimePicker;
    private Calendar userInstanceCalender;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    public ListAdapter(List<EventModel> eventModelList, Context context)
    {
        this.eventModelList = eventModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(eventModelList.get(position).getTitle());
        holder.date.setText(eventModelList.get(position).getDate());
        holder.startTime.setText(eventModelList.get(position).getStartTime());
        holder.endTime.setText(eventModelList.get(position).getEndTime());
        holder.note.setText(eventModelList.get(position).getNote());

        holder.setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectTime();
            }
        });

        holder.setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = eventModelList.get(position).getTitle().toString();
                String dateClicked = eventModelList.get(position).getDate().toString();

                if(timeTonotify == null)
                {
                    Toast.makeText(context, "Set the time!", Toast.LENGTH_SHORT).show();
                }else
                {
                    setAlarm(title, dateClicked, timeTonotify);
                }
            }
        });

        holder.removeAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }


    private void selectTime() {                                                                     //this method performs the time picker task
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                 timeTonotify = i + ":" + i1;                                                        //temp variable to store the time to set alarm
            }
        }, hour, minute, false);
        timePickerDialog.show();


    }

    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);                   //assigining alaram manager object to set alaram

        Intent intent = new Intent(context, Notification.class);
        intent.putExtra("event", text);                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
            Toast.makeText(context, "Reminder has been set", Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void cancelAlarm()
    {
        Intent intent = new Intent(context, Notification.class);

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_IMMUTABLE);

        if(alarmManager == null)
        {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);

        Toast.makeText(context, "reminder cancelled", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, startTime, endTime, note;
        CardView cardView;
        Button setTimeButton, setAlarmButton, removeAlarmButton;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView1);
            title = itemView.findViewById(R.id.recycleview_title);
            date = itemView.findViewById(R.id.recycleview_date);
            startTime = itemView.findViewById(R.id.recycleview_startTime);
            endTime = itemView.findViewById(R.id.recycleview_endTime);
            note = itemView.findViewById(R.id.recycleview_note);
            setTimeButton = itemView.findViewById(R.id.reminder);
            setAlarmButton = itemView.findViewById(R.id.setAlarm);
            removeAlarmButton = itemView.findViewById(R.id.removeAlarm);
        }
    }
}
