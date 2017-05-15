package com.plbtw.misskeen_app.Model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 5/15/2017.
 */
public class UserObject {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("id")
    private String id;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("telp")
    private String telp;
    @SerializedName("alamat")
    private String alamat;

    public UserObject(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public UserObject(String email, String password,String fullname,String telp,String alamat)
    {
        this.email = email;
        this.password = password;
        this.fullname=fullname;
        this.telp=telp;
        this.alamat=alamat;
    }
}
