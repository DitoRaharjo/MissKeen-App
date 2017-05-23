package com.plbtw.misskeen_app;

import com.plbtw.misskeen_app.Model.IngredientObject;
import com.plbtw.misskeen_app.Model.Recipe;
import com.plbtw.misskeen_app.Model.User;
import com.plbtw.misskeen_app.Model.UserObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    @POST("recipe")
    Call<Recipe> createRecipe(@Body Recipe recipe);
    @GET("ingredient")
    Call<List<IngredientObject>> getAllIngredients();
    @PATCH("recipe")
    Call<Recipe> editRecipe(@Body Recipe recipe);
    @DELETE("recipe")
    Call<Recipe> deleteRecipe(@Query("id") int id);
}
