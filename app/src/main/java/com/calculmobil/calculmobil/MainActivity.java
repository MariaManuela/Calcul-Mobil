package com.calculmobil.calculmobil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    ImageButton girlImageButton;
    ImageButton boyImageButton;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        girlImageButton = (ImageButton) findViewById(R.id.girlButton);
        boyImageButton = (ImageButton)findViewById(R.id.boyButton);


        girlImageButton.setOnClickListener(new View.OnClickListener(){
            // When the button is pressed/clicked, it will run the code below
            @Override
            public void onClick(View v){
                // Intent is what you use to start another activity
                Intent intent = new Intent(MainActivity.this, BodyActivity.class);
                startActivity(intent);
            }
        });

        boyImageButton.setOnClickListener(new View.OnClickListener(){
            // When the button is pressed/clicked, it will run the code below
            @Override
            public void onClick(View v){
                // Intent is what you use to start another activity
                Intent intent = new Intent(MainActivity.this, BodyActivity.class);
                startActivity(intent);
            }
        });

    }




}
