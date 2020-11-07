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


    }

    public static void createWeek(Context context) {
        createSimpleNotification(context, WEEKLY_CHANNEL_ID, "New week", "Check how you manged your budget this week!", MainActivity.class);
    }

    public static void createInvestment(Context context) {
        Intent testIntent = new Intent(context, MainActivity.class);
        testIntent.setAction("Invest");
        testIntent.putExtra(Notification.EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getActivity(context, 0, testIntent, 0);

        ArrayList<NotificationCompat.Action> actions = new ArrayList<NotificationCompat.Action>();
        actions.add(new NotificationCompat.Action(R.drawable.ic_stat_name, "Invest", snoozePendingIntent));

        NotificationHelper.createActionNotification(context, WEEKLY_CHANNEL_ID, "End of month", "You have 253 EUR left this month. Would you like to invest it?", MainActivity.class, actions);
    }

    public static void createWarning(Context context) {
        createSimpleNotification(context, WEEKLY_CHANNEL_ID, "Warning", "You are in risk of exceeding your budget for Transportation this month!", MainActivity.class);
    }

    public static int createSimpleNotification(Context context, String channelId, String title, String content, Class<?> activity) {

        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        return displayNotification(context, builder);
    }

    public static int createActionNotification(Context context, String channelId, String title, String content, Class<?> activity, List<NotificationCompat.Action> buttonActions) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

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
