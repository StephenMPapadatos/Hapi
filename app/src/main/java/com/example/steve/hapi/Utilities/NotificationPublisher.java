package com.example.steve.hapi.Utilities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.example.steve.hapi.Controllers.UserController;
import com.example.steve.hapi.MainActivity;
import com.example.steve.hapi.R;

import java.util.Date;

/**
 * Created by steve on 2017-06-17.
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    Context mContext;

    public void onReceive(Context context, Intent intent) {

        mContext = context;

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

        Date start = UserController.getStart();
        Date end = UserController.getEnd();
        int amount = UserController.getAmount();
        if(start != null && end != null && amount != 0){
            NotificationScheduler.scheduleNotification(context, "What's Up?", start, end, amount);
        }
    }

}
