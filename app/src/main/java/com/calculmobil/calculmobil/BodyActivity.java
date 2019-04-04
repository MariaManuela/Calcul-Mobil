package com.calculmobil.calculmobil;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.sql.RowId;


public class BodyActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    NumberPicker weightPicker;
    NumberPicker heightPicker;
    NumberPicker decWeightPicker;
    NumberPicker decHeightPicker;
    Button nextButton;
    String[] elements;
    MainActivity gender = new MainActivity();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        Intent intent = getIntent();
        elements=intent.getStringArrayExtra("myExtras");

        myDb = new DatabaseHelper(this);

        weightPicker = (NumberPicker) findViewById(R.id.weight_id);
        decWeightPicker = (NumberPicker) findViewById((R.id.secondWeight_id));
        heightPicker = (NumberPicker) findViewById(R.id.height_id);
        decHeightPicker = (NumberPicker) findViewById(R.id.secondHeight_id);
        nextButton = (Button) findViewById(R.id.nextButton);


        weightPicker.setMinValue(40);
        weightPicker.setMaxValue(250);
        decWeightPicker.setMinValue(0);
        decWeightPicker.setMaxValue(9);

        heightPicker.setMinValue(150);
        heightPicker.setMaxValue(230);
        decHeightPicker.setMinValue(0);
        decHeightPicker.setMaxValue(9);


        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                elements[1] = heightPicker.getValue()+"."+decHeightPicker.getValue();
                elements[2] = weightPicker.getValue()+"."+decWeightPicker.getValue();
                myDb.insert(elements[0],elements[1], elements[2]);

                Intent intent = new Intent(BodyActivity.this, PedometerActivity.class);
               // intent.putExtra("myExtras", elements);
                startActivity(intent);
            }
        });


    }


}
