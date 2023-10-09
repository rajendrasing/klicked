package com.dating.klicked.Authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dating.klicked.MainActivity;
import com.dating.klicked.Model.ResponseRepo.UserResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.LoginPresenter;
import com.dating.klicked.databinding.ActivityLoginBinding;
import com.dating.klicked.utils.AppUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.irozon.sneaker.Sneaker;

import org.json.JSONObject;

import java.util.Arrays;

public class Login extends AppCompatActivity implements View.OnClickListener, LoginPresenter.LoginView {
    ActivityLoginBinding binding;
    private Context context;
    private Dialog dialog;
    private LoginPresenter presenter;
    private View view;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    private CallbackManager callbackManager;
    private AccessToken mAccessToken;
    AccessTokenTracker accessTokenTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);
        // Passing Login in Facebook SDK.
        FacebookSdk.sdkInitialize(Login.this);

        AppUtils.checkAndRequestPermissions(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        view = binding.getRoot();
        context = Login.this;
        presenter = new LoginPresenter(this);
        dialog = AppUtils.hideShowProgress(context);
        binding.txtLogin.setOnClickListener(this);


        initview();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_login:
                startActivity(new Intent(Login.this, PhoneNumber.class));
               // startActivity(new Intent(Login.this, Add_Image.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public void showHideLoginProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void onLoginError(String message) {
        if (message.equalsIgnoreCase("Do not exist")) {
            startActivity(new Intent(Login.this, PhoneNumber.class));
            mGoogleSignInClient.signOut();
            LoginManager.getInstance().logOut();

        } else {
            Sneaker.with(this)
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        }

    }

    @Override
    public void onSocialLoginSuccess(UserResponse responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
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

            SharedPrefManager.getInstance(Login.this).SetLoginData(user_data);
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
            MyPreferences.getInstance(Login.this).clearPreferences();
            mGoogleSignInClient.signOut();
            LoginManager.getInstance().logOut();
            Toast.makeText(context, "" + responseBody.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onLoginFailure(Throwable t) {
        Sneaker.with(this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    private void initview() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        callbackManager = CallbackManager.Factory.create();

        binding.loginButton.setReadPermissions(Arrays.asList("user_friends", "email", "public_profile"));


        if (AccessToken.getCurrentAccessToken() != null) {

            getUserProfile(AccessToken.getCurrentAccessToken());

            // If already login in then show the Toast.
            Sneaker.with(Login.this)
                    .setTitle("Already logged in")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        } else {

            // If not login in then show the Toast.
          /*  Sneaker.with(Login.this)
                    .setTitle("User not logged in")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();*/


        }

        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //  mAccessToken = loginResult.getAccessToken();
                getUserProfile(loginResult.getAccessToken());

                //  Toast.makeText(Login.this, "mAccessToken" +loginResult.getAccessToken(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {

                //   Toast.makeText(Login.this, "Cancelled", Toast.LENGTH_SHORT).show();
                Sneaker.with(Login.this)
                        .setTitle("Cancelled")
                        .setMessage("")
                        .setCornerRadius(4)
                        .setDuration(1500)
                        .sneakError();

            }


            @Override
            public void onError(FacebookException error) {
                Log.d("coder", "" + error);
                Sneaker.with(Login.this)
                        .setTitle(error.getMessage())
                        .setMessage("")
                        .setCornerRadius(4)
                        .setDuration(1500)
                        .sneakError();

                //   Toast.makeText(Login.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });

        // Detect user is login or not. If logout then clear the TextView and delete all the user info from TextView.
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken2 == null) {

                    // Clear the TextView after logout.
                    Sneaker.with(Login.this)
                            .setTitle("all Data are Removed")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakSuccess();


                }
            }
        };
    }

    public void GoogleLogin(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            String personName = "", personGivenName = "", personFamilyName = "", personEmail = "", personId = "", personMobile = "";
            Uri personPhoto = null;
            if (acct != null) {
                personName = acct.getDisplayName();
                personGivenName = acct.getGivenName();
                personFamilyName = acct.getFamilyName();
                personEmail = acct.getEmail();
                personId = acct.getId();
                personPhoto = acct.getPhotoUrl();

                MyPreferences.getInstance(context).putString(PrefConf.USER_Name, personName);
                MyPreferences.getInstance(context).putString(PrefConf.User_Email, personEmail);
                presenter.SocialLogin(context, personEmail, personName);

            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            Log.d("coder", "" + e.getLocalizedMessage());

            // updateUI(null);
        }
    }

    public void FbLogin(View view) {
        binding.loginButton.performClick();

    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {


                        // Application code
                        if (response.getError() != null) {
                            System.out.println("ERROR");
                        } else {
                            System.out.println("Success");
                            String jsonresult = String.valueOf(object);
                            System.out.println("JSON Result" + jsonresult);
                            String fbUserId = object.optString("id");
                            String fbUserFirstName = object.optString("name");
                            String fbUserEmail = object.optString("email");
                            Log.d("SignUpActivity", "Email: " + fbUserEmail + "\nName: " + fbUserFirstName + "\nID: " + fbUserId);


                            if (fbUserEmail.isEmpty()) {
                                Sneaker.with(Login.this)
                                        .setTitle("Email not Found")
                                        .setMessage("")
                                        .setCornerRadius(4)
                                        .setDuration(1500)
                                        .sneakError();
                            } else {
                                MyPreferences.getInstance(context).putString(PrefConf.USER_Name, fbUserFirstName);
                                MyPreferences.getInstance(context).putString(PrefConf.User_Email, fbUserEmail);
                                presenter.SocialLogin(context, fbUserEmail, fbUserFirstName);
                            }
                            //   LoginManager.getInstance().logIn(Login.this,Arrays.asList("user_friends", "email", "public_profile"));

                        }
                        Log.d("SignUpActivity", response.toString());
                    }

                });

        Bundle parameters = new Bundle();

        parameters.putString("fields", "id,name,email,last_name,first_name");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(Login.this);


    }
}