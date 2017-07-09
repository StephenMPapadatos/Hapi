package com.papadatos.steve.hapi.Utilities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.papadatos.steve.hapi.EmotionLog.EmotionLogFragment;
import com.papadatos.steve.hapi.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by steve on 2017-06-18.
 */

public final class NotificationScheduler {

    public static void scheduleNotification(Context context, String content, Date start, Date end, int amount) {
        Notification notification = getNotification(context, content);

        int delay = getDelay(start, end, amount);

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = System.currentTimeMillis() + delay;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }

    private static int getDelay(Date start, Date end, int amount) {
        int delay;
        int millisecondsInDay = 24 * 60 * 60 * 1000;

        Calendar cStart = Calendar.getInstance();
        cStart.setTime(start);
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTime(end);
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());

        int s = (cStart.get(Calendar.HOUR_OF_DAY) * 60 + cStart.get(Calendar.MINUTE)) * 60 * 1000;
        int e = (cEnd.get(Calendar.HOUR_OF_DAY) * 60 + cEnd.get(Calendar.MINUTE)) * 60 * 1000;

        if (e <= s) {
            e += millisecondsInDay;
        }

        int c = (current.get(Calendar.HOUR_OF_DAY) * 60 + current.get(Calendar.MINUTE)) * 60 * 1000;
        int interval = (e - s) / amount;
        int currentInterval = 5;

        for (int i = 0; i < amount; i++) {
            if (c >= s + interval * i && c < s + interval * (i + 1)) {
                currentInterval = i + 1;
            }
        }
        Random r = new Random();
        if (currentInterval < amount) {
            delay = ((s + (currentInterval * interval)) - c) + r.nextInt(interval);
        } else {
            delay = ((s + (currentInterval * interval)) - c) + (millisecondsInDay - (e - s)) + r.nextInt(interval);
        }

        return delay;
    }

    private static Notification getNotification(Context context, String content) {
        Intent clickedIntent = new Intent(context, EmotionLogFragment.class);
        clickedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingClickedIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), clickedIntent, 0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setContentIntent(pendingClickedIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(null, R.mipmap.ic_launcher_round));
        return builder.build();
    }

}
