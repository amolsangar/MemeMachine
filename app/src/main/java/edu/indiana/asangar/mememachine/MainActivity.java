package edu.indiana.asangar.mememachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

/* MainActivity.java
 *
 * Java code for Main Activity
 *
 * Created by: Amol Sangar
 * Created on: 2/20/23
 * Last Modified by: Amol Sangar
 * Last Modified on: 2/27/23
 * Project: A590 Android Development Final Project - Meme Machine
 * Part of: Meme Machine, refers to activity_main.xml layout file
 **/

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MemeMachine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Display notification for limiting daily usage
        MyNotification.notifyDailyLimit(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.homepage);

        // This code handles orientation issue with fragments
        // By default, it loads the HomeFragment if no fragment is set
        // Otherwise, on screen rotation it fetches the current fragment to display
        // (earlier it used to jump on the HomeFragment on screen rotation)
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if(currentFragment == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            HomeFragment fragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
        }
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameLayout, currentFragment).commit();
        }

    }

    /** Closes keyboard when clicked outside */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handles navigation view item clicks
        int id = item.getItemId();

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.homepage) {
            fragment = new HomeFragment();
        } else if (id == R.id.build_your_own) {
            fragment = new BuildMemeFragment();
        } else if (id == R.id.time_spent) {
            fragment = new TimeSpentFragment();
        } else if (id == R.id.settings) {
            fragment = new SettingsFragment();
        }

        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /** HomeFragment Show More Button OnClickHandler
     *  Called when the user touches the button on the bottom of the homepage */
    public void showMore(View view)
    {
        HomeFragment homeFrag = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        homeFrag.getMemeModal(homeFrag.getCurrentSubredditName(), homeFrag.getCurrentSubredditPostCount());
    }

}