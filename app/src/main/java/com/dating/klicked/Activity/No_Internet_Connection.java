package com.dating.klicked.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.dating.klicked.Authentication.Login;
import com.dating.klicked.MainActivity;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.WelcomeScreen;
import com.dating.klicked.databinding.ActivityNoInternetConnectionBinding;
import com.dating.klicked.utils.CheckInternetConnection;

public class No_Internet_Connection extends AppCompatActivity {
    ActivityNoInternetConnectionBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_no_internet_connection);

        CheckInternetConnection network = new CheckInternetConnection(getApplicationContext());
        network.registerNetworkCallback();

        binding.txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckInternetConnection.Variables.isNetworkConnected) {
                    if (SharedPrefManager.getInstance(No_Internet_Connection.this).isLoggedIn()) {
                        startActivity(new Intent(No_Internet_Connection.this, MainActivity.class));
                        finish();

                    } else {
                        if (MyPreferences.getInstance(No_Internet_Connection.this).getBoolean(PrefConf.welcome, false) == false) {
                            startActivity(new Intent(No_Internet_Connection.this, WelcomeScreen.class));
                            finish();
                        } else {
                            startActivity(new Intent(No_Internet_Connection.this, Login.class));
                            finish();
                        }

                    }
                } else {
                    Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);

                }

            }
        });

    }
}