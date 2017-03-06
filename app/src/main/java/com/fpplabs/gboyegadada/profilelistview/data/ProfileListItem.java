package com.fpplabs.gboyegadada.profilelistview.data;

import org.parceler.Parcel;

/**
 * Created by Gboyega.Dada on 3/5/2017.
 */

@Parcel
public class ProfileListItem {
    private int id;
    private String name, handle, profilePic, url;

    public ProfileListItem() {
    }

    public ProfileListItem(int id, String handle, String name,
                           String profilePic, String url) {
        super();
        this.id = id;
        this.name = name;
        this.handle = handle;
        this.profilePic = profilePic;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
