package com.example.easylearnsce.Class;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class OreoNotification extends ContextWrapper {
    private static final String CHANNEL_ID = "com.example.easylearnsce";
    private static final String CHANNEL_NAME = "easylearnchat";
    private NotificationManager notificationManager;
    public OreoNotification(Context context){
        super(context);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(false);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getNotificationManager(channel);
    }
    public NotificationManager getNotificationManager(NotificationChannel channel){
        if(notificationManager == null)
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return  notificationManager;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getOreaNotification(String Title, String Body, PendingIntent pendingIntent, Uri Sound, String Icon){
        return new Notification.Builder(getApplicationContext(),CHANNEL_ID).setContentIntent(pendingIntent).setContentTitle(Title).setContentText(Body).setSmallIcon(Integer.parseInt(Icon)).setSound(Sound).setAutoCancel(true);
    }
}