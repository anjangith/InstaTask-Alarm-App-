package com.todolist.reminder;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import static android.content.Context.ALARM_SERVICE;


/**
 * Created by HP on 24-12-2017.
 */

public class TimeSet extends DialogFragment {
    private TimePicker timePicker;
    private int hour,minute;
    private Button continuebtn;
    private Button continuebtn1;
    private int day,month,year;
    private DatePicker datePicker;
    private AlarmManager alarmManager;
    private Calendar calendar;
    private String hr,mi,dy,mo,ye;
    private String text;
    private ArrayList<Model> item;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.clockfragment,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        timePicker=(TimePicker)rootView.findViewById(R.id.timePicker);
        continuebtn=(Button)rootView.findViewById(R.id.continuebtn);
        continuebtn1=(Button)rootView.findViewById(R.id.continuebtn2);
        datePicker=(DatePicker)rootView.findViewById(R.id.datePicker);
        Bundle args = getArguments();
        text = args.getString("text");
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();
                SharedPreferences appSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = appSharedPreference.getString("anjan", null);
                Type type = new TypeToken<ArrayList<Model>>() {
                }.getType();
                item = gson.fromJson(json, type);
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
                int minimum = 2;
                int maximum = 998;
                int ran1 = ThreadLocalRandom.current().nextInt(minimum, maximum + 1);

                hr = Integer.toString(hour);
                mi = Integer.toString(minute);
                dy = Integer.toString(day);
                mo = Integer.toString(month + 1);
                ye = Integer.toString(year);
                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                intent.putExtra("message", text);
              //  PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), ran1, intent, PendingIntent.FLAG_UPDATE_CURRENT );
              //  alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                setAlarm(getActivity(),ran1,calendar.getTimeInMillis());
                Model md = new Model(text, hr, mi, ran1, dy, mo, ye);
                if(item==null){
                    item=new ArrayList<>();
                }
                item.add(md);
                MainActivity.adapter = new CustomAdapter(getActivity(), item);
                MainActivity.adapter.notifyDataSetChanged();
                saveToPreference();

                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);



            }
        });

        continuebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return rootView;
}

    private void saveToPreference() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefEditor = appSharedPrefs.edit();
        Gson gSon = new Gson();
        String json1 = gSon.toJson(item);
        prefEditor.putString("anjan", json1);
        prefEditor.commit();
    }

    public void setAlarm(Context mContext,int requestCode,long time) {

        Intent myIntent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, myIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        cancelAlarmIfExists(mContext,requestCode,myIntent);

        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
    }

    private void cancelAlarmIfExists(Context mContext, int requestCode, Intent myIntent) {
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am=(AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
            am.cancel(pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
