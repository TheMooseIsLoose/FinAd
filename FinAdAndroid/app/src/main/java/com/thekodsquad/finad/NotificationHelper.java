package com.thekodsquad.finad;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.thekodsquad.finad.activities.MainActivity;

public class NotificationHelper {
    public static final String WEEKLY_CHANNEL_ID = "weekly";

    public static ArrayList<Integer> NOTIFICATIONS = new ArrayList<>();
    private static int currentId = 0;

    public static void testNotifications(Context context) {
        createSimpleNotification(context, WEEKLY_CHANNEL_ID, "Weekly report", "Test", MainActivity.class);

        Intent testIntent = new Intent(context, MainActivity.class);
        testIntent.setAction("Test");
        testIntent.putExtra(Notification.EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getActivity(context, 0, testIntent, 0);

        ArrayList<NotificationCompat.Action> actions = new ArrayList<NotificationCompat.Action>();
        actions.add(new NotificationCompat.Action(R.drawable.ic_stat_name, "Test", snoozePendingIntent));

        NotificationHelper.createActionNotification(context, WEEKLY_CHANNEL_ID, "Action", "Notification with actions", MainActivity.class, actions);
    }

    public static int createSimpleNotification(Context context, String channelId, String title, String content, Class<?> activity) {

        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        return displayNotification(context, builder);
    }

    public static int createActionNotification(Context context, String channelId, String title, String content, Class<?> activity, List<NotificationCompat.Action> buttonActions) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        for (NotificationCompat.Action action :
                buttonActions) {
            builder.addAction(action);
        }

        return displayNotification(context, builder);
    }

    private static int displayNotification(Context context, NotificationCompat.Builder builder) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        currentId = currentId + 1;
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(currentId, builder.build());
        return currentId;
    }

}
