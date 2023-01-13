package com.example.reminderapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class Notification extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String text = bundle.getString("event");
            String date = bundle.getString("date") + " " + bundle.getString("time");

            Log.d("TITLE", "TITLE ON SET LISTNER:                     " + text);
            Log.d("DATECLICKED", "DATE CLICKED ON SET LISTNER:        " + date);

            //Click on Notification
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent1.putExtra("message", 1000);

            //Notification Builder
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_IMMUTABLE);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001");


            //here we set all the properties for the notification
            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
//            contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
            PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            contentView.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent);
            contentView.setTextViewText(R.id.message, text);
            contentView.setTextViewText(R.id.date, date);
            mBuilder.setSmallIcon(R.drawable.ic_baseline_alarm_24);
            mBuilder.setAutoCancel(true);
            mBuilder.setOngoing(true);
            mBuilder.setAutoCancel(true);
            mBuilder.setPriority(android.app.Notification.PRIORITY_HIGH);
            mBuilder.setOnlyAlertOnce(true);
            mBuilder.build().flags = android.app.Notification.FLAG_NO_CLEAR | android.app.Notification.PRIORITY_HIGH;
            mBuilder.setContent(contentView);
            mBuilder.setContentIntent(pendingIntent);

            //we have to create notification channel after api level 26
            int pendingFlags;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
                String channelId = "channel_id";
                NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
                mBuilder.setChannelId(channelId);
            }
            else{
                pendingFlags = PendingIntent.FLAG_ONE_SHOT;
            }

            android.app.Notification notification = mBuilder.build();
            notificationManager.notify(1, notification);


        }
}

