package com.example.besalarm;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Calendar;


public class UpdateClockFragment extends Fragment {
    private static final String TAG = UpdateClockFragment.class.getSimpleName() ;

    Clock clock;
    int idListClock;
    public UpdateClockFragment(Clock clock, int idListClock){
        this.clock = clock;
        this.idListClock = idListClock;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragment = inflater.inflate(R.layout.fragment_update_clock,null,false);
        //
        final DATABASE database = new DATABASE(getContext());
        final Cursor cursor = database.getData("SELECT * FROM " + DATABASE.NAME_TABLE);
        cursor.moveToPosition(idListClock);
        // set lại giá trị cho các view từ SQl đồng hồ của nó
        setViewValue(fragment,cursor);
        // nếu là change thì sẻ cập nhật lại toàn bộ giá trị của đồng hồ đó
        ((TextView)fragment.findViewById(R.id.update_update_clock_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "này là change", Toast.LENGTH_SHORT).show();
                ///////////////////////////////////////////////////////////////////////////////
                database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET nameAlarm = '"+
                        ((TextView)fragment.findViewById(R.id.update_title_clock_edt)).getText().toString().trim()
                        +"' WHERE Id = '" + clock.getDatabaseId() + "' ");

                TimePicker update_clock_time_picker = (TimePicker)fragment.findViewById(R.id.update_clock_time_picker);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(clock.getCalendar().getTimeInMillis());
                calendar.set(Calendar.HOUR_OF_DAY,update_clock_time_picker.getHour());
                calendar.set(Calendar.MINUTE,update_clock_time_picker.getMinute());
                database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET triggerTime = '"+
                        calendar.getTimeInMillis()
                        +"' WHERE Id = '" + clock.getDatabaseId() + "' ");

                if(((ToggleButton)fragment.findViewById(R.id.update_monday_tggbtn)).isChecked()){
                    database.queryData("UPDATE " + DATABASE.NAME_TABLE + " SET isActive2 = '" + 1 + "' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }else {
                    database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET isActive2 = '"+ 0 +"' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }
                if(((ToggleButton)fragment.findViewById(R.id.update_tuestday_tggbtn)).isChecked()){
                    database.queryData("UPDATE " + DATABASE.NAME_TABLE + " SET isActive3 = '" + 1 + "' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }else {
                    database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET isActive3 = '"+ 0 +"' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }
                if(((ToggleButton)fragment.findViewById(R.id.update_wednesday_tggbtn)).isChecked()){
                    database.queryData("UPDATE " + DATABASE.NAME_TABLE + " SET isActive4 = '" + 1 + "' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }else {
                    database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET isActive4 = '"+ 0 +"' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }
                if(((ToggleButton)fragment.findViewById(R.id.update_thursday_tggbtn)).isChecked()){
                    database.queryData("UPDATE " + DATABASE.NAME_TABLE + " SET isActive5 = '" + 1 + "' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }else {
                    database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET isActive5 = '"+ 0 +"' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }
                if(((ToggleButton)fragment.findViewById(R.id.update_friday_tggbtn)).isChecked()){
                    database.queryData("UPDATE " + DATABASE.NAME_TABLE + " SET isActive6 = '" + 1 + "' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }else {
                    database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET isActive6 = '"+ 0 +"' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }
                if(((ToggleButton)fragment.findViewById(R.id.update_saturday_tggbtn)).isChecked()){
                    database.queryData("UPDATE " + DATABASE.NAME_TABLE + " SET isActive7 = '" + 1 + "' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }else {
                    database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET isActive7 = '"+ 0 +"' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }
                if(((ToggleButton)fragment.findViewById(R.id.update_sunday_tggbtn)).isChecked()){
                    database.queryData("UPDATE " + DATABASE.NAME_TABLE + " SET isActiveS = '" + 1 + "' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }else {
                    database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET isActiveS = '"+ 0 +"' WHERE Id = '" + clock.getDatabaseId() + "' ");
                }

                //end action update/////////////////////////////////////////////////////
                MainActivity.cancelPendingToAlert(getContext(),clock.getDatabaseId());
                MainActivity.mfragmentManager.popBackStack();
            }
        });
        ((TextView)fragment.findViewById(R.id.update_remove_clock_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // xóa data trong sql - hủy PendingIntent
                database.queryData("DELETE FROM " + DATABASE.NAME_TABLE + " WHERE ID = '"+clock.getDatabaseId()+"' ");
                MainActivity.cancelPendingToAlert(getContext(),clock.getDatabaseId());
                //end action remove
                MainActivity.mfragmentManager.popBackStack();
            }
        });

        //
        return fragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setViewValue(View view, Cursor cursor){
        ((TextView)view.findViewById(R.id.update_title_clock_edt)).setText(MainActivity.mClocks.get(idListClock).getName());

        Calendar calendar = MainActivity.mClocks.get(idListClock).getCalendar();
        TimePicker update_clock_time_picker = (TimePicker)view.findViewById(R.id.update_clock_time_picker);
        update_clock_time_picker.setIs24HourView(true);
        update_clock_time_picker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        update_clock_time_picker.setMinute(calendar.get(Calendar.MINUTE));

        ((ToggleButton)view.findViewById(R.id.update_monday_tggbtn))
                .setChecked(MainActivity.mClocks.get(idListClock).getActiveDayOfWeek(Clock.MONDAY));
        ((ToggleButton)view.findViewById(R.id.update_tuestday_tggbtn))
                .setChecked(MainActivity.mClocks.get(idListClock).getActiveDayOfWeek(Clock.TUESTDAY));
        ((ToggleButton)view.findViewById(R.id.update_wednesday_tggbtn))
                .setChecked(MainActivity.mClocks.get(idListClock).getActiveDayOfWeek(Clock.WEDNESDAY));
        ((ToggleButton)view.findViewById(R.id.update_thursday_tggbtn))
                .setChecked(MainActivity.mClocks.get(idListClock).getActiveDayOfWeek(Clock.THURSDAY));
        ((ToggleButton)view.findViewById(R.id.update_friday_tggbtn))
                .setChecked(MainActivity.mClocks.get(idListClock).getActiveDayOfWeek(Clock.FRIDAY));
        ((ToggleButton)view.findViewById(R.id.update_saturday_tggbtn))
                .setChecked(MainActivity.mClocks.get(idListClock).getActiveDayOfWeek(Clock.SATURDAY));
        ((ToggleButton)view.findViewById(R.id.update_sunday_tggbtn))
                .setChecked(MainActivity.mClocks.get(idListClock).getActiveDayOfWeek(Clock.SUNDAY));
    }
}
