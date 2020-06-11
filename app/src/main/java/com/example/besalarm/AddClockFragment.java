package com.example.besalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.besalarm.R;

import java.util.Calendar;

public class AddClockFragment extends Fragment {
    private static final String TAG = AddClockFragment.class.getSimpleName() ;
    TimePicker timePicker;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        final View view = inflater.inflate(R.layout.fragment_add_clock,container,false);

        timePicker = ((TimePicker)view.findViewById(R.id.add_clock_time_picker));
        timePicker.setIs24HourView(true);
        timePicker.setHour(0);
        timePicker.setMinute(0);

        ((Button)view.findViewById(R.id.add_add_clock_btn)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                calendar.set(Calendar.MINUTE,timePicker.getMinute());
                calendar.set(Calendar.SECOND,0);
                if(calendar.before(Calendar.getInstance())){
                    calendar.add(Calendar.DATE,1);
                }
                String nameOfAlarm = ((TextView)view.findViewById(R.id.add_title_clock_edt)).getText().toString().trim();

                Long triggerTime = calendar.getTimeInMillis();
                final int isActive = 1;

                int isActive2 = 0;
                ToggleButton toggleButton = (ToggleButton)view.findViewById(R.id.add_monday_tggbtn);
                if(toggleButton.isChecked()){
                    isActive2 = 1;
                }
                int isActive3 = 0;
                toggleButton = (ToggleButton)view.findViewById(R.id.add_tuestday_tggbtn);
                if(toggleButton.isChecked()){
                    isActive3 = 1;
                }
                int isActive4 = 0;
                toggleButton = (ToggleButton)view.findViewById(R.id.add_wednesday_tggbtn);
                if(toggleButton.isChecked()){
                    isActive4 = 1;
                }
                int isActive5 = 0;
                toggleButton = (ToggleButton)view.findViewById(R.id.add_thursday_tggbtn);
                if(toggleButton.isChecked()){
                    isActive5 = 1;
                }
                int isActive6 = 0;
                toggleButton = (ToggleButton)view.findViewById(R.id.add_friday_tggbtn);
                if(toggleButton.isChecked()){
                    isActive6 = 1;
                }
                int isActive7 = 0;
                toggleButton = (ToggleButton)view.findViewById(R.id.add_saturday_tggbtn);
                if(toggleButton.isChecked()){
                    isActive7 = 1;
                }
                int isActiveS = 0;
                toggleButton = (ToggleButton)view.findViewById(R.id.add_sunday_tggbtn);
                if(toggleButton.isChecked()){
                    isActiveS = 1;
                }
                String stringResult =  "null, "
                        +"'"+nameOfAlarm+"'"+","
                        +triggerTime+", "
                        +isActive+", "
                        +isActive2+", "
                        +isActive3+", "
                        +isActive4+", "
                        +isActive5+", "
                        +isActive6+", "
                        +isActive7+", "
                        +isActiveS+" ";
//                Log.e(TAG, "onClick: "+ stringResult);
//                Log.e(TAG, "onClick: " + MainActivity.fullSimpleDateFormat.format(calendar.getTime()));

                MainActivity.database.queryData("INSERT INTO "+DATABASE.NAME_TABLE+" VALUES( "+stringResult+" )");
                MainActivity.showDetailAlarmInDatabase(getContext());

                MainActivity.mfragmentManager.popBackStack();
                //


            }
        });
        return view;
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
