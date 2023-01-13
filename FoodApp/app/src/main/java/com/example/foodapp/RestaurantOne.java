package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.foodapp.adapters.AllRestaurantAdapter;
import com.example.foodapp.models.AllRestaurantModels;

import java.util.ArrayList;
import java.util.List;

public  class RestaurantOne extends AppCompatActivity{

    RecyclerView recyclerView;
    AllRestaurantAdapter allRestaurantAdapter;
    List<AllRestaurantModels> allRestaurantModels;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_one);

        String name = getIntent().getStringExtra("NAME");


        recyclerView = findViewById(R.id.allRestaurantsMenus);
        imageView = findViewById(R.id.restaurant_one_pic);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allRestaurantModels= new ArrayList<>();
        allRestaurantAdapter = new AllRestaurantAdapter(allRestaurantModels);
        recyclerView.setAdapter(allRestaurantAdapter);


        if(name!=null && name.equalsIgnoreCase("macdonalds")){

            imageView.setImageResource(R.drawable.macdonalds);
            allRestaurantModels.add(new AllRestaurantModels(R.drawable.mcd1, "mcd1"));
            allRestaurantModels.add(new AllRestaurantModels(R.drawable.mcd2, "mcd2"));
            allRestaurantModels.add(new AllRestaurantModels(R.drawable.mcd3, "mcd3"));
            allRestaurantModels.add(new AllRestaurantModels(R.drawable.mcd4, "mcd4"));
            allRestaurantAdapter.notifyDataSetChanged();
        }



    }

}