package edu.indiana.asangar.mememachine;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/* HomeFragment.java
 *
 * Java code for Home Fragment to display memes
 *
 * Created by: Amol Sangar
 * Created on: 2/22/23
 * Last Modified by: Amol Sangar
 * Last Modified on: 5/19/23
 * Project: A590 Android Development Final Project - Meme Machine
 * Part of: Meme Machine, refers to home_fragment.xml layout file
 **/

public class HomeFragment extends Fragment {

    private static final String TAG = "MemeMachine";

    /** creating variables for our request queue,
    * array list, progressbar, edittext,
    * image button and our recycler view. */
    private ProgressBar progressBar;
    private String selection;
    private String subredditName = "";
    private int subRedditPostCount = 5;

    RecyclerView homeRV;
    HomeRVAdapter adapter;
    private ArrayList<IMemeModal> memeModalArrayList;

    boolean isLoading = false;
    private boolean useTestMemes = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /** Getter to retrieve the currently set subreddit name */
    public String getCurrentSubredditName() {
        return this.subredditName;
    }

    /** Getter to retrieve the currently set subreddit post count */
    public int getCurrentSubredditPostCount() {
        return this.subRedditPostCount;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // View - Home
        View homeView = inflater.inflate(R.layout.home_fragment,container,false);

        // initializing our view
        progressBar = homeView.findViewById(R.id.idLoadingPB);

        // initialize array list
        memeModalArrayList = new ArrayList<>();

        // RecycleView adapter: below line we are creating an adapter class and adding our array list in it
        adapter = new HomeRVAdapter(memeModalArrayList, getContext());

        // Display notification for limiting daily usage
        MyNotification.notifyDailyLimit(getContext());

        // Home RecycleView
        homeRV = homeView.findViewById(R.id.idRVHome);

        // below line is for setting linear layout manager to our recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        // below line is to set layout manager to our recycler view
        homeRV.setLayoutManager(linearLayoutManager);

        // below line is to set adapter to our recycler view
        homeRV.setAdapter(adapter);

        // calling method to load data in recycler view
        if(useTestMemes) {
            memeModalArrayList.addAll(new TestMemeModal().getMemeModal(subredditName, subRedditPostCount));
            progressBar.setVisibility(View.GONE);
        }
        else {
            memeModalArrayList.addAll(getMemeModal(subredditName, subRedditPostCount));
        }

        // Refresh adapter
        adapter.notifyDataSetChanged();

        // initialize scroll listening
        initScrollListener();

        return homeView;
    }

    public void onEvent() {
        getMemeModal(this.getCurrentSubredditName(),this.getCurrentSubredditPostCount());
    }

    /** Fetches memes in MemeModal */
    public ArrayList<IMemeModal> getMemeModal(String subredditName,int count) {
        ArrayList<IMemeModal> modalArrayList;
        RequestQueue mRequestQueue;

        // initialize array list
        modalArrayList = new ArrayList<>();

        // below line is use to initialize the variable for our request queue.
        mRequestQueue = Volley.newRequestQueue(getContext());

        // below line is use to clear cache this will
        // be use when our data is being updated.
        mRequestQueue.getCache().clear();

        // below is the url for getting data
        // from API in json format.
        String url = "";
        if(subredditName == "") {
            url = String.format("https://meme-api.com/gimme/%s", count);
        }
        else {
            url = String.format("https://meme-api.com/gimme/%s/%s", subredditName, count);
        }

        // Retrieve filter NSFW flag
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Boolean isFilterNSFWEnabled = preferences.getBoolean("filter_nsfw", true);

        // below line we are creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    int count = response.getInt("count");
                    JSONArray dataArray = response.getJSONArray("memes");
                    for (int i = 0; i < count; i++) {
                         JSONObject dataObj = dataArray.getJSONObject(i);
                         if(isFilterNSFWEnabled && dataObj.optString("nsfw") == "true") {
                             continue;
                         }
                        IMemeModal memeModal = new RedditMemeModal(dataObj);

                        // below line is use to add modal class to our array list
                        memeModalArrayList.add(memeModal);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    // handling error case.
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Client Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling error message.
                Toast.makeText(getContext(), "Server Error: Failed to fetch memes", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

        return modalArrayList;
    }

    private void initScrollListener() {
        homeRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView homeRV, int newState) {
                super.onScrollStateChanged(homeRV, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView homeRV, int dx, int dy) {
                super.onScrolled(homeRV, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) homeRV.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == memeModalArrayList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    /** load more memes once the user reaches the scroll end */
    private void loadMore() {
        memeModalArrayList.add(null);
        adapter.notifyItemInserted(memeModalArrayList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                memeModalArrayList.removeAll(Collections.singleton(null));
                int scrollPosition = memeModalArrayList.size();
                adapter.notifyItemRemoved(scrollPosition);
                if(useTestMemes) {
                    memeModalArrayList.addAll(new TestMemeModal().getMemeModal(subredditName, subRedditPostCount));
                }
                else {
                    memeModalArrayList.addAll(getMemeModal(subredditName, subRedditPostCount));
                }
                adapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    /** Helper function to show toast messages */
    void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_select_subreddit, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.SettingsMenu:
                try {
                    openSelectSubredditDialog();
                }
                catch (Exception error) {
                    Log.d(TAG + "onOptionsItemSelected:", String.valueOf(error));
                }
                return true;
        }
        return false;
    }

    /** Dialog to select source for displaying posts */
    private void openSelectSubredditDialog() throws XmlPullParserException, IOException {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String subredditNames = preferences.getString("subreddit_names", "");
        String[] subredditArr = subredditNames.trim().split(",");

        AlertDialog.Builder selectSubredditDialog = new AlertDialog.Builder(getContext());
        selectSubredditDialog.setTitle(R.string.select_subreddit);

        ArrayList<String> list = new ArrayList<String>();
        for (String s : subredditArr)
            if (!s.equals(""))
                list.add(s);
        subredditArr = list.toArray(new String[list.size()]);

        if(subredditArr.length > 0) {
            String[] finalSubredditArr = subredditArr;
            selectSubredditDialog.setSingleChoiceItems(subredditArr, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    selection = finalSubredditArr[i];
                }
            });

            selectSubredditDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    showToast("Showing " + selection);
                    subredditName = selection;
                    memeModalArrayList.clear();
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.VISIBLE);
                    if(useTestMemes) {
                        memeModalArrayList.addAll(new TestMemeModal().getMemeModal(subredditName, subRedditPostCount));
                    }
                    else {
                        memeModalArrayList.addAll(getMemeModal(subredditName, subRedditPostCount));
                    }
                    adapter.notifyDataSetChanged();
                }
            });

            selectSubredditDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Do nothing
                }
            });
        }
        else {
            // Set the message show for the Alert time
            selectSubredditDialog.setMessage("Source not set in settings");
        }

        selectSubredditDialog.show();
    }
}
