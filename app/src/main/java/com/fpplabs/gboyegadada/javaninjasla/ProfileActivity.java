package com.fpplabs.gboyegadada.javaninjasla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fpplabs.gboyegadada.profilelistview.data.ProfileListItem;

import org.parceler.Parcels;

/**
 * Created by Gboyega.Dada on 3/6/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        final ProfileListItem u = Parcels.unwrap(getIntent().getParcelableExtra("profile"));

        NetworkImageView avatar = (NetworkImageView) findViewById(R.id.profile_avatar);
        avatar.setImageUrl(u.getProfilePic(), imageLoader);
        TextView name = (TextView) findViewById(R.id.profile_name);
        name.setText('@' + u.getName());

        TextView url = (TextView) findViewById(R.id.profile_url);
        url.setText(Html.fromHtml("<a href=\"" + u.getUrl() + "\">"
                + u.getUrl() + "</a> "));
        // Making url clickable
        url.setMovementMethod(LinkMovementMethod.getInstance());

        getSupportActionBar().setTitle('@' + u.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle button click
        Button clickButton = (Button) findViewById(R.id.share_button);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();

                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @" + u.getHandle() + ", " + u.getUrl() + ".");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);

            }
        });


        // finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
