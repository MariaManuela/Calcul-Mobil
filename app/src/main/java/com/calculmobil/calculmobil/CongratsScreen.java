package com.calculmobil.calculmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CongratsScreen extends AppCompatActivity {

    ImageView kawaii;
    TextView lala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats_screen);

        kawaii = (ImageView) findViewById(R.id.imageView2);
        lala = (TextView) findViewById(R.id.textView2);
    }
}
