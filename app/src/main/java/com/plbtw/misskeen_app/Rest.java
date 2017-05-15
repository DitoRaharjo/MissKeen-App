package com.plbtw.misskeen_app;

import com.plbtw.misskeen_app.Model.User;
import com.plbtw.misskeen_app.Model.UserObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Paulina on 5/15/2017.
 */
public interface Rest {
    //    @Headers({
//            "Content-Type : application json",
//            "X-Requested-With : XMLHttpRequest"
//    })
    @POST("user/auth")
    Call<User> getLogin(@Body UserObject user);
    @POST("user/register")
    Call<User> getRegis(@Body UserObject user);
}
