package com.example.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.AllRestaurantsListener;
import com.example.foodapp.R;
import com.example.foodapp.models.OffersModels;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private Context context;
    private List<OffersModels> list;
    private AllRestaurantsListener listener;

    public OffersAdapter(Context context, List<OffersModels> list, AllRestaurantsListener listener)
    {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.roundedImageView.setImageResource(list.get(position).getImg());
        holder.roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView roundedImageView;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.img);
        }

    }
}
