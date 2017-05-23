package com.plbtw.misskeen_app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.plbtw.misskeen_app.Client;
import com.plbtw.misskeen_app.Model.Recipe;
import com.plbtw.misskeen_app.Rest;
import com.scalified.fab.ActionButton;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.plbtw.misskeen_app.AppController;
import com.plbtw.misskeen_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class RecipeDetail extends AppCompatActivity {
    private static final String url = "http://ditoraharjo.co/misskeen/api/v1/recipe/".toString().trim();
    private ImageView imageView;
    private TextView txtrecipename,txtrecipedescription,txtrecipesteps,txtrecipeingredients;
    Bundle extras;
    String id;
    private ActionButton fab, fabdel;
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


        fab = (ActionButton) findViewById(R.id.edit_button);
        fabdel = (ActionButton) findViewById(R.id.delete_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Intent i = new Intent(getApplicationContext(), EditRecipe.class);
                b.putString("name", txtrecipename.getText().toString());
                b.putString("deskripsi", txtrecipedescription.getText().toString());
                b.putString("cara", txtrecipesteps.getText().toString());
                b.putString("bahan", txtrecipeingredients.getText().toString());
                i.putExtras(b);
                startActivity(i);
            }
        });
        fabdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RecipeDetail.this);
                alertDialogBuilder.setTitle("Konfirmasi");
                alertDialogBuilder.setMessage("Anda yakin akan menghapus resep ini?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Rest rest = Client.getClient().create(Rest.class);
                        Call<Recipe> call = rest.deleteRecipe(Integer.parseInt(id));
                        call.enqueue(new Callback<Recipe>() {
                            @Override
                            public void onResponse(Call<Recipe> call, retrofit2.Response<Recipe> response) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RecipeDetail.this);
                                alertDialogBuilder.setTitle("Berhasil!");
                                alertDialogBuilder.setMessage("Resep berhasil dihapus");
                                alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }

                            @Override
                            public void onFailure(Call<Recipe> call, Throwable t) {
                                Log.d("Error Edit Resep : ", t.toString());
                            }
                        });
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
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
