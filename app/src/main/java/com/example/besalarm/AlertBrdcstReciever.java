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
    public void onReceive(final Context context, Intent parintent) {
        Log.e(TAG, "onReceive: " + MainActivity.fullSimpleDateFormat.format(Calendar.getInstance().getTime()));
        if (parintent.getBooleanExtra("HAVE_ALARM_TODAY",false)) {
            // gửi thông báo
            recieverSendNotify(context,parintent);
            // bật nhạc chuông
            ringRingtone(context);
            new CountDownTimer(3000,3000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    // hết nhạc chuông - sau 3s cập nhật mới lại đồng hồ
                    Intent intent = new Intent("com.example.besalarm.action.GOT_NEW_ALARM");
                    intent.setPackage("com.example.besalarm");
                    context.sendBroadcast(intent);
                    return;
                }
            }.start();
        }
        // cập nhật đồng hồ
        Intent intent = new Intent("com.example.besalarm.action.GOT_NEW_ALARM");
        intent.setPackage("com.example.besalarm");
        context.sendBroadcast(intent);
        return;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void recieverSendNotify(Context context,Intent intent){
        String AlarmTitle = "Đã đến giờ " + intent.getStringExtra("AlarmTitle") + "!!!";
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context,App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle(intent.getStringExtra("HOUR"))
                .setContentText(AlarmTitle)
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
        CountDownTimer countDownTimer = new CountDownTimer(15000,15000) {
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
