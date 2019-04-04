package com.calculmobil.calculmobil;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.w3c.dom.Text;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;


public class PedometerActivity extends AppCompatActivity implements SensorEventListener {

    DatabaseHelper myDb;
    ProgressBar progressBar;
    private ImageButton calendarIcon;
    private ImageButton settingsButton;
    private Button startButton;
    private ImageView kcalIcon;
    private ImageView distanceIcon;
    private ImageView timeIcon;

    boolean active = false;
    private TextView stepText;
    private TextView timeText;
    private TextView kcalText;
    private TextView distanceText;

    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] accelValues;
    private float[] magnetValues;

    private int stepCount = 0;
    private long stepTimestamp = 0;
    private int stepDetector = 0;
    private long startTime = 0;
    long timeMilliseconds = 0;
    long elapsedTime = 0;
    long updatedTime = 0;
    private double distance = 0;

    private Handler handler = new Handler();
    private SharedPreferences sharedPreferences;
    private int dayStepRecord;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer2);

        myDb = new DatabaseHelper(this);

        calendarIcon = (ImageButton) findViewById(R.id.calendarButton);
        startButton = (Button) findViewById(R.id.startPauseButton);
        progressBar = (ProgressBar) findViewById((R.id.progressBar));
        stepText = (TextView) findViewById(R.id.stepsText);
        kcalIcon = (ImageView) findViewById(R.id.kcalIcon);
        distanceIcon = (ImageView) findViewById(R.id.distanceIcon);
        timeIcon = (ImageView) findViewById((R.id.timeIcon));
        timeText = (TextView) findViewById(R.id.timeText);
        kcalText = (TextView) findViewById(R.id.kcalText);
        distanceText = (TextView) findViewById(R.id.distanceText);
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        setViewDefaultValues();


        sensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        accelerometer = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(stepDetectorSensor == null)
            showErrorDialog();

        if(startButton!=null)
        {
            startButton.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v)
               {
                   sensorManager.registerListener(PedometerActivity.this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
                   sensorManager.registerListener(PedometerActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                   sensorManager.registerListener(PedometerActivity.this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
                   startTime = SystemClock.uptimeMillis();
                   handler.postDelayed(timerRunnable, 0);

                   //handler.removeCallbacks(timerRunnable);
                       //active = false;


               }
            });
        }

        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PedometerActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        calendarIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PedometerActivity.this, CalendaActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setViewDefaultValues()
    {
        stepText.setText(String.format(getResources().getString(R.string.steps), 0));
        timeText.setText(String.format(getResources().getString(R.string.time), "00:00:00"));
        distanceText.setText(String.format(getResources().getString(R.string.distance), "0"));
        kcalText.setText(String.format(getResources().getString(R.string.kcal), 0));



    }

    private void showErrorDialog() {
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType())
        {
            case(Sensor.TYPE_ACCELEROMETER):
                accelValues=event.values;
                break;
            case(Sensor.TYPE_MAGNETIC_FIELD):
                magnetValues=event.values;
                break;
            case(Sensor.TYPE_STEP_DETECTOR):
                    countSteps(event.values[0]);

                break;

        }


    }

    private void countSteps(float step)
    {


        stepCount += (int) step;
        distance = stepCount * 0.8; //Average step length in an average adult
        String distanceString = String.format("%.2f m", distance);
        distanceText.setText(String.format(getResources().getString(R.string.distance), distanceString));
        stepText.setText(String.format(getResources().getString(R.string.steps), stepCount));

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            timeMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = elapsedTime + timeMilliseconds;

            int seconds = (int) (updatedTime / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            String timeString = String.format("%d:%s:%s", hours, String.format("%02d", minutes), String.format("%02d", seconds));

            timeText.setText(String.format(getResources().getString(R.string.time), timeString));

            handler.postDelayed(this, 0);
        }
    };


}
