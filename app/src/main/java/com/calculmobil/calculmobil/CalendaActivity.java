package com.calculmobil.calculmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;

public class CalendaActivity extends AppCompatActivity {

    ImageView stepImage;
    ImageView kcalImage;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        stepImage = (ImageView) findViewById(R.id.shoeIcon);
        kcalImage = (ImageView) findViewById(R.id.kcalIcon);
        calendarView = (CalendarView) findViewById(R.id.calendarView2);
    }
}
