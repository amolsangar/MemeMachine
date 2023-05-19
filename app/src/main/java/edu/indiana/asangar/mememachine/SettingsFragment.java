package edu.indiana.asangar.mememachine;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

/* SettingsFragment.java
 *
 * Java code for Settings Fragment to manage app settings
 *
 * Created by: Amol Sangar
 * Created on: 2/22/23
 * Last Modified by: Amol Sangar
 * Last Modified on: 2/27/23
 * Project: A590 Android Development Final Project - Meme Machine
 * Part of: Meme Machine, refers to settings.xml xml file
 **/

public class SettingsFragment extends PreferenceFragmentCompat {

    EditTextPreference time_limit_daily;
    SwitchPreferenceCompat notifications;
    SwitchPreferenceCompat filter_nsfw;
    ActivityResultLauncher<Intent> requestPermissionLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display notification for limiting daily usage
        MyNotification.notifyDailyLimit(getContext());

        // initializing our view
        time_limit_daily = findPreference("time_limit_daily");
        notifications = findPreference("notifications");
        filter_nsfw = findPreference("filter_nsfw");

        if(!isUsagePermissionGiven()) {
            notifications.setChecked(false);
            // Ask for UsagePermissions
            requestPermissionLauncher = getUsagePermissionsLauncher();
        }

        // Toggles display of time limit field on click of notification switch
        SwitchPreferenceCompat notifications = findPreference("notifications");
        notifications.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (!isUsagePermissionGiven()) {
                    Intent askPermissionIntent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    requestPermissionLauncher.launch(askPermissionIntent);
                }
                toggleNotificationSettings();
                return true;
            }
        });

        // Removes alphabets in daily time limit field when changed
        // https://stackoverflow.com/questions/13596250/how-to-listen-for-preference-changes-within-a-preferencefragment
        Preference pref = findPreference("time_limit_daily");
        pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // true to update the state of the Preference with the new value
                // in case you want to disallow the change return false
                String time_limit = (String) newValue;
                boolean atleastOneAlpha = time_limit.matches(".*[a-zA-Z]+.*");
                if (atleastOneAlpha) {
                    time_limit = time_limit.replaceAll("[^0-9]", "");
                    time_limit_daily.setText(time_limit);
                    return false;
                }

                return true;
            }
        });
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

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        toggleNotificationSettings();
    }

    /** Toggles 'time_limit_daily' based on notification switch */
    public void toggleNotificationSettings() {
        time_limit_daily = findPreference("time_limit_daily");
        notifications = findPreference("notifications");

        if(notifications.isChecked()) {
            if (time_limit_daily != null) {
                time_limit_daily.setVisible(true);
            }
        }
        else {
            if (time_limit_daily != null) {
                time_limit_daily.setVisible(false);
            }
        }
    }

    /** Create activity launcher for UsagePermission
     * Used later on the click of notification switch
     * @return ActivityResultLauncher
     */
    private ActivityResultLauncher<Intent> getUsagePermissionsLauncher() {
        AppOpsManager appOps = (AppOpsManager) getContext().getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getContext().getPackageName());

        if(mode != AppOpsManager.MODE_ALLOWED) {
            ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            // Replace current screen with itself to see permission changes
                            reloadScreen();
                        }
                    });

            return startActivityIntent;
        }
        return null;
    }

    /** Reloads the screen by replacing with a new instance of same fragment */
    void reloadScreen() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SettingsFragment fragment = new SettingsFragment();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();
    }
}