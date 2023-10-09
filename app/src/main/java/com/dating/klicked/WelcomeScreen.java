package com.dating.klicked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dating.klicked.Authentication.Login;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.databinding.ActivityWelcomeScreenBinding;

public class WelcomeScreen extends AppCompatActivity {
    ActivityWelcomeScreenBinding binding;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_welcome_screen);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_screen);
        context = WelcomeScreen.this;

        binding.txtAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPreferences.getInstance(context).putBoolean(PrefConf.welcome, true);
                if (SharedPrefManager.getInstance(WelcomeScreen.this).isLoggedIn()) {
                    startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
                    finish();

                } else {
                    startActivity(new Intent(WelcomeScreen.this, Login.class));
                    finish();
                }
            }
        });


    }
}