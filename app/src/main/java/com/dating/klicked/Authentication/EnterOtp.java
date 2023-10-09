package com.dating.klicked.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dating.klicked.MainActivity;
import com.dating.klicked.Model.RequestRepo.VerifyOTP_Body;
import com.dating.klicked.Model.ResponseRepo.UserResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.VerifyOtp_Presenter;
import com.dating.klicked.databinding.ActivityEnterOtpBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.SmsBroadcastReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.irozon.sneaker.Sneaker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class EnterOtp extends AppCompatActivity implements View.OnClickListener, VerifyOtp_Presenter.OTP_VerifyView {
    ActivityEnterOtpBinding binding;
    private View view;
    private Context context;
    private Dialog dialog;
    String Otp;
    VerifyOtp_Presenter presenter;
    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;
    boolean checkLogin, DeactivateCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_otp);
        view = binding.getRoot();
        context = EnterOtp.this;
        dialog = AppUtils.hideShowProgress(context);

        checkLogin = MyPreferences.getInstance(context).getBoolean(PrefConf.USER_LOGIN, false);

        presenter = new VerifyOtp_Presenter(this);
        startSmsUserConsent();

        binding.txtReady.setOnClickListener(this);
        binding.txtResend.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txt_ready:
                Otp = binding.enterOtp.getText().toString();

                if (Otp.isEmpty()) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Enter Your OTP  !!!!!")
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
                    VerifyOTP_Body body = new VerifyOTP_Body(MyPreferences.getInstance(context).getString(PrefConf.USER_NUMBER, null), Otp);

                    if (checkLogin == false) {
                        presenter.VerifyUser(body);
                    } else {
                        presenter.UserLogin(context, body);
                    }
                }

                break;

            case R.id.txt_resend:
                presenter.SendOTP(context, MyPreferences.getInstance(context).getString(PrefConf.USER_NUMBER, null));
                break;
        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {

            dialog.dismiss();
        }

    }

    @Override
    public void onError(String message) {

        if (message.equalsIgnoreCase("User is unverified. Please check your mail to verify")) {
            DeactivateCheck = true;
            VerifyOTP_Body body = new VerifyOTP_Body(MyPreferences.getInstance(context).getString(PrefConf.USER_NUMBER, null), Otp);
            presenter.VerifyUser(body);

        } else {
            Sneaker.with(EnterOtp.this)
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        }


    }


    @Override
    public void onSuccess(String message) {
        if (message.equalsIgnoreCase("ok")) {
            if (DeactivateCheck == true) {
                VerifyOTP_Body body = new VerifyOTP_Body(MyPreferences.getInstance(context).getString(PrefConf.USER_NUMBER, null), Otp);

                presenter.UserLogin(context, body);

            } else {
                startActivity(new Intent(EnterOtp.this, View_Flipper.class));
                MyPreferences.getInstance(EnterOtp.this).putString(PrefConf.USER_OTP, binding.enterOtp.getText().toString());
            }


        }

    }

    @Override
    public void onSendOTPSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(EnterOtp.this)
                    .setTitle("Successfully otp resend in Phone Number")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
        }
    }

    @Override
    public void onUserLoginSuccess(UserResponse responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            MyPreferences.getInstance(EnterOtp.this).putString(PrefConf.USER_OTP, binding.enterOtp.getText().toString());

            User_Data user_data = new User_Data(responseBody.getResult().getId(),
                    responseBody.getResult().getEmail(),
                    responseBody.getToken(),
                    responseBody.getResult().getMyReferalcode(),
                    responseBody.getResult().getFirstName(),
                    responseBody.getResult().getPhone(),
                    responseBody.getResult().getDob(),
                    responseBody.getResult().getGender(),
                    responseBody.getResult().getProfileImage(),
                    responseBody.getResult().getOccuptation());

            if (responseBody.getResult().getEmail() == null) {
                startActivity(new Intent(EnterOtp.this, View_Flipper.class));

            } else if (responseBody.getResult().getAudioDescription() == null) {
                startActivity(new Intent(EnterOtp.this, Audio_Des.class));

            } else if (responseBody.getResult().getSpecialFeature() == null && responseBody.getResult().getSpecialFeature().size() < 0) {
                startActivity(new Intent(EnterOtp.this, Add_Image.class));

            } else {
                SharedPrefManager.getInstance(EnterOtp.this).SetLoginData(user_data);
                startActivity(new Intent(EnterOtp.this, MainActivity.class));
                finish();
                MyPreferences.getInstance(EnterOtp.this).clearPreferences();
                MyPreferences.getInstance(EnterOtp.this).putString(PrefConf.Musicsong, PrefConf.IMAGE_URL + responseBody.getResult().getAudioDescription());
                Toast.makeText(context, "" + responseBody.getMessage(), Toast.LENGTH_SHORT).show();

            }


        }
    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(EnterOtp.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //  Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //  Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                //  Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                getOtpFromMessage(message);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            binding.enterOtp.setText(matcher.group(0));
        }
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }

                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

}