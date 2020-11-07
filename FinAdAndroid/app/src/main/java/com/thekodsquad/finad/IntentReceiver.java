package com.thekodsquad.finad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class IntentReceiver extends BroadcastReceiver {

    final String tag = "IntentReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String data = intent.getStringExtra("notification");
            if (data.equals("week")) NotificationHelper.createWeek(context);
            if (data.equals("invest")) NotificationHelper.createInvestment(context);
            if (data.equals("warning")) NotificationHelper.createWarning(context);
        } catch (Exception e) {
            Toast.makeText(context, "Intercepted", Toast.LENGTH_LONG).show();
        }
    }
}