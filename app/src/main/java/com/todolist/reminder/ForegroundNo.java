package com.todolist.reminder;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ForegroundNo extends AppCompatActivity {

    private ArrayList<TaskModel> modelList;
    private TextView taskName;
    private TextView taskDate;
    private TextView taskHasDone;
    private Button statsBtn;
    private Button mainBtn;
    private TaskModel tempModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_yes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        taskName=(TextView)findViewById(R.id.taskname);
        taskDate=(TextView)findViewById(R.id.taskdate);
        taskHasDone=(TextView)findViewById(R.id.taskstatus);
        statsBtn=(Button)findViewById(R.id.statsBtn);
        mainBtn=(Button)findViewById(R.id.mainBtn);
        modelList=getTasksFromSP(this);
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getSystemService(ns);
        nMgr.cancelAll();
        if(modelList==null){
            modelList=new ArrayList<>();
            Toast.makeText(ForegroundNo.this,"Error",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(ForegroundNo.this,"Task is: "+modelList.get(modelList.size()-1).getTaskName(),Toast.LENGTH_LONG).show();
            tempModel=modelList.get(modelList.size()-1);
            tempModel.setTaskComplete(true);
            modelList.set(modelList.size()-1,tempModel);
            saveTasksToSP(this,modelList);

        }

        taskName.setText("TASK NAME: "+tempModel.getTaskName());
        taskDate.setText("TASK TIME: "+tempModel.getDay()+"/ "+tempModel.getMonth()+"/ "+tempModel.getYear());
        taskHasDone.setText("STATUS: "+(modelList.get(modelList.size()-1).isTaskComplete()));

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ForegroundNo.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ForegroundNo.this,StatsActivity.class);
                startActivity(i);
            }
        });


    }


    public ArrayList<TaskModel> getTasksFromSP(Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("message",Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("favourites","");
        ArrayList<TaskModel> list=new ArrayList<>();
        list=gson.fromJson(json,new TypeToken<ArrayList<TaskModel>>(){}.getType());
        return list;
    }
    public void saveTasksToSP(Context context,ArrayList<TaskModel> fav){

        SharedPreferences preferences=context.getSharedPreferences("message",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(fav);
        editor.putString("favourites",json);
        editor.commit();

    }

}
