package com.todolist.reminder;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class AlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        String message=getIntent().getStringExtra("message");
        Bundle b= new Bundle();
        int pos=b.getInt("notiId");
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getSystemService(ns);
        nMgr.cancelAll();
        Toast.makeText(this,"NOTIFICATION ID IS "+pos,Toast.LENGTH_LONG).show();
        try {

            Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notify);
            r.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
