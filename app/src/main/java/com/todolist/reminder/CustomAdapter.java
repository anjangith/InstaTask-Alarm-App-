package com.todolist.reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;



/**
 * Created by HP on 25-12-2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    public static ArrayList<Model> models;
    private AlarmManager alarmManager;

    public CustomAdapter(Context context,ArrayList<Model> model){
        this.context=context;
        this.models=model;
        alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
       return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, final ViewGroup viewGroup) {
    convertView=null;
    if(convertView==null){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.listitem,null);
        TextView img=(TextView) convertView.findViewById(R.id.textView);
        TextView hrtext=(TextView)convertView.findViewById(R.id.hourtext);
        TextView mitext=(TextView)convertView.findViewById(R.id.mintext);
        ImageButton button=(ImageButton) convertView.findViewById(R.id.cancelbtn);
        TextView day=(TextView)convertView.findViewById(R.id.day);
        TextView month=(TextView)convertView.findViewById(R.id.month);
        TextView year=(TextView)convertView.findViewById(R.id.year);
        Button editBtn=(Button)convertView.findViewById(R.id.editbtn);

        button.setTag(i);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle("CANCEL TASK")
                        .setMessage("Are you sure you want to cancel?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(context,AlarmReceiver.class);
                                Model old=models.get(i);
                                int ran1=old.getRan1();
                                PendingIntent pendingIntent=PendingIntent.getBroadcast(context,ran1,intent,PendingIntent.FLAG_UPDATE_CURRENT|Intent.FILL_IN_DATA);
                                alarmManager.cancel(pendingIntent);
                                MainActivity.itemModel.remove(i);
                                MainActivity.adapter.notifyDataSetChanged();
                                SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor prefEditor = appSharedPrefs.edit();
                                Gson gSon = new Gson();
                                String json1 = gSon.toJson(MainActivity.itemModel);
                                prefEditor.putString("anjan", json1);
                                prefEditor.commit();
                                Toast.makeText(context,"Task cancelled",Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("No", null).show();


            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity=(FragmentActivity)context;
                android.support.v4.app.FragmentManager fm=activity.getSupportFragmentManager();
                Bundle args=new Bundle();
                args.putInt("pos",i);
                Edit edit=new Edit();
                edit.setArguments(args);
                edit.show(fm,"Edit");

            }
        });
        Model m=models.get(i);
        img.setText(m.getTasktext());
        hrtext.setText(m.getHr());
        mitext.setText(m.getMin());
        day.setText(m.getDay());
        month.setText(m.getMonth());
        year.setText(m.getYear());


    }
    return convertView;
    }
}
