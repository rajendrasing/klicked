package com.dating.klicked;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.dating.klicked.Activity.No_Internet_Connection;
import com.dating.klicked.Authentication.Login;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.databinding.ActivitySplashScreenBinding;
import com.dating.klicked.utils.CheckInternetConnection;

public class Splash_Screen extends AppCompatActivity {
    ActivitySplashScreenBinding binding;
    User_Data user_data;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_splash_screen);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        // Register Callback - Call this in your app start!
        CheckInternetConnection network = new CheckInternetConnection(getApplicationContext());
        network.registerNetworkCallback();


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        if (CheckInternetConnection.Variables.isNetworkConnected) {
                            if (SharedPrefManager.getInstance(Splash_Screen.this).isLoggedIn()) {
                                startActivity(new Intent(Splash_Screen.this, MainActivity.class));
                                finish();

                            } else {
                              /*  if (MyPreferences.getInstance(Splash_Screen.this).getBoolean(PrefConf.welcome, false) == false) {
                                    startActivity(new Intent(Splash_Screen.this, WelcomeScreen.class));
                                    finish();
                                } else {*/
                                    startActivity(new Intent(Splash_Screen.this, Login.class));
                                    finish();
                             //   }
                            }
                        } else {
                            startActivity(new Intent(Splash_Screen.this, No_Internet_Connection.class));
                            finish();
                        }


                    }
                }, 4200);

    }
}