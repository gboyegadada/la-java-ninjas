package com.fpplabs.gboyegadada.profilelistview.adapter;

import com.fpplabs.gboyegadada.javaninjasla.R;
import com.fpplabs.gboyegadada.javaninjasla.AppController;
import com.fpplabs.gboyegadada.profilelistview.data.ProfileListItem;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Gboyega.Dada on 3/5/2017.
 */

public class ProfileListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ProfileListItem> profileListItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ProfileListAdapter(Activity activity, List<ProfileListItem> profileListItems) {
        this.activity = activity;
        this.profileListItems = profileListItems;
    }

    @Override
    public int getCount() {
        return profileListItems.size();
    }

    @Override
    public Object getItem(int location) {
        return profileListItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView url = (TextView) convertView
                .findViewById(R.id.url);
        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);

        ProfileListItem item = profileListItems.get(position);


        // Checking for null feed url
        name.setText(item.getName());
        url.setText(item.getUrl());

        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);


        return convertView;
    }
}
