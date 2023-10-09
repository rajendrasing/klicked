package com.dating.klicked.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dating.klicked.Authentication.Adapter.CountryCodeAdapter;
import com.dating.klicked.Model.ResponseRepo.CountryCode;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.ViewPresenter.PhoneNumber_presenter;
import com.dating.klicked.databinding.ActivityPhoneNumberBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class PhoneNumber extends AppCompatActivity implements View.OnClickListener, PhoneNumber_presenter.PhoneNumberView {
    ActivityPhoneNumberBinding binding;
    private View view;
    private Context context;
    private Dialog dialog;
    private PhoneNumber_presenter presenter;
    String countryCode, userNumber;
    boolean checkProgress = false;
    private String compareValue = "IN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_number);
        view = binding.getRoot();
        context = PhoneNumber.this;
        dialog = AppUtils.hideShowProgress(context);

        presenter = new PhoneNumber_presenter(this);

        presenter.GetCountryCode(PhoneNumber.this);


        binding.imgBack.setOnClickListener(this);
        binding.txtReady.setOnClickListener(this);
        binding.backLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_back:
                onBackPressed();
                break;

            case R.id.back_login:
                onBackPressed();
                break;

            case R.id.txt_ready:
                if (binding.edPhoneNo.getText().toString().isEmpty()) {

                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Enter Your Phone No. !!!!!")
                            .setConfirmText("OK")
                            .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                            .setContentTextSize(14)
                            .setConfirmButtonBackgroundColor(getResources().getColor(R.color.global__primary))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    userNumber = countryCode + binding.edPhoneNo.getText().toString();
                    Log.d("userNumber", userNumber);
                    presenter.SignUP(context, userNumber);
                    //presenter.SendOTP(context, userNumber);

                }

                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onSaveInstanceState(new Bundle());
    }

    @Override
    public void showHideProgress(boolean isShow) {
       // Log.d("respossse","come"+isShow);

        if (isShow) {
            dialog.show();
        } else {
            if (checkProgress == false) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        dialog.dismiss();
                    }


                }, 1000);

            } else {
                dialog.dismiss();
            }


        }
    }

    @Override
    public void onError(String message) {
        if (message.equalsIgnoreCase("Contact number already exists") || message.equalsIgnoreCase("This phone is registered, but not verified")) {
            presenter.SendOTP(context, userNumber);
            MyPreferences.getInstance(context).putBoolean(PrefConf.USER_LOGIN, true);
        } /*else if (message.equalsIgnoreCase("Authenticate")){
            MyPreferences.getInstance(context).putString(PrefConf.COUNTRY_CODE, countryCode);
            MyPreferences.getInstance(context).putString(PrefConf.USER_NUMBER, userNumber);
            startActivity(new Intent(PhoneNumber.this, EnterOtp.class));
        }*/else {
            Sneaker.with(PhoneNumber.this)
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        }
    }

    @Override
    public void onGetCountryCodeSuccess(List<CountryCode> countryCodesList, String message) {
       checkProgress = true;
        if (countryCodesList.size() > 0) {

            CountryCodeAdapter countryCodeAdapter = new CountryCodeAdapter(PhoneNumber.this, countryCodesList);
            binding.countryCode.setAdapter(countryCodeAdapter);
            for (int i = 0; i < countryCodesList.size(); i++) {
                if (compareValue.equalsIgnoreCase(countryCodesList.get(i).getIsoName())) {
                    binding.countryCode.setSelection(i);
                }
            }
            binding.countryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    countryCode = countryCodesList.get(i).getCallingCodes().get(0);
                    MyPreferences.getInstance(PhoneNumber.this).putString(PrefConf.COUNTRY_NAME, countryCodesList.get(i).getName());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    @Override
    public void onSignUPSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            MyPreferences.getInstance(context).putBoolean(PrefConf.USER_LOGIN, false);
            Log.d("userNumber", userNumber);
            presenter.SendOTP(context, userNumber);
        }

    }

    @Override
    public void onSendOTPSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            MyPreferences.getInstance(context).putString(PrefConf.COUNTRY_CODE, countryCode);
            MyPreferences.getInstance(context).putString(PrefConf.USER_NUMBER, userNumber);
            startActivity(new Intent(PhoneNumber.this, EnterOtp.class));
        }

    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(PhoneNumber.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }
}