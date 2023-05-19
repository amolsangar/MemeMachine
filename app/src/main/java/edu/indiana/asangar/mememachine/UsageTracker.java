package edu.indiana.asangar.mememachine;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* UsageTracker.java
 *
 * Java class for tracking the users time on the app
 *
 * Created by: Amol Sangar
 * Created on: 2/27/23
 * Last Modified by: Amol Sangar
 * Last Modified on: 2/27/23
 * Project: A590 Android Development Final Project - Meme Machine
 * Part of: Meme Machine, referred by all fragments
 **/

public class UsageTracker {

    /** Calculates the duration of time based on interval
     * @param context
     * @param appPackageName
     * @param interval
     * @return time in milliseconds
     */
    public long calculateTimeSpent(Context context, String appPackageName, String interval) {
        // For start time
        Calendar beginCal = Calendar.getInstance();
        // For end time
        Calendar endCal = Calendar.getInstance();
        long startMillis = 0;
        long endMillis = 0;
        long result = 0;

        switch(interval) {
            case "DAILY":
                Date dateNow = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateNow);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date dateTime = calendar.getTime();
                long millis = dateTime.getTime();
                startMillis = millis;
                endMillis = endCal.getTimeInMillis();
                result = getUsageTime(context, appPackageName, interval, startMillis, endMillis);
                break;

            case "WEEKLY":
                // Start Time
                beginCal.add(Calendar.DAY_OF_YEAR, -7);
                startMillis = beginCal.getTimeInMillis();
                endMillis = endCal.getTimeInMillis();
                result = getUsageTime(context, appPackageName, interval, startMillis, endMillis);
                break;

            case "MONTHLY":
                beginCal.add(Calendar.MONTH, -1);
                startMillis = beginCal.getTimeInMillis();
                endMillis = endCal.getTimeInMillis();
                result = getUsageTime(context, appPackageName, interval, startMillis, endMillis);
                break;

            default:
                result = getUsageTime(context, appPackageName,"DAILY",startMillis, endMillis);
        }

        return result;
    }

    // Burak - https://stackoverflow.com/questions/36238481/android-usagestatsmanager-not-returning-correct-daily-results
    /** Gets the usage statistics
     * @param context
     * @param appPackageName
     * @param interval
     * @param startTime
     * @param endTime
     * @return time in milliseconds
     */
    public long getUsageTime(Context context, String appPackageName, String interval, long startTime, long endTime)
    {
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> usageStatsList;

        switch(interval) {
            case "DAILY":
            case "WEEKLY":
            default:
                usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
                break;
            case "MONTHLY":
                usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_WEEKLY, startTime, endTime);
                break;
        }

        long time = 0;
        for (int i=0; i<usageStatsList.size(); i++) {
            UsageStats stat = usageStatsList.get(i);
            if(stat.getPackageName().equals(appPackageName)) {
                time += stat.getTotalTimeInForeground();
            }
        }
        return time;
    }

}
