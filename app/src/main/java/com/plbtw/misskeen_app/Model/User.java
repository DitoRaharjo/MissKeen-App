package com.plbtw.misskeen_app.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 5/15/2017.
 */
public class User {
    @SerializedName("status")
    private String status;
    @SerializedName("info")
    private String info;
    @SerializedName("user")
    private UserObject user;

    public UserObject getUser() {
        return user;
    }

    public String getInfo() {
        return info;
    }

    public String getStatus() {
        return status;
    }
}
