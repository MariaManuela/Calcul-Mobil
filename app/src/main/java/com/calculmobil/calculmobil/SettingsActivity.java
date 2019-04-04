package com.calculmobil.calculmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

public class SettingsActivity extends AppCompatActivity {

    private NumberPicker goalPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        goalPicker = (NumberPicker) findViewById(R.id.stepGoalPicker);

        goalPicker.setMinValue(0);
        goalPicker.setMaxValue(5000);
    }
}
