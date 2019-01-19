package com.anmol.rosei;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {
    String CHANNEL_ID = "rasoi_notification_id";
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);

        showNotification(context);
    }
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Rasoi notification channel";
            String description = "notifications to keep you updated about latest IIIT news";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setLightColor(channel.getLightColor());
            channel.setVibrationPattern(new long[]{100,100,100,100});
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void showNotification(Context context) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
        builder.setColorized(true);
        builder.setColor(ContextCompat.getColor(context,R.color.colorPrimary));

        builder.setSmallIcon(R.drawable.rosei);
        builder.setBadgeIconType(R.drawable.rosei);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("Rasoi");
        builder.setContentText("book your coupon");
        builder.setSound(alarmSound);
        builder.setVibrate(new long[] {100,100,100,100});
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        Intent resultIntent = new Intent(context,RoseiActivity.class);
        TaskStackBuilder stackbuilder = TaskStackBuilder.create(context);
        stackbuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackbuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        builder.setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0,builder.build());
    }
}
