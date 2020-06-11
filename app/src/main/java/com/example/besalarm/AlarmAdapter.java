package com.example.besalarm;

import android.app.AlarmManager;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class AlarmAdapter extends BaseAdapter {
    public static final String TAG = AlarmManager.class.getSimpleName();

    private MainActivity mainActivity;
    private ArrayList<Clock> clocks;

    public AlarmAdapter(MainActivity mainActivity, ArrayList<Clock> clocks){
        this.mainActivity = mainActivity;
        this.clocks = clocks;
    }

    @Override
    public int getCount() {
        return clocks.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = mainActivity.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.alarm_adapter_view,null);
        // nếu nhấp vào đồng hồ sẻ hiện ra màn hình thay đổi
        LinearLayout alarm_adapter_lineanr = (LinearLayout)convertView.findViewById(R.id.alarm_adapter_lineanr);
        alarm_adapter_lineanr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getIntoUpdateFragment(clocks.get(i),i);
            }
        });

        setViewValue(i,alarm_adapter_lineanr);

        // nếu tắt active => cập nhật sql
        Switch adapter_active_swtch = (Switch)alarm_adapter_lineanr.findViewById(R.id.adapter_active_swtch);
        adapter_active_swtch.setChecked(clocks.get(i).getActive());
        adapter_active_swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MainActivity.database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET isActive = '"+1+"' WHERE Id = '"+clocks.get(i).getDatabaseId()+"' ");
                }else {
                    MainActivity.database.queryData("UPDATE "+DATABASE.NAME_TABLE+" SET isActive = '"+0+"' WHERE Id = '"+clocks.get(i).getDatabaseId()+"' ");
                }
                MainActivity.resetList();
            }
        });


        return alarm_adapter_lineanr;
    }

    private void setViewValue(int i, View convertView){
        // thể hiện thời gian báo thức
        ((TextView)convertView.findViewById(R.id.adapter_name_alarm_txt)).setText(clocks.get(i).getName());
        ((TextView)convertView.findViewById(R.id.adapter_showtime_alarm_txt)).setText(
                MainActivity.sortSimpleDateFormat.format(clocks.get(i).getCalendar().getTime()));
        // nếu ngày trong tuần được active nó sẻ có màu xanh
        if(clocks.get(i).getActiveDayOfWeek(Clock.MONDAY) == true){
            ((TextView)convertView.findViewById(R.id.adapter_2_txt)).setTextColor(mainActivity.getResources().getColor(R.color.color_simple_theme));
        }else{
            ((TextView)convertView.findViewById(R.id.adapter_2_txt)).setTextColor(Color.BLACK);
        }
        if(clocks.get(i).getActiveDayOfWeek(Clock.TUESTDAY) == true){
            ((TextView)convertView.findViewById(R.id.adapter_3_txt)).setTextColor(mainActivity.getResources().getColor(R.color.color_simple_theme));
        }else{
            ((TextView)convertView.findViewById(R.id.adapter_3_txt)).setTextColor(Color.BLACK);
        }
        if(clocks.get(i).getActiveDayOfWeek(Clock.WEDNESDAY) == true){
            ((TextView)convertView.findViewById(R.id.adapter_4_txt)).setTextColor(mainActivity.getResources().getColor(R.color.color_simple_theme));
        }else{
            ((TextView)convertView.findViewById(R.id.adapter_4_txt)).setTextColor(Color.BLACK);
        }
        if(clocks.get(i).getActiveDayOfWeek(Clock.THURSDAY) == true){
            ((TextView)convertView.findViewById(R.id.adapter_5_txt)).setTextColor(mainActivity.getResources().getColor(R.color.color_simple_theme));
        }else{
            ((TextView)convertView.findViewById(R.id.adapter_5_txt)).setTextColor(Color.BLACK);
        }
        if(clocks.get(i).getActiveDayOfWeek(Clock.FRIDAY) == true){
            ((TextView)convertView.findViewById(R.id.adapter_6_txt)).setTextColor(mainActivity.getResources().getColor(R.color.color_simple_theme));
        }else{
            ((TextView)convertView.findViewById(R.id.adapter_6_txt)).setTextColor(Color.BLACK);
        }
        if(clocks.get(i).getActiveDayOfWeek(Clock.SATURDAY) == true){
            ((TextView)convertView.findViewById(R.id.adapter_7_txt)).setTextColor(mainActivity.getResources().getColor(R.color.color_simple_theme));
        }else{
            ((TextView)convertView.findViewById(R.id.adapter_7_txt)).setTextColor(Color.BLACK);
        }
        if(clocks.get(i).getActiveDayOfWeek(Clock.SUNDAY) == true){
            ((TextView)convertView.findViewById(R.id.adapter_S_txt)).setTextColor(mainActivity.getResources().getColor(R.color.color_simple_theme));
        }else{
            ((TextView)convertView.findViewById(R.id.adapter_S_txt)).setTextColor(Color.BLACK);
        }

    }



    //
}
