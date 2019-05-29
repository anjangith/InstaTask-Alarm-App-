package com.todolist.reminder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;

public class SoundService extends Service {
    int soundset;
    int vibrationset;
    private Vibrator vibrator;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vibrator=(Vibrator)this.getSystemService(VIBRATOR_SERVICE);
        SharedPreferences sharedPreferences=this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        soundset=sharedPreferences.getInt("soundkey",1);
        vibrationset=sharedPreferences.getInt("vibrationkey",0);
        if(soundset==1) {
            try {
                Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(this, notify);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(soundset==2) {
            try {
                Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                Ringtone r = RingtoneManager.getRingtone(this, notify);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(vibrationset==1) {
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(2000, 10));
            } else {
                vibrator.vibrate(2000);
            }

    }


}
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {

            Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notify);
            r.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}
