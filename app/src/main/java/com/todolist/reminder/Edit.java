package com.todolist.reminder;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class Edit extends DialogFragment {

    private Button cancel;
    private Button conti;
    private Calendar calendar;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private int hour,minute,day,month,year;
    private String hr,mi,ye,dy,mo;
    private int position;
    private ArrayList<Model> item;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.editview, container, false);
        timePicker=(TimePicker)v.findViewById(R.id.timePicker1);
        datePicker=(DatePicker)v.findViewById(R.id.datePicker1);
        conti=(Button)v.findViewById(R.id.continuebtn1);
        Toast.makeText(getActivity(),"Position is: "+position,Toast.LENGTH_LONG).show();
        cancel=(Button)v.findViewById(R.id.continuebtn3);
        position=getArguments().getInt("pos");
        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences appSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = appSharedPreference.getString("anjan", null);
                Type type = new TypeToken<ArrayList<Model>>() {
                }.getType();
                item = gson.fromJson(json, type);
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute );
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                int difference = (int) (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis());
                if (difference > 0) {
                    calendar.set(Calendar.DAY_OF_MONTH, day + 1);
                    day = day + 1;
                }
                hr = Integer.toString(hour);
                mi = Integer.toString(minute);
                dy = Integer.toString(day);
                mo = Integer.toString(month + 1);
                ye = Integer.toString(year);
                Model md=CustomAdapter.models.get(position);
                md.setHr(hr);
                md.setDay(dy);
                md.setMin(mi);
                md.setMonth(mo);
                md.setYear(ye);
                item.set(position,md);
                MainActivity.adapter = new CustomAdapter(getActivity(), item);
                MainActivity.adapter.notifyDataSetChanged();
                saveToPreference();
                setAlarm(getActivity(),md.getRan1(),calendar.getTimeInMillis());

                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }

    private void saveToPreference() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefEditor = appSharedPrefs.edit();
        Gson gSon = new Gson();
        String json1 = gSon.toJson(item);
        prefEditor.putString("anjan", json1);
        prefEditor.commit();
    }
    public void setAlarm(Context mContext, int requestCode, long time) {

        Intent myIntent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, myIntent,0);

        cancelAlarmIfExists(mContext,requestCode,myIntent);

        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);
    }

    private void cancelAlarmIfExists(Context mContext, int requestCode, Intent myIntent) {
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, myIntent,0);
            AlarmManager am=(AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
            am.cancel(pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
