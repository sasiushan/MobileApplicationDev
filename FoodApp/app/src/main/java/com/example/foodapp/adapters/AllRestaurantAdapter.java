package com.example.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.AllRestaurantsListener;
import com.example.foodapp.R;
import com.example.foodapp.models.AllRestaurantModels;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AllRestaurantAdapter extends RecyclerView.Adapter<AllRestaurantAdapter.ViewHolder> {

    private Context context;
    private List<AllRestaurantModels> list;
    private AllRestaurantsListener listener;

    public AllRestaurantAdapter(Context context, List<AllRestaurantModels> list, AllRestaurantsListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public AllRestaurantAdapter(List<AllRestaurantModels> allRestaurantModels) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_all_res_items,parent, false));
//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.all_res_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.roundedImageView.setImageResource(list.get(position).getImg());
        holder.restaurantName.setText(list.get(position).getName());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemClicked(list.get(holder.getAdapterPosition()));
//            }
//        });

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, RestaurantOne.class);
//                intent.putExtra("NAME", list.get(position).getName());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        RoundedImageView roundedImageView;
        TextView restaurantName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.all_res_item_cardview);
            roundedImageView = itemView.findViewById(R.id.quick_order_restaurant_pic);
            restaurantName = itemView.findViewById(R.id.all_res_res_name);
        }
    }
}
