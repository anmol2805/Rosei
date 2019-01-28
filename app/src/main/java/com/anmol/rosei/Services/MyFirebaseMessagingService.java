package com.anmol.rosei.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import com.anmol.rosei.R;
import com.anmol.rosei.SplashActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String CHANNEL_ID = "rasoi_notification_id";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        createNotificationChannel();
        if(remoteMessage.getData().size()>0){
            Map<String,String> payload = remoteMessage.getData();
            showNotification(payload);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "rasoi notification channel";
            String description = "notifications to keep you updated about mess related news";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setLightColor(channel.getLightColor());
            channel.setVibrationPattern(new long[]{100,100,100,100});
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(Map<String, String> payload) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setColorized(true);
        builder.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        builder.setSmallIcon(R.drawable.ic_stat_rcolor);
        builder.setBadgeIconType(R.drawable.ic_stat_rcolor);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle(payload.get("title"));
        builder.setContentText(payload.get("body"));
        builder.setSound(alarmSound);
        builder.setVibrate(new long[] {100,100,100,100});
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        Intent resultIntent = new Intent(this, SplashActivity.class);
        TaskStackBuilder stackbuilder = TaskStackBuilder.create(this);
        stackbuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackbuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        builder.setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0,builder.build());
    }
}
