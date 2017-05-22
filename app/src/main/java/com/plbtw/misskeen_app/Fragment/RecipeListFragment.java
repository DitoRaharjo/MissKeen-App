package com.plbtw.misskeen_app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.plbtw.misskeen_app.Activity.CreateRecipe;
import com.plbtw.misskeen_app.AppController;
import com.plbtw.misskeen_app.R;
import com.plbtw.misskeen_app.RecipeDetail;
import com.plbtw.misskeen_app.RecipeListAdapter;
import com.scalified.fab.ActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Paulina on 5/22/2017.
 */
public class RecipeListFragment extends ListFragment {


    private ImageLoader mImageLoader;
    private static final String url = "http://ditoraharjo.co/misskeen/api/v1/recipe".toString().trim();
    private ProgressDialog pDialog;
    private List<RecipeDetail> recipeDetailList = new ArrayList<>();
    private ListView listView;
    private RecipeListAdapter adapter;
    private ActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_recipe_list, container, false);
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        Log.wtf("List size", recipeDetailList.size()+"");
        listView = (ListView) getView().findViewById(R.id.recipelist);
        adapter = new RecipeListAdapter(this.getActivity(), recipeDetailList);
        listView.setAdapter(adapter);
        fab = (ActionButton) getView().findViewById(R.id.action_button);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        getDataResep();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recipeid = ((TextView) view.findViewById(R.id.recipeid)).getText().toString();
                Intent in = new Intent(getActivity(), com.plbtw.misskeen_app.Activity.RecipeDetail.class);
                in.putExtra("recipeid",recipeid);
                startActivity(in);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateRecipe.class);
                startActivity(i);
            }
        });
    }



    public static RecipeListFragment newInstance() {

        Bundle args = new Bundle();

        RecipeListFragment fragment = new RecipeListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void getDataResep() {
        pDialog.dismiss();
        JsonArrayRequest recipeReq = new JsonArrayRequest(url,
            new Response.Listener<JSONArray>() {
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            RecipeDetail recipeDetail = new RecipeDetail();
                            recipeDetail.setRecipeid(obj.getString("id"));
                            recipeDetail.setRecipethumbnail(obj.getString("image"));
                            recipeDetail.setRecipedescription(obj.getString("description"));
                            recipeDetail.setRecipename(obj.getString("name"));
                            recipeDetail.setReciperating(obj.getString("rating"));
                            recipeDetailList.add(recipeDetail);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        AppController.getInstance().addToRequestQueue(recipeReq);
    }

    private void showJSON(String json) {
        JSONArray recipe = null;
        ArrayList<HashMap<String, String>> recipelist = new ArrayList<HashMap<String, String>>();
        try {
            recipe = new JSONArray(json);
            for (int i = 0; i < recipe.length(); i++) {
                JSONObject c = recipe.getJSONObject(i);
                String id = c.getString("id");
                String name = c.getString("name");
                String description = c.getString("description");
                String image = c.getString("image");
                String rating = c.getString("rating");

                HashMap<String, String> recipes = new HashMap<String, String>();

                recipes.put("id", id);
                recipes.put("name", name);
                recipes.put("description", description);
                recipes.put("image", image);
                recipes.put("rating", rating);

                recipelist.add(recipes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                getContext(), recipelist, R.layout.recipe_item,
                new String[]{"id", "name", "description", "image", "rating"},
                new int[]{R.id.recipeid, R.id.recipename, R.id.recipedescription, R.id.recipethumbnail, R.id.reciperating}
        );
        listView.setAdapter(adapter);
        pDialog.dismiss();
    }
}
