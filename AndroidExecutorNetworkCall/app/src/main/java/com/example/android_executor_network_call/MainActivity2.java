package com.example.android_executor_network_call;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {

    TextView idText, nameText, addressText, infoText;
    Button button;

    String nameOTextOutput, addressTextOutput, infoTextOutput;
    public String defaultKey = "posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        idText = findViewById(R.id.userdetailsresults_ID);
        nameText = findViewById(R.id.userdetailsresults_NAME);
        addressText = findViewById(R.id.userdetailsresults_ADDRESS);
        infoText = findViewById(R.id.userdetailsresults_INFO);
        button = findViewById(R.id.loadButton);


        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");

        String name = intent.getExtras().getString("name");
        String username = intent.getExtras().getString("username");
        String email = intent.getExtras().getString("email");

        String street = intent.getExtras().getString("street");
        String suite = intent.getExtras().getString("suite");
        String city = intent.getExtras().getString("city");
        String zipcode = intent.getExtras().getString("zipcode");
        String lat = intent.getExtras().getString("lat");
        String lng = intent.getExtras().getString("lng");

        String phone = intent.getExtras().getString("phone");
        String website = intent.getExtras().getString("website");
        String companyName = intent.getExtras().getString("companyName");
        String catchPhrase = intent.getExtras().getString("catchPhrase");
        String bs = intent.getExtras().getString("bs");

        nameOTextOutput = String.format("Name: %s\nUsername: %s\nEmail: %s\n", name, username, email);
        addressTextOutput = String.format("Street: %s\nSuite: %s\nCity: %s\nZipcode: %s\nLatitude: %s\nLongitude: %s", street, suite, city, zipcode, lat, lng);
        infoTextOutput = String.format("Phone: %s\nWebsite: %s\nname: %s\ncatchPhrase: %s\nbs: %s", phone, website, companyName, catchPhrase, bs);

        idText.setText(id);

        nameText.setText(nameOTextOutput);
        addressText.setText(addressTextOutput);
        infoText.setText(infoTextOutput);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                intent.putExtra("USERID", id);
                startActivity(intent);

            }
        });


    }
}