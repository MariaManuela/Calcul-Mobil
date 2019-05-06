package com.calculmobil.calculmobil;

import android.animation.ObjectAnimator;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.w3c.dom.Text;

import java.io.Console;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;


public class PedometerActivity extends AppCompatActivity implements SensorEventListener {

    private final String CHANNEL_ID = "personal_notification";
    private final int NOTIFICATION_ID = 001;
    private double speed = 0;


    DatabaseHelper myDb;
    private ProgressBar progressBar;
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
    private TextView stepGoalText;
    private TextView valueText;
    private TextView progressText;

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
    private int percent = 0;
    long timeMilliseconds = 0;
    long elapsedTime = 0;
    long updatedTime = 0;
    private double distance = 0;
    private int pStatus = 0;

    private Handler handler = new Handler();
    private SharedPreferences sharedPreferences;
    private int dayStepRecord;
    StringBuffer buffer = new StringBuffer();





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer2);

        myDb = new DatabaseHelper(this);


        calendarIcon = (ImageButton) findViewById(R.id.calendarButton);
        startButton = (Button) findViewById(R.id.startPauseButton);
        progressBar = (ProgressBar) findViewById((R.id.progressBar));
        progressText=(TextView) findViewById(R.id.progressText);
        stepText = (TextView) findViewById(R.id.stepsText);
        stepGoalText = (TextView) findViewById(R.id.goalText);
        valueText = (TextView) findViewById(R.id.valueText);
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

        Cursor res = myDb.getData();
        int ceva = 0;

        while (res.moveToNext()) {
            //buffer.append(" "+ res.getString(0)+"\n");

            ceva = res.getInt(res.getColumnIndex("GOAL"));

        }
        valueText.setText(String.format(getResources().getString(R.string.value), ceva ));



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
        kcalText.setText(String.format(getResources().getString(R.string.kcal), 0.0));
        valueText.setText(String.format(getResources().getString(R.string.value), 0));
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
                    //progressBar.clearAnimation();
                break;
                default:
                    Thread.interrupted();

        }


    }

    private void countSteps(float step)
    {


        stepCount += (int) step;
        distance = stepCount * 0.8; //Average step length in an average adult
        String distanceString = String.format("%.2f m", distance);
        distanceText.setText(String.format(getResources().getString(R.string.distance), distanceString));
        stepText.setText(String.format(getResources().getString(R.string.steps), stepCount));

        dayStepRecord = Integer.parseInt(valueText.getText().toString());
        final int pasText = Integer.parseInt(stepText.getText().toString());
        //kcalText.setText(String.format(getResources().getString(R.string.value), pasText ));
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(pStatus < 100)
                {

                    //final int progress = (pasText / dayStepRecord) *100;
                    pStatus=pasText/(dayStepRecord/100);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(pStatus != 0) {
                                pStatus+=1;
                                progressText.setText(pStatus + "%");
                                progressBar.setProgress(pStatus);
                            }

                            if(pStatus == 100)
                            {
                                displayNotification();
                            }
                        }
                    });

                }
            }
        }).start();

        int calories = 0;






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
            int time = 0;
            float caloriesBurned = 0.0f;
            float currDistance = 0.0f;
            float greut = 0.0f;
            float h = 0.0f;
            int duration = 0;
            float kcalBurned = 0.0f;

            seconds = seconds % 60;
            minutes = minutes % 60;
            duration = 60 * seconds+minutes;

            String timeString = String.format("%d:%s:%s", hours, String.format("%02d", minutes), String.format("%02d", seconds));

            timeText.setText(String.format(getResources().getString(R.string.time), timeString));


          //  currDistance = Float.parseFloat(distanceText.getText().toString());

            speed = distance/duration;

            Cursor weightDb = myDb.getWeight();

            while (weightDb.moveToNext()) {
                //buffer.append(" "+ res.getString(0)+"\n");

                greut = weightDb.getFloat(weightDb.getColumnIndex("WEIGHT"));

            }

            Cursor heightDb = myDb.getHeight();

            while (heightDb.moveToNext()) {
                //buffer.append(" "+ res.getString(0)+"\n");

                h = heightDb.getFloat(heightDb.getColumnIndex("HEIGHT"));
            }



            //caloriesBurned = (float) ((0.035* greut)+((speed*speed)/h)*0.029*greut);
            caloriesBurned = (float) (0.029 * greut * duration);
            kcalBurned = (float)(caloriesBurned * 0.001);
            kcalText.setText(String.format("%.2f", kcalBurned));



            handler.postDelayed(this, 0);
        }
    };

    public void displayNotification()
    {

        Intent intent = new Intent(this, CongratsScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.sneakers)
                .setContentTitle("Goal reached!!")
                .setContentText("Congratulation, you have reached your daily step goal!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }


}
