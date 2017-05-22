package com.plbtw.misskeen_app.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.plbtw.misskeen_app.AppController;
import com.plbtw.misskeen_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                                com.plbtw.misskeen_app.RecipeDetail recipeDetail = new com.plbtw.misskeen_app.RecipeDetail();
                                recipeDetail.setRecipeid(obj.getString("id"));
                                recipeDetail.setRecipethumbnail(obj.getString("image"));
                                recipeDetail.setRecipedescription(obj.getString("description"));
                                recipeDetail.setRecipename(obj.getString("name"));
                                recipeDetail.setReciperating(obj.getString("rating"));
                                JSONArray ingredients = obj.getJSONArray("ingredients");

                                Picasso
                                        .with(RecipeDetail.this)
                                        .load(recipeDetail.getRecipethumbnail()).memoryPolicy(MemoryPolicy.NO_CACHE) .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .into(imageView);
                                txtrecipename.setText(recipeDetail.getRecipename());
                                txtrecipedescription.setText(recipeDetail.getRecipedescription());
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
