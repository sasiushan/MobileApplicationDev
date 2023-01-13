package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.adapters.AllRestaurantAdapter;
import com.example.foodapp.adapters.OffersAdapter;
import com.example.foodapp.adapters.OrderQuickAdapter;
import com.example.foodapp.models.AllRestaurantModels;
import com.example.foodapp.models.OffersModels;
import com.example.foodapp.models.OrderQuickModels;

import java.util.ArrayList;
import java.util.List;

public class Restaurants extends AppCompatActivity  implements AllRestaurantsListener{

    RecyclerView recyclerViewOffers;
    List<OffersModels> offersModels;
    OffersAdapter offersAdapter;

    RecyclerView recyclerViewQuickOrders;
    List<OrderQuickModels> orderQuickModels;
    OrderQuickAdapter orderQuickAdapter;

    RecyclerView recyclerViewAllRestaurants;
    List<AllRestaurantModels> allRestaurantsModels;
    AllRestaurantAdapter allRestaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        allRestaurantsModels = new ArrayList<>();

        getOffers();
        getAllOrderQuicks();
        getAllRestaurants();


        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Restaurants.this, MainActivity.class));
            }
        });

    }

    private void getOffers() {
        recyclerViewOffers = findViewById(R.id.offers);
        recyclerViewOffers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        recyclerViewOffers.setHasFixedSize(true);

        offersModels = new ArrayList<>();
        offersModels.add(new OffersModels(R.drawable.deal0, "deal0"));
        offersModels.add(new OffersModels(R.drawable.deal1, "deal1"));
        offersModels.add(new OffersModels(R.drawable.deal2,"deal2"));
        offersModels.add(new OffersModels(R.drawable.deal3, "deal3"));
        offersModels.add(new OffersModels(R.drawable.deal4, "deal4"));
        offersModels.add(new OffersModels(R.drawable.deal6, "deal6"));

        offersAdapter = new OffersAdapter(this, offersModels,this);
        recyclerViewOffers.setAdapter(offersAdapter);


    }

    private void getAllOrderQuicks() {
        recyclerViewQuickOrders = findViewById(R.id.quickOrder);
        recyclerViewQuickOrders.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        recyclerViewQuickOrders.setHasFixedSize(true);

        orderQuickModels = new ArrayList<>();
        orderQuickModels.add(new OrderQuickModels(R.drawable.mcd2, "order_mcd2"));
        orderQuickModels.add(new OrderQuickModels(R.drawable.dominos2,  "order_dominos2"));
        orderQuickModels.add(new OrderQuickModels(R.drawable.starbucks1, "order_starbucks1"));
        orderQuickModels.add(new OrderQuickModels(R.drawable.kfc1, "order_kfc1"));
        orderQuickModels.add(new OrderQuickModels(R.drawable.tacos1, "order_tacos1"));

        orderQuickAdapter = new OrderQuickAdapter(this, orderQuickModels, this);
        recyclerViewQuickOrders.setAdapter(orderQuickAdapter);

    }

    private void getAllRestaurants() {
        recyclerViewAllRestaurants = findViewById(R.id.allRestaurants);
        recyclerViewAllRestaurants.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        recyclerViewAllRestaurants.setHasFixedSize(true);


        allRestaurantsModels.add(new AllRestaurantModels(R.drawable.macdonalds, "macdonalds"));
        allRestaurantsModels.add(new AllRestaurantModels(R.drawable.kfc, "kfc"));
        allRestaurantsModels.add(new AllRestaurantModels(R.drawable.starbucks, "starbucks"));
        allRestaurantsModels.add(new AllRestaurantModels(R.drawable.dunkindonuts, "dunkindonuts"));
        allRestaurantsModels.add(new AllRestaurantModels(R.drawable.dominos, "dominos"));


        allRestaurantAdapter = new AllRestaurantAdapter(this, allRestaurantsModels, this);
        recyclerViewAllRestaurants.setAdapter(allRestaurantAdapter);
    }


    @Override
    public void onItemClicked(OffersModels offersModels) {
        Toast.makeText(this, offersModels.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemClicked(OrderQuickModels orderQuickModels) {
        Toast.makeText(this, orderQuickModels.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(AllRestaurantModels allRestaurantModels) {
        Toast.makeText(this, allRestaurantModels.getName(), Toast.LENGTH_SHORT).show();

//        startActivity(new Intent(Restaurants.this, RestaurantOne.class));
//        Intent intent = new Intent(Restaurants.this, RestaurantOne.class);
//        intent.putExtra("picture", allRestaurantModels.getImg());
//        intent.putExtra("name", allRestaurantModels.getName());
//        startActivity(intent);
    }

}