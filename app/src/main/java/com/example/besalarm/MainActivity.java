package com.example.besalarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static SimpleDateFormat sortSimpleDateFormat = new SimpleDateFormat("HH:mm  dd.MM");
    public static SimpleDateFormat fullSimpleDateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

    public static FragmentManager mfragmentManager;
    public static ArrayList<Clock> mClocks;
    public static AlarmAdapter mAlarmAdapter;

    public static DATABASE database;
//    public static final String IDSQl = "ID";
//    public static final String NAME_ALARM_SQl = "NAME_ALARM";
//    public static final String TRIGGER_TIME = "ID";
//    public static final String IS_ACTIVE = "ID";
//    public static final String IS_ACTIVE_MONDAY_ = "ID";

    private MainContentFragment mainContentFragment = new MainContentFragment();
    private AlarmBrdcstReciever alarmBrdcstReciever;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mfragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mfragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_framelayout, mainContentFragment,mainContentFragment.TAG).commit();

        database = new DATABASE(this);
        database.queryData("CREATE TABLE IF NOT EXISTS "+ DATABASE.NAME_TABLE +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nameAlarm VARCHAR(280)," +
                " triggerTime LONG," +
                " isActive  INTEGER," +
                " isActive2 INTEGER," +
                " isActive3 INTEGER," +
                " isActive4 INTEGER," +
                " isActive5 INTEGER," +
                " isActive6 INTEGER," +
                " isActive7 INTEGER," +
                " isActiveS INTEGER"  +")");

        if(mClocks==null){
            mClocks = new ArrayList<Clock>();
        }
        mAlarmAdapter = new AlarmAdapter(this,mClocks);

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY,1);
//        calendar.set(Calendar.MINUTE,0);
//        calendar.set(Calendar.SECOND,0);
//        String stringResult =  "null, "
//                +"'"+"nameOfAlarm"+"'"+","
//                +calendar.getTimeInMillis()+", "
//                +0+", "
//                +1+", "
//                +1+", "
//                +1+", "
//                +1+", "
//                +1+", "
//                +0+", "
//                +0+" ";
//        database.queryData("INSERT INTO "+DATABASE.NAME_TABLE+" VALUES( "+stringResult+" )")
//        database.queryData("DELETE FROM " + DATABASE.NAME_TABLE);
        alarmBrdcstReciever = new AlarmBrdcstReciever();
        IntentFilter intentFilter = new IntentFilter("com.example.basalarm.action.GOT_NEW_ALARM");
        registerReceiver(alarmBrdcstReciever,intentFilter);

        showDetailAlarmInDatabase(this);
    }

    public void clickClick(View view){
        switch (view.getId()){
            case R.id.add_clock_btn:{
                FragmentTransaction fragmentTransaction = mfragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_framelayout, new AddClockFragment()).commit();
                break;
            }
        }
    }

    public static void showDetailAlarmInDatabase(Context context){
        DATABASE database_g = new DATABASE(context);
        Cursor cursor =  database_g.getData("SELECT * FROM " + DATABASE.NAME_TABLE );
        Log.e("empty", "showDetailAlarmInDatabase: " + "////////////////////////////////////////" );
        while (cursor.moveToNext()){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(2));
            Log.e(TAG, "showDetailAlarmInDatabase: " + cursor.getInt(DATABASE.ID_ROW) + " "
                    + cursor.getString(DATABASE.NAME_ALARM_ROW) + " "
                    + cursor.getLong(DATABASE.TRIGGER_TIME_ROW) + " "
                    + cursor.getInt(DATABASE.IS_ACTIVE) + " "
                    + cursor.getInt(DATABASE.IS_ACTIVE_MONDAY) + " "
                    + cursor.getInt(DATABASE.IS_ACTIVE_TUESTDAY) + " "
                    + cursor.getInt(DATABASE.IS_ACTIVE_WEDNESDAY) + " "
                    + cursor.getInt(DATABASE.IS_ACTIVE_THURSDAY) + " "
                    + cursor.getInt(DATABASE.IS_ACTIVE_FRIDAY) + " "
                    + cursor.getInt(DATABASE.IS_ACTIVE_SATURDAY) + " "
                    + cursor.getInt(DATABASE.IS_ACTIVE_SUNDAY) + " "
                    + MainActivity.fullSimpleDateFormat.format(calendar.getTime()));
        }
        Log.e("empty", "showDetailAlarmInDatabase: " + "////////////////////////////////////////" );
    }
    public static void resetList(){
        mClocks.clear();
        Cursor cursor =  database.getData("SELECT * FROM " + DATABASE.NAME_TABLE );
        while (cursor.moveToNext()){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(DATABASE.TRIGGER_TIME_ROW));
            Clock clock = new Clock(
                    cursor.getInt(DATABASE.ID_ROW),
                    cursor.getString(DATABASE.NAME_ALARM_ROW),
                    calendar);
            if(cursor.getInt(DATABASE.IS_ACTIVE) == 1 ){
                clock.setActive(true);
            }else {
                clock.setActive(false);
            }
            if(cursor.getInt(DATABASE.IS_ACTIVE_MONDAY) == 1){
                clock.setActiveDayOfWeek(Clock.MONDAY,true);
            }else {
                clock.setActiveDayOfWeek(Clock.MONDAY,false);
            }
            if(cursor.getInt(DATABASE.IS_ACTIVE_TUESTDAY) == 1){
                clock.setActiveDayOfWeek(Clock.TUESTDAY,true);
            }else {
                clock.setActiveDayOfWeek(Clock.TUESTDAY,false);
            }
            if(cursor.getInt(DATABASE.IS_ACTIVE_WEDNESDAY) == 1){
                clock.setActiveDayOfWeek(Clock.WEDNESDAY,true);
            }else {
                clock.setActiveDayOfWeek(Clock.WEDNESDAY,false);
            }
            if(cursor.getInt(DATABASE.IS_ACTIVE_THURSDAY) == 1){
                clock.setActiveDayOfWeek(Clock.THURSDAY,true);
            }else {
                clock.setActiveDayOfWeek(Clock.THURSDAY,false);
            }
            if(cursor.getInt(DATABASE.IS_ACTIVE_FRIDAY) == 1){
                clock.setActiveDayOfWeek(Clock.FRIDAY,true);
            }else {
                clock.setActiveDayOfWeek(Clock.FRIDAY,false);
            }
            if(cursor.getInt(DATABASE.IS_ACTIVE_SATURDAY) == 1){
                clock.setActiveDayOfWeek(Clock.SATURDAY,true);
            }else {
                clock.setActiveDayOfWeek(Clock.SATURDAY,false);
            }
            if(cursor.getInt(DATABASE.IS_ACTIVE_SUNDAY) == 1){
                clock.setActiveDayOfWeek(Clock.SUNDAY,true);
            }else {
                clock.setActiveDayOfWeek(Clock.SUNDAY,false);
            }
            mClocks.add(clock);
        }
        mAlarmAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart:" );
        mainContentFragment.listView.setAdapter(mAlarmAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        unregisterReceiver(alarmBrdcstReciever);
    }
}
