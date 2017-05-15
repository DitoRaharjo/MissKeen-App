package com.plbtw.misskeen_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.plbtw.misskeen_app.Helper.PrefHelper;
import com.plbtw.misskeen_app.R;

/**
 * Created by Paulina on 5/15/2017.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mFlag = PrefHelper.getPref(getApplicationContext(),"ID");
        if(mFlag == null)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }, 5000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PrefHelper.saveToPref(getApplicationContext(),"ID","");
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }, 5000);
        }
        setContentView(R.layout.activity_splash_screen);
    }
}
