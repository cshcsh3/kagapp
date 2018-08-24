package edu.sit.great.kagapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class Navigate2Activity extends AppCompatActivity {

    TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate2);

        locationTextView = findViewById(R.id.locationTextView);
        String location = getIntent().getStringExtra("location");
        locationTextView.setText(location);

    }

}
