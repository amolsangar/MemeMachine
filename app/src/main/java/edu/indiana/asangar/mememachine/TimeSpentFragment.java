package edu.indiana.asangar.mememachine;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/* TimeSpentFragment.java
 *
 * Java code for TimeSpent Fragment to show the amount of time spent on the app
 *
 * Created by: Amol Sangar
 * Created on: 2/22/23
 * Last Modified by: Amol Sangar
 * Last Modified on: 2/27/23
 * Project: A590 Android Development Final Project - Meme Machine
 * Part of: Meme Machine, refers to time_spent_fragment.xml layout file
 **/

public class TimeSpentFragment extends Fragment {
    private final String appPackageName = BuildConfig.APPLICATION_ID;
    TextView timeSpentToday;
    TextView timeSpentThisWeek;
    TextView timeSpentThisMonth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // View - Time Spent
        View timeSpentView = inflater.inflate(R.layout.time_spent_fragment,container,false);

        // Requesting usage permissions
        if(!isUsagePermissionGiven()) {
            requestUsagePermissions();
        }

        // Display notification for limiting daily usage
        MyNotification.notifyDailyLimit(getContext());

        // initializing our view
        timeSpentToday = timeSpentView.findViewById(R.id.TimeSpentTodayTV);
        timeSpentThisWeek = timeSpentView.findViewById(R.id.TimeSpentThisWeekTV);
        timeSpentThisMonth = timeSpentView.findViewById(R.id.TimeSpentThisMonthTV);

        // check if the app is allowed to fetch usage stats
        AppOpsManager appOps = (AppOpsManager) getContext().getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getContext().getPackageName());

        if(mode == AppOpsManager.MODE_ALLOWED) {
            UsageTracker usageTracker = new UsageTracker();
            long todayTimeSpent = usageTracker.calculateTimeSpent(getContext(), appPackageName, "DAILY");
            long weekTimeSpent = usageTracker.calculateTimeSpent(getContext(), appPackageName, "WEEKLY");
            long monthTimeSpent = usageTracker.calculateTimeSpent(getContext(), appPackageName, "MONTHLY");
            timeSpentToday.setText(String.format("%s", Utils.convertLongToTimeChar(todayTimeSpent)));
            timeSpentThisWeek.setText(String.format("%s", Utils.convertLongToTimeChar(weekTimeSpent)));
            timeSpentThisMonth.setText(String.format("%s", Utils.convertLongToTimeChar(monthTimeSpent)));
        }

        return timeSpentView;
    }

    /** Check if the permission for getting usage stats is given
     * Returns True if permission is given
     * Otherwise false
     * */
    public boolean isUsagePermissionGiven() {
        AppOpsManager appOps = (AppOpsManager) getContext().getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getContext().getPackageName());

        if(mode != AppOpsManager.MODE_ALLOWED) {
            return false;
        }
        return true;
    }

    /** Requests users permission to fetch usage statistics */
    private void requestUsagePermissions() {
        ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Replace current screen with itself to see permission changes
                        reloadScreen();
                    }
                });

        Intent askPermissionIntent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivityIntent.launch(askPermissionIntent);
    }

    /** Reloads the screen by replacing with a new instance of same fragment */
    public void reloadScreen() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TimeSpentFragment fragment = new TimeSpentFragment();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();
    }

    private long getUsageData(String freq) {
        long totalTimeUsageInMillis = 0;

        if (freq == "day") {
//            Calendar beginCal = Calendar.getInstance();
////            beginCal.add(Calendar.DATE, -1);
//
//            Date currentTime = Calendar.getInstance().getTime();
//            Log.d("AMOL - day", String.valueOf(currentTime));
//
//            SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
//            Date todaysDate = new Date(beginCal.getTimeInMillis());
//            Log.d("AMOL - day1",s.format(todaysDate));
//            Log.d("AMOL - day1",String.valueOf(todaysDate));
//
//            long millis = 0;
//            String dayStartTime = todaysDate + " 00:00:00";
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//                Date date = sdf.parse(dayStartTime);
//                millis = date.getTime();
//            } catch (Exception e) {
//                //The handling for the code
//                e.printStackTrace();
//            }
//
//            Log.d("AMOL - day2",s.format(millis));
//            Log.d("AMOL - day2", String.valueOf(millis));

            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date dateTime = calendar.getTime();
            long millis = dateTime.getTime();

            Calendar endCal = Calendar.getInstance();
            long startMillis = millis;
            long endMillis = endCal.getTimeInMillis();
            Log.d("AMOL - day1",String.valueOf(startMillis));
            Log.d("AMOL - day1",String.valueOf(endMillis));
//            Log.d("AMOL - day3", getUsageTime("edu.indiana.asangar.mememachine","DAILY",startMillis, endMillis));

            UsageStatsManager mUsageStatsManager = (UsageStatsManager) getContext().getSystemService(Context.USAGE_STATS_SERVICE);
            Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(startMillis, endMillis);
            // Filter Mememachine Usage
            totalTimeUsageInMillis = lUsageStatsMap.get("edu.indiana.asangar.mememachine").getTotalTimeInForeground();
        }
        else if (freq == "week") {
            // Start Time
            Calendar beginCal = Calendar.getInstance();
            beginCal.add(Calendar.DAY_OF_YEAR, -7);

            SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
            Log.d("AMOL - week", s.format(new Date(beginCal.getTimeInMillis())));
            // End Time
            Calendar endCal = Calendar.getInstance();

            long startMillis = beginCal.getTimeInMillis();
            long endMillis = endCal.getTimeInMillis();
            Log.d("AMOL - week1",String.valueOf(startMillis));
            Log.d("AMOL - week1",String.valueOf(endMillis));
//            Log.d("AMOL - week3", getUsageTime("edu.indiana.asangar.mememachine","WEEKLY",startMillis, endMillis));

            // Fetch usage for all apps
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) getContext().getSystemService(Context.USAGE_STATS_SERVICE);
            Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(startMillis, endMillis);
            // Filter Mememachine Usage
            totalTimeUsageInMillis = lUsageStatsMap.get("edu.indiana.asangar.mememachine").getTotalTimeInForeground();
        }
        else if (freq == "alltime") {
            // Start Time
            long startMillis = 18000000;
            // End Time
            long endMillis = System.currentTimeMillis();

            Log.d("AMOL - alltime1",String.valueOf(startMillis));
            Log.d("AMOL - alltime1",String.valueOf(endMillis));
//            Log.d("AMOL - alltime3", getUsageTime("edu.indiana.asangar.mememachine","MONTHLY", startMillis, endMillis));

            // Fetch usage for all apps
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) getContext().getSystemService(Context.USAGE_STATS_SERVICE);
            Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(startMillis, endMillis);
            // Filter Mememachine Usage
            totalTimeUsageInMillis = lUsageStatsMap.get("edu.indiana.asangar.mememachine").getTotalTimeInForeground();
        }

        return totalTimeUsageInMillis;
    }
}

