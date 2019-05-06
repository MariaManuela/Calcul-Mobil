package com.calculmobil.calculmobil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class SettingsActivity extends AppCompatActivity {

    private NumberPicker goalPicker;
    private Button setButton;
    private int stepGoal;
    String goalText;
    //int id;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myDb = new DatabaseHelper(this);

        goalPicker = (NumberPicker) findViewById(R.id.stepGoalPicker);
        setButton = (Button) findViewById(R.id.setButton);

        goalPicker.setMinValue(0);
        goalPicker.setMaxValue(5000);

        setButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stepGoal = goalPicker.getValue();
                //id = goalPicker.getId();

                myDb.insertGoal(stepGoal);


                Intent intent = new Intent(SettingsActivity.this, PedometerActivity.class);

                startActivity(intent);
            }
        });
    }
}
