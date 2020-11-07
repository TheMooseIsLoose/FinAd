package com.thekodsquad.finad.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thekodsquad.finad.Loader;
import com.thekodsquad.finad.NotificationHelper;
import com.thekodsquad.finad.R;

import java.util.ArrayList;

import static com.thekodsquad.finad.NotificationHelper.WEEKLY_CHANNEL_ID;
import static com.thekodsquad.finad.NotificationHelper.createSimpleNotification;
import static com.thekodsquad.finad.NotificationHelper.testNotifications;

public class MainActivity extends AppCompatActivity {


    Loader loader = new Loader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Bottom Navigation View.
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);



        //Initialize NavController.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.overview_item) {
                    navController.navigate(R.id.overviewFragment);
                }
                if (item.getItemId() == R.id.budget_item) {
                    navController.navigate(R.id.budgetFragment);
                }
                if (item.getItemId() == R.id.statistics_item) {
                    navController.navigate(R.id.overviewFragment);
                }
                return true;
            }
        });



        createNotificationChannel();
    }

    public void loadData(View view) {
        Intent intent = new Intent(this, OverviewActivity.class);
        this.startActivity(intent);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "weekly";
            String description = "Weekly reports";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(WEEKLY_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void generateReport(View view) {
        NotificationHelper.testNotifications(this);
    }
}