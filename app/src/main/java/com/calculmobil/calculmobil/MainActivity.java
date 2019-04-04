package com.calculmobil.calculmobil;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    String[] elements = new String[3];


    ImageButton girlImageButton;
    ImageButton boyImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        girlImageButton = (ImageButton) findViewById(R.id.girlButton);
        boyImageButton = (ImageButton)findViewById(R.id.boyButton);


        girlImageButton.setOnClickListener(new View.OnClickListener(){
            // When the button is pressed/clicked, it will run the code below
            @Override
            public void onClick(View v){

                elements[0] = "Girl";
                myDb.insert(elements[0], null, null);

                // Intent is what you use to start another activity
                Intent intent = new Intent(MainActivity.this, BodyActivity.class);
                intent.putExtra("myExtras", elements);
                startActivity(intent);
            }
        });

        boyImageButton.setOnClickListener(new View.OnClickListener() {
           // When the button is pressed/clicked, it will run the code below
           @Override
           public void onClick(View v) {

                elements[0] = "Boy";
                myDb.insert(elements[0], null, null);


                // Intent is what you use to start another activity
                Intent intent = new Intent(MainActivity.this, BodyActivity.class);
                intent.putExtra("myExtras", elements);
                startActivity(intent);
           }
        });



    }




}
