package com.calculmobil.calculmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ImageButton girlImageButton;
    ImageButton boyImageButton;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        girlImageButton = (ImageButton) findViewById(R.id.girlButton);
        boyImageButton = (ImageButton)findViewById(R.id.boyButton);
    }
}
