package com.example.besalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class AlarmBrdcstReciever extends BroadcastReceiver {
    private static final String TAG = AlarmBrdcstReciever.class.getSimpleName() ;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent parIntent) {
//        Log.e(TAG, "onReceive: " + MainActivity.fullSimpleDateFormat.format(Calendar.getInstance().getTime()));
        // kiểm tra ngày trong sql có nằm ở sau giờ hiện tại hay ko
        checkAlarmData(context);

        DATABASE database = new DATABASE(context);
        Cursor cursor = database.getData("SELECT * FROM " + DATABASE.NAME_TABLE);
        while (cursor.moveToNext()){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(DATABASE.TRIGGER_TIME_ROW));
            // thứ 2,... có được chạy đồng hồ không? vv
            Boolean alarmToday = true;
            if (cursor.getInt(DATABASE.IS_ACTIVE) == 1  && cursor.getInt((calendar.get(Calendar.DAY_OF_WEEK)+3)) == 0) {
                alarmToday = false;
            }
            // luôn chạy báo thức dù notActive
            createPendingAlarm(context,
                    cursor.getInt(DATABASE.ID_ROW),
                    cursor.getString(DATABASE.NAME_ALARM_ROW),
                    calendar.getTimeInMillis(),
                    MainActivity.sortSimpleDateFormat.format(calendar.getTime()),
                    alarmToday);
        }
    }
    // kiểm tra đồng hồ
    private void checkAlarmData(Context context) {
        DATABASE database = new DATABASE(context);
        Cursor cursor = database.getData("SELECT * FROM " + DATABASE.NAME_TABLE);
        while (cursor.moveToNext()){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(DATABASE.TRIGGER_TIME_ROW));
            if(calendar.before(Calendar.getInstance())){
                // kiểm tra ngày nếu đứng sau thời điểm hiện tự tăng 1 ngày
                calendar.add(Calendar.DATE,1);
                database.queryData("UPDATE " + DATABASE.NAME_TABLE + " SET triggerTime = '"+calendar.getTimeInMillis()+"'");
            }
        }
//        Log.e(TAG, "checkAlarmData: " + "đã điều chỉnh ngày!" );
        MainActivity.showDetailAlarmInDatabase(context);
    }
    // tạo báo thức
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPendingAlarm(Context context, int idRequest, String name, long triggerTime, String time,Boolean alarmToday){
        Intent intent = new Intent("com.example.besalarm.action.WAKE_UP");
        intent.setPackage("com.example.besalarm");
        intent.putExtra("AlarmTitle",name);
        intent.putExtra("HOUR",time);
        intent.putExtra("HAVE_ALARM_TODAY",alarmToday);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,idRequest,intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,triggerTime,pendingIntent);
    }
}
