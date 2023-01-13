package com.example.android_executor_network_call;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android_executor_network_call.adapter.PostAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//will contain the posts
public class MainActivity3 extends AppCompatActivity {

    public String defaultKey = "posts";
    public String user_Id = "";

    RecyclerView recyclerView2;
    ProgressBar progressBar2;
    PostAdapter postAdapter;
    LinearLayoutManager linearLayoutManager;

    ExecutorService executorService2 = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        progressBar2 = findViewById(R.id.progressBar2);
        recyclerView2 = findViewById(R.id.recycler2);
        linearLayoutManager = new LinearLayoutManager(this);

        Intent intent = getIntent();
        user_Id =  intent.getExtras().getString("USERID");

        Log.d("USERID", user_Id);

        progressBar2.setVisibility(View.INVISIBLE);


        BackGroundTaskHandler backGroundTaskHandler2 = new BackGroundTaskHandler(MainActivity3.this, defaultKey, progressBar2, recyclerView2, linearLayoutManager, user_Id);
        executorService2.execute(backGroundTaskHandler2);
    }
}