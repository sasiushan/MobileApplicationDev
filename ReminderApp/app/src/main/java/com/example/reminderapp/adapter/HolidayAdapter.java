package com.example.reminderapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapp.R;
import com.example.reminderapp.model.HolidayModel;

import java.util.List;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.ViewHolder>{

    List<HolidayModel> holidayModelList;
    Context context;
    Activity activity;

    public HolidayAdapter(List<HolidayModel> eventModelList, Context context)
    {
        this.holidayModelList = eventModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HolidayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview2_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayAdapter.ViewHolder holder, int position) {

        holder.title.setText(holidayModelList.get(position).getTitle());
        holder.date.setText(holidayModelList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return holidayModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.recyclerview2_date);
            date = itemView.findViewById(R.id.recyclerview2_title);
        }
    }
}

