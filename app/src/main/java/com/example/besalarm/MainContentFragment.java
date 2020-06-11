package com.example.besalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class MainContentFragment extends Fragment{

    public static final String TAG = MainContentFragment.class.getSimpleName();

    public ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: " + (MainActivity.mAlarmAdapter == null));
        View fragment = inflater.inflate(R.layout.fragment_main_content,container,false);

        listView = (ListView) fragment.findViewById(R.id.mainfrag_alarm_lst);
        MainActivity.resetList();
        listView.setAdapter(MainActivity.mAlarmAdapter);

        Intent intent = new Intent("com.example.besalarm.action.GOT_NEW_ALARM");
        intent.setPackage("com.example.besalarm");
//        getContext().sendBroadcast(intent);
        // sau 10s reciever phải dc nhận dù app có bị kill hay không!
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),-1,intent,0 );
        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()+10000,pendingIntent);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: ");
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: ");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
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
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: ");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }
}
