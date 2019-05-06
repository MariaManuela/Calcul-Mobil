package com.calculmobil.calculmobil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "pedometer.db";
    public static final String TABLE_NAME = "userLogin";
    public static final String GOAL_TABLE_NAME = "stepGoal";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "GENDER";
    public static final String COL_3 = "WEIGHT";
    public static final String COL_4 = "HEIGHT";
    public static final String GOAL_COL_1 = "ID";
    public static final String GOAL_COL_2 = "GOAL";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, GENDER TEXT, WEIGHT TEXT, HEIGHT TEXT)");
        db.execSQL("create table if not exists " + GOAL_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, GOAL INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GOAL_TABLE_NAME);
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
    public boolean insertGoal(int goal)
    {
        //gender = "Boy";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOAL_COL_2, goal);
        long result = db.insert(GOAL_TABLE_NAME,null,contentValues);

        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(" select GOAL from " +  GOAL_TABLE_NAME + " order by " + GOAL_COL_1 + " desc limit 1",null);

        return res;
    }

    public Cursor getWeight()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor weightDb = db.rawQuery(" select WEIGHT from " + TABLE_NAME + " order by " + COL_3 + " desc limit 1", null );
        return weightDb;
    }

    public Cursor getHeight()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor heightDb = db.rawQuery(" select HEIGHT from " + TABLE_NAME + " order by " + COL_4 + " desc limit 1", null );
        return heightDb;
    }
    //public boolean updateData(int goal, int id)
   // {
   //     SQLiteDatabase db = this.getWritableDatabase();
   //     ContentValues contentValues = new ContentValues();
   //     contentValues.put(GOAL_COL_1, id);
   //     contentValues.put(GOAL_COL_2, goal);
   //     db.update(GOAL_TABLE_NAME, contentValues, "ID = ?", new String[]{GOAL_COL_1});
   //     return true;
   // }






}
