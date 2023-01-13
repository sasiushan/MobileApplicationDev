package com.example.android_executor_network_call;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_executor_network_call.adapter.UserAdapter;
import com.example.android_executor_network_call.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity{

    public String defaultKey = "users";

    ProgressBar progressBar;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    LinearLayoutManager linearLayoutManager;

    BackGroundTaskHandler backGroundTaskHandler;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progressBarId);
        recyclerView = findViewById(R.id.recycler1);
        linearLayoutManager = new LinearLayoutManager(this);

        progressBar.setVisibility(View.INVISIBLE);

                BackGroundTaskHandler backGroundTaskHandler = new BackGroundTaskHandler(MainActivity.this, defaultKey, progressBar, recyclerView, linearLayoutManager);
                executorService.execute(backGroundTaskHandler);

//            }
//        });
    }

}
