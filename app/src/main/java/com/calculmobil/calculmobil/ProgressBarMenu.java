package com.calculmobil.calculmobil;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class ProgressBarMenu extends AppCompatActivity {

    private ProgressBar progressBar;
    ImageButton calendarIcon;
    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        calendarIcon = (ImageButton) findViewById(R.id.calendarButton);
        startButton = (Button) findViewById(R.id.startPauseButton);
        progressBar.setVisibility(View.VISIBLE);
        init();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }

        });

    }

    private void init()
    {
        this.progressBar=findViewById(R.id.progressBar);
    }
}
