package com.fpplabs.gboyegadada.javaninjasla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fpplabs.gboyegadada.profilelistview.data.ProfileListItem;

import org.parceler.Parcels;

/**
 * Created by Gboyega.Dada on 3/6/2017.
 */

public class ProfileActivity extends Activity {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfileListItem u = Parcels.unwrap(getIntent().getParcelableExtra("profile"));

        NetworkImageView avatar = (NetworkImageView) findViewById(R.id.profile_avatar);
        avatar.setImageUrl(u.getProfilePic(), imageLoader);

        TextView name = (TextView) findViewById(R.id.profile_name);
        name.setText(Html.fromHtml("<a href=\"" + u.getUrl() + "\">"
                + u.getName() + "</a> "));
        // Making url clickable
        name.setMovementMethod(LinkMovementMethod.getInstance());


        // finish();
    }
}
