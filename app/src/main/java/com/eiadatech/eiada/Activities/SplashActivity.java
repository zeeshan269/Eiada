package com.eiadatech.eiada.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Session;

public class SplashActivity extends AppCompatActivity {
    private int progressValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initializeViews();

    }


    private void initializeViews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressValue < 100) {

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressValue += 1;

                }

                if (Session.getRememberPassword(SplashActivity.this).equalsIgnoreCase("true")) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }).start();
    }

}
