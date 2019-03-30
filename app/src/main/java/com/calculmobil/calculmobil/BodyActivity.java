package com.calculmobil.calculmobil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;


public class BodyActivity extends AppCompatActivity {

    NumberPicker weightPicker;
    NumberPicker heightPicker;
    NumberPicker decWeightPicker;
    NumberPicker decHeightPicker;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        weightPicker = (NumberPicker) findViewById(R.id.weight_id);
        decWeightPicker = (NumberPicker) findViewById((R.id.secondWeight_id));
        heightPicker = (NumberPicker) findViewById(R.id.height_id);
        decHeightPicker = (NumberPicker) findViewById(R.id.secondHeight_id);
        nextButton = (Button) findViewById(R.id.nextButton);




        weightPicker.setMinValue(40);
        weightPicker.setMinValue(250);
        decWeightPicker.setMinValue(0);
        decWeightPicker.setMaxValue(9);

        heightPicker.setMinValue(150);
        heightPicker.setMaxValue(230);
        decHeightPicker.setMinValue(0);
        decHeightPicker.setMaxValue(9);


        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BodyActivity.this, PedometerActivity.class);
                startActivity(intent);
            }
        });

       // weightPicker.setOnValueChangedListener(onValueChangeListener);
       // decWeightPicker.setOnValueChangedListener(onValueChangeListener);
       // heightPicker.setOnValueChangedListener(onValueChangeListener);
        //decHeightPicker.setOnValueChangedListener(onValueChangeListener);
    }

    //NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener(){
      //  @Override
       // public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            //Toast.makeText(BodyActivity.this,
                 //   "selected number "+numberPicker.getValue(), Toast.LENGTH_SHORT);
        //}

    //};
}
