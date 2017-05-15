package com.plbtw.misskeen_app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.plbtw.misskeen_app.Client;
import com.plbtw.misskeen_app.Helper.PrefHelper;
import com.plbtw.misskeen_app.Model.User;
import com.plbtw.misskeen_app.Model.UserObject;
import com.plbtw.misskeen_app.R;
import com.plbtw.misskeen_app.Rest;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Paulina on 5/15/2017.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editPassword;

    private Button btnLogin;
    private Button btnRegis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegis = (Button)findViewById(R.id.btnRegis);
    }

    public void buttonLogin(View v)
    {
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        UserObject user = new UserObject(editUsername.getText().toString(), editPassword.getText().toString());
        Rest rest = Client.getClient().create(Rest.class);
        Call<User> call = rest.getLogin(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.body().getStatus().equals("true"))
                {
                    PrefHelper.saveToPref(getApplicationContext(), "email", "password");
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
                else
                if(response.body().getStatus().equals("false"))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setMessage(response.body().getInfo());
                    alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(isOnline()) {
                    Log.d("Error Login : ", t.toString());
                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setMessage("Koneksi internet tidak tersedia");
                    alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

    }
    public boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
