package com.example.besalarm;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class AlertBrdcstReciever extends BroadcastReceiver {
    private static final String TAG = AlertBrdcstReciever.class.getSimpleName() ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: " + MainActivity.fullSimpleDateFormat.format(Calendar.getInstance().getTime()));

        recieverSendNotify(context);
        ringRingtone(context);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void recieverSendNotify(Context context){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context,App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle("From besalarm")
                .setContentText("Là ok nếu mày thấy tin này!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();
        notificationManagerCompat.notify(1,notification);
    }
    public void ringRingtone(Context context){
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(uri == null){
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        final Ringtone ringtone = RingtoneManager.getRingtone(context,uri);
        ringtone.play();
        CountDownTimer countDownTimer = new CountDownTimer(11000,10000) {
            @Override
            public void onTick(long millisUntilFinished){}
            @Override
            public void onFinish() {
                ringtone.stop();
            }
        };
        countDownTimer.start();
    }
}
