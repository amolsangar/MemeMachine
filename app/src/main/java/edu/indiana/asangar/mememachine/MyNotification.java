package edu.indiana.asangar.mememachine;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;

/* MyNotification.java
 *
 * Java class to display notifications which can be used in other .java files
 *
 * Created by: Amol Sangar
 * Created on: 2/27/23
 * Last Modified by: Amol Sangar
 * Last Modified on: 2/27/23
 * Project: A590 Android Development Final Project - Meme Machine
 * Part of: Meme Machine, referred by all fragments
 **/

public class MyNotification {
    private static final String CHANNEL_ID = "1";
    private static int NOTIFY_ID = 1;

    /** Notification for crossing daily limit */
    public static void notifyDailyLimit(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean isNotificationEnabled = preferences.getBoolean("notifications", false);

        if (isNotificationEnabled) {
            int timeLimitDaily = 1500;
            try {
                timeLimitDaily = Integer.parseInt(preferences.getString("time_limit_daily", ""));
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
            UsageTracker usageTracker = new UsageTracker();
            long usageToday = usageTracker.calculateTimeSpent(context, BuildConfig.APPLICATION_ID, "DAILY");
            if ( usageToday > (timeLimitDaily * 60000) ) {
                String title = context.getResources().getString(R.string.notif_title);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                R.mipmap.ic_launcher))
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("You have reached your " + timeLimitDaily + " mins time limit on Meme Machine today\nYou can edit this limit in settings anytime"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);;

                createNotificationChannel(context);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(NOTIFY_ID, builder.build());
            }
        }
    }

    /** Creates notification channel */
    public static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getResources().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
