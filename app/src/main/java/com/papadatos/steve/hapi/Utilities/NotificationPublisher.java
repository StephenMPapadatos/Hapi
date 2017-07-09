package com.papadatos.steve.hapi.Utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.papadatos.steve.hapi.Controllers.UserController;

import java.util.Date;

import io.realm.Realm;

/**
 * Created by steve on 2017-06-17.
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    Context mContext;

    public void onReceive(Context context, Intent intent) {

        Realm.init(context);
        mContext = context;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

        Date start = UserController.getStart();
        Date end = UserController.getEnd();
        int amount = UserController.getAmount();
        if (start != null && end != null && amount != 0) {
            NotificationScheduler.scheduleNotification(context, "What's Up?", start, end, amount);
        }
    }

}
