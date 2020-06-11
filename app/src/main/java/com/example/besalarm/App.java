package com.example.besalarm;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID = "Alarm";
    @Override
    public void onCreate() {
        super.onCreate();
        creatNewNotificationChannel();
    }

    private void creatNewNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel alarmChannel = new NotificationChannel(
                    CHANNEL_1_ID,
                    CHANNEL_1_ID,
                    NotificationManager.IMPORTANCE_HIGH
            );
            alarmChannel.setDescription("This is " + CHANNEL_1_ID);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(alarmChannel);
        }
    }
}
