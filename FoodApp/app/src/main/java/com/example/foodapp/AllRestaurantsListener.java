package com.example.foodapp;

import com.example.foodapp.models.AllRestaurantModels;
import com.example.foodapp.models.OffersModels;
import com.example.foodapp.models.OrderQuickModels;

public interface AllRestaurantsListener {

    public void onItemClicked(OffersModels offersModels);

    public void onItemClicked(OrderQuickModels orderQuickModels);

    public void onItemClicked(AllRestaurantModels allRestaurantModels);


}
