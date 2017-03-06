package com.fpplabs.gboyegadada.javaninjasla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fpplabs.gboyegadada.profilelistview.adapter.ProfileListAdapter;
import com.fpplabs.gboyegadada.profilelistview.data.ProfileListItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * Created by Gboyega.Dada on 3/4/2017.
 */

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private ProfileListAdapter listAdapter;
    private List<ProfileListItem> listItems;
    private String URL_FEED = "https://api.github.com/search/users?q=language:java+location:lagos,nigeria";

    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        listView.setItemsCanFocus(true);

        listItems = new ArrayList<ProfileListItem>();

        listAdapter = new ProfileListAdapter(this, listItems);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // first parameter is the context, second is the class of the activity to launch
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                ProfileListItem item = listItems.get(position);

                // Toast.makeText(getApplicationContext(), "Selected: "+item.getName(), Toast.LENGTH_LONG).show();

                // put "extras" into the bundle for access in the second activity
                i.putExtra("profile", Parcels.wrap(item));

                // brings up the second activity
                startActivity(i);
                // overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)
        // getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        // getActionBar().setIcon(
        //         new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("items");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                ProfileListItem item = new ProfileListItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("login"));
                item.setProfilePic(feedObj.getString("avatar_url"));
                item.setHandle(feedObj.getString("login"));
                item.setUrl(feedObj.getString("html_url"));

                listItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}