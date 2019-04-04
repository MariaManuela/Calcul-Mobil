package com.calculmobil.calculmobil;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "pedometer.db";
    public static final String TABLE_NAME = "userLogin";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "GENDER";
    public static final String COL_3 = "WEIGHT";
    public static final String COL_4 = "HEIGHT";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, GENDER TEXT, WEIGHT TEXT, HEIGHT TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String gender,String height, String weight)
    {
        //gender = "Boy";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, gender);
        contentValues.put(COL_3, weight);
        contentValues.put(COL_4, height);
        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1)
            return false;
        else
            return true;

    }




}
