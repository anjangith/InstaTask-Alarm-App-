package com.todolist.reminder;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class AlarmNotificationService extends IntentService {
    int notifyId=0;
    int importance;
    private static int id=12345;
    String channelId="my_channel_01";
    CharSequence name="name_of_channel";


    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        importance= NotificationManager.IMPORTANCE_HIGH;
        NotificationManager manager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel mChannel=new NotificationChannel(channelId,name,NotificationManager.IMPORTANCE_HIGH);

            manager.createNotificationChannel(mChannel);
        }
        Intent yes = new Intent(this, NotificationYes.class);
        yes.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent1 =
                PendingIntent.getActivity(
                        this,
                        0,
                        yes,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        Intent no = new Intent(this, NotificationNo.class);

        PendingIntent pendingIntent2 =
                PendingIntent.getActivity(
                        this,
                        0,
                        no,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.ic_alarm_add_black_24dp)
                //example for large icon
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Have you completed your task?")
                .setContentText("Speak the truth")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true).addAction(R.drawable.ic_alarm_black_24dp,"YES",pendingIntent1).addAction(R.drawable.ic_delete_forever_black_48dp,"NO",pendingIntent2);

        // example for blinking LED
        //   builder.setLights(0xFFb71c1c, 1000, 2000);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        //    builder.setContentIntent(pendingIntent);
        manager.notify(notifyId, builder.build());
        Intent i=new Intent(this,SoundService.class);
        this.stopService(i);
    }
}
