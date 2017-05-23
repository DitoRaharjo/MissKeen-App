package com.plbtw.misskeen_app.Activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.plbtw.misskeen_app.Client;
import com.plbtw.misskeen_app.Model.IngredientObject;
import com.plbtw.misskeen_app.Model.Ingredients;
import com.plbtw.misskeen_app.Model.Recipe;
import com.plbtw.misskeen_app.Model.User;
import com.plbtw.misskeen_app.R;
import com.plbtw.misskeen_app.Rest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRecipe extends AppCompatActivity {
    private EditText namaR;
    private EditText deskripsiR;
    private EditText caraR;
    private EditText porsiR;
    private EditText jumlahB;
    private EditText satuanB;
    private EditText deskripsiB;

    private Spinner bahanR;
    private Button btnBahan;
    private Button btnSimpan;
    private ImageView btnTambahGambar;
    private ArrayList<String> ingredientList=new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        namaR = (EditText) findViewById(R.id.etNama);
        deskripsiR = (EditText) findViewById(R.id.etDescription);
        bahanR = (Spinner) findViewById(R.id.etIngredient);
        caraR = (EditText) findViewById(R.id.etCooking);
        porsiR = (EditText) findViewById(R.id.etPortion);
        jumlahB = (EditText) findViewById(R.id.etAmount);
        satuanB = (EditText) findViewById(R.id.etSatuan);
        deskripsiB = (EditText) findViewById(R.id.etDeskripsiB);

        btnBahan = (Button) findViewById(R.id.btnaddIngredient);
        btnSimpan = (Button) findViewById(R.id.btnSubmit);
        btnTambahGambar = (ImageView) findViewById(R.id.ivAdd);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSimpan(v);
            }
        });
        getBahan();
    }

    public void getBahan()
    {
        Rest rest = Client.getClient().create(Rest.class);
        Call<List<IngredientObject>> call = rest.getAllIngredients();
        call.enqueue(new Callback<List<IngredientObject>>() {
            @Override
            public void onResponse(Call<List<IngredientObject>> call, Response<List<IngredientObject>> response) {
                Log.wtf("Response size",response.body().size()+"");
                for(int i = 0; i<response.body().size(); i++)
                {
                    ingredientList.add(response.body().get(i).getNama());
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ingredientList);
                bahanR.setAdapter(adapter);
//                bahanR.notifyAll();
            }

            @Override
            public void onFailure(Call<List<IngredientObject>> call, Throwable t) {
                Log.d("Error Get Bahan : ", t.toString());
            }
        });
        bahanR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int id_bahan = bahanR.getId();
                Log.wtf("Response size",id_bahan+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }
    public void buttonBahan(View v)
    {
        final Spinner spinner = new Spinner(getApplicationContext());
        spinner.setAdapter(adapter);
        spinner.setPopupBackgroundResource(R.color.fab_material_black);
        int gray = Color.GRAY;
        spinner.setBackgroundColor(gray);
        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.laySpinner);
        rlayout.addView(spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int id_bahan2 = spinner.getId();
                Log.wtf("Response size",id_bahan2+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void buttonSimpan(View v)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateRecipe.this);
        alertDialogBuilder.setTitle("Konfirmasi");
        alertDialogBuilder.setMessage("Yakin akan menyimpan resep ini?");
        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Rest rest = Client.getClient().create(Rest.class);
                IngredientObject ingredientObject = new IngredientObject("Daging Ayam", "200", "gram", "dicincang kasar");
//        Ingredients ingredients = new Ingredients(ingredientObject);
                Recipe recipe = new Recipe("Nasi Goreng", "Nasi goreng kecap, mudah, cepat, enak", "1. Panaskan minyak 2. Masukkan nasi putih 3. Tambahkan kecap dan daging ayam", "1", ingredientObject , null);
//        Recipe recipe = new Recipe(namaR.getText().toString(), deskripsiR.getText().toString(), caraR.getText().toString(), porsiR.getText().toString(), bahanR.getSelectedItem().toString(), "");
                Call<Recipe> call = rest.createRecipe(recipe);
                call.enqueue(new Callback<Recipe>() {
                    @Override
                    public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                        //tersimpan
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateRecipe.this);
                        alertDialogBuilder.setTitle("Berhasil!");
                        alertDialogBuilder.setMessage("Resep berhasil disimpan");
                        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onFailure(Call<Recipe> call, Throwable t) {
                        Log.d("Error Create Resep : ", t.toString());
                    }
                });
            }
        });
        alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
