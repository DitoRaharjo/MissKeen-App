package com.plbtw.misskeen_app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.plbtw.misskeen_app.AppController;
import com.plbtw.misskeen_app.R;
import com.plbtw.misskeen_app.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

public class RecipeDetail extends AppCompatActivity {
    private static final String url = "http://ditoraharjo.co/misskeen/api/v1/recipe/".toString().trim();
    private ImageView imageView;
    private TextView txtrecipename,txtrecipedescription,txtrecipesteps,txtrecipeingredients;
    Bundle extras;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        imageView= (ImageView)findViewById(R.id.recipeimagedetail);
        txtrecipename=(TextView)findViewById(R.id.recipedetailname);
        txtrecipedescription=(TextView)findViewById(R.id.recipedetaildescription);
        txtrecipesteps=(TextView)findViewById(R.id.recipesteps);
        txtrecipeingredients=(TextView)findViewById(R.id.recipeingredients);
        extras = getIntent().getExtras();
        id=extras.getString("recipeid");
        getDataResep();

    }


    private void getDataResep() {


        JsonArrayRequest recipeReq = new JsonArrayRequest(url+id,
                new Response.Listener<JSONArray>() {

                    public void onResponse(JSONArray response) {

                            try {

                                JSONObject obj = response.getJSONObject(0);
                                Recipe recipe = new Recipe();
                                recipe.setRecipeid(obj.getString("id"));
                                recipe.setRecipethumbnail(obj.getString("image"));
                                recipe.setRecipedescription(obj.getString("description"));
                                recipe.setRecipename(obj.getString("name"));
                                recipe.setReciperating(obj.getString("rating"));
                                JSONArray ingredients = obj.getJSONArray("ingredients");

                                Picasso
                                        .with(RecipeDetail.this)
                                        .load(recipe.getRecipethumbnail()).memoryPolicy(MemoryPolicy.NO_CACHE) .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .into(imageView);
                                txtrecipename.setText(recipe.getRecipename());
                                txtrecipedescription.setText(recipe.getRecipedescription());
                                txtrecipesteps.setText(obj.getString("procedure"));
                                for (int i=0;i<ingredients.length();i++)
                                {

                                    txtrecipeingredients.setText("\n"+txtrecipeingredients.getText()+ingredients.getJSONObject(i).getString("name"));

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();


                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RecipeDetail.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


        AppController.getInstance().addToRequestQueue(recipeReq);
    }
}
