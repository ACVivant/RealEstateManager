package com.openclassrooms.realestatemanager.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.openclassrooms.realestatemanager.R;

/**
 * Created by Anne-Charlotte Vivant on 10/06/2019.
 */
public class RemApp extends Application {

    public static final String CHANNEL_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannelREM();
    }

    private void createNotificationChannelREM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Real Estate Manager Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(getResources().getString(R.string.channel_description));

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
