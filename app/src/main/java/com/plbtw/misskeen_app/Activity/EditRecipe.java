package com.plbtw.misskeen_app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.plbtw.misskeen_app.Client;
import com.plbtw.misskeen_app.Model.Recipe;
import com.plbtw.misskeen_app.Model.User;
import com.plbtw.misskeen_app.R;
import com.plbtw.misskeen_app.Rest;
import com.scalified.fab.ActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Paulina on 5/23/2017.
 */
public class EditRecipe extends AppCompatActivity {
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
    private  Bundle extras;
    private String nama, deskripsi, pembuatan, bahan;

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

        extras = new Bundle();
        extras = getIntent().getExtras();
        namaR.setText(extras.getString("name"));
        deskripsiR.setText(extras.getString("deskripsi"));
        caraR.setText(extras.getString("cara"));
//        bahanR.setextras.getString("bahan");
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
    }

    public void simpan()
    {
        Recipe recipe = new Recipe(namaR.getText().toString(), deskripsiR.getText().toString());
        Rest rest = Client.getClient().create(Rest.class);
        Call<Recipe> call = rest.editRecipe(recipe);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditRecipe.this);
                alertDialogBuilder.setTitle("Berhasil!");
                alertDialogBuilder.setMessage("Resep berhasil disimpan");
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
}
