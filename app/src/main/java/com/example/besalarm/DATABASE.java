package com.example.besalarm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DATABASE extends SQLiteOpenHelper {
    public static final String NAME_TABLE = "AlarmData";
    public static final int ID_ROW              = 0;
    public static final int NAME_ALARM_ROW      = 1;
    public static final int TRIGGER_TIME_ROW    = 2;
    public static final int IS_ACTIVE           = 3;

    public static final int IS_ACTIVE_MONDAY = 4;
    public static final int IS_ACTIVE_TUESTDAY = 5;
    public static final int IS_ACTIVE_WEDNESDAY = 6;
    public static final int IS_ACTIVE_THURSDAY = 7;
    public static final int IS_ACTIVE_FRIDAY = 8;
    public static final int IS_ACTIVE_SATURDAY = 9;
    public static final int IS_ACTIVE_SUNDAY = 10;

    public DATABASE(@Nullable Context context) {
        super(context, "note.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void queryData(String string){
        getWritableDatabase().execSQL(string);
    }
    public Cursor getData(String string){
        return getReadableDatabase().rawQuery(string,null);
    }
}
