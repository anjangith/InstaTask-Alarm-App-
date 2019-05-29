package com.todolist.reminder;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by HP on 04-01-2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private Vibrator vibrator;
    private int pos;
    private int soundset;
    private int vibrationset;
    private ArrayList<Model> itemModel;
    private ArrayList<TaskModel> modelItems;
    int notifyId = 0;
    int importance;
    private static int id = 12345;
    String channelId = "my_channel_01";
    CharSequence name = "name_of_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, SoundService.class));

        //This will send a notification message and show notification in notification tray
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmNotificationService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
    }
}
