package com.example.besalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class AlarmBrdcstReciever extends BroadcastReceiver {
    private static final String TAG = AlarmBrdcstReciever.class.getSimpleName() ;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent parIntent) {
        Log.e(TAG, "onReceive: " + MainActivity.fullSimpleDateFormat.format(Calendar.getInstance().getTime()));

//        Intent intent = new Intent("com.example.besalarm.action.WAKE_UP");
//        intent.setPackage("com.example.besalarm");
        Intent intent = new Intent("com.example.application.action.yah");
        intent.setPackage("com.example.application");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,-1,intent,0 );
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()+5000,pendingIntent);

        checkAlarmData(context);

    }

    private void checkAlarmData(Context context) {
        DATABASE database = new DATABASE(context);
        Cursor cursor = database.getData("SELECT * FROM " + DATABASE.NAME_TABLE);
        while (cursor.moveToNext()){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(DATABASE.TRIGGER_TIME_ROW));
            if(calendar.before(Calendar.getInstance())){
                calendar.add(Calendar.DATE,1);
                database.queryData("UPDATE " + DATABASE.NAME_TABLE + " SET triggerTime = '"+calendar.getTimeInMillis()+"'");
            }
        }
        Log.e(TAG, "checkAlarmData: " + "đã điều chỉnh ngày!" );
        MainActivity.showDetailAlarmInDatabase(context);
    }
}
