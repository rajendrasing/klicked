package com.dating.klicked.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dating.klicked.R;
import com.dating.klicked.databinding.ActivityCallBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.JavascriptInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class CallActivity extends AppCompatActivity {
    ActivityCallBinding binding;

    private boolean isPeerConnected = false, isAudio = true, isVideo = true;
    String username = "", friendsUsername = "", uniqueId,name;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    public boolean permissionStatus;
    Dialog dialogBox;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_call);


        Bundle bundle = getIntent().getExtras();

        username = bundle.getString("username");
        name = bundle.getString("name");

        binding.txtName.setText(name);

        binding.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendsUsername = bundle.getString("friendsUsername");

                sendCallRequest();
            }
        });

        binding.toggleAudioBtn.setOnClickListener(view -> {
            isAudio = !isAudio;
            callJavascriptFunction("javascript:toggleAudio(\"" + isAudio + "\")");
            binding.toggleAudioBtn.setImageResource(isAudio ? R.drawable.ic_baseline_mic_24 : R.drawable.ic_baseline_mic_off_24);

        });

        binding.toggleVideoBtn.setOnClickListener(view -> {
            isVideo = !isVideo;
            callJavascriptFunction("javascript:toggleVideo(\"" + isVideo + "\")");
            binding.toggleVideoBtn.setImageResource(isVideo ? R.drawable.ic_baseline_videocam_24 : R.drawable.ic_baseline_videocam_off_24);

        });

        binding.imgEnd.setOnClickListener(view -> {
            onBackPressed();
        });

        setupWebView();

    }

    public void sendCallRequest() {
        if (!isPeerConnected) {
            Toast.makeText(this, "You're not connected. Check your internet", Toast.LENGTH_LONG).show();
            return;
        }

        reference.child(friendsUsername).child("incoming").setValue(username);
        reference.child(friendsUsername).child("isAvailable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null && snapshot.getValue().toString() == "true") {
                    listenForConnId();
                } else {
                    listenForConnId();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void listenForConnId() {
        reference.child(friendsUsername).child("connId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null)
                    return;
                switchToControls();
                callJavascriptFunction("javascript:startCall(\"" + snapshot.getValue() + "\")");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupWebView() {


        binding.webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d("TAG", "onPermissionRequest");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    request.grant(request.getResources());
                }
            }

        });


        WebSettings webSettings = binding.webView.getSettings();


        // Enable Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        binding.webView.addJavascriptInterface(new JavascriptInterface(this), "Android");

        loadVideoCall();
    }

    private void loadVideoCall() {
        String filePath = "file:android_asset/call.html";
        binding.webView.loadUrl(filePath);

        binding.webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                initializePeer();
            }
        });

    }

    private void initializePeer() {

        uniqueId = getUniqueID();

        callJavascriptFunction("javascript:init(\"" + uniqueId + "\")");
        reference.child(username).child("incoming").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    onCallRequest(snapshot.getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void onCallRequest(String caller) {
        if (caller == null)
            return;

        binding.callLayout.setVisibility(View.VISIBLE);
        binding.incomingCallTxt.setText(caller + " is calling...");

        binding.acceptBtn.setOnClickListener(view -> {
            reference.child(username).child("connId").setValue(uniqueId);
            reference.child(username).child("isAvailable").setValue(true);

            binding.callLayout.setVisibility(View.GONE);

            switchToControls();
        });

        binding.rejectBtn.setOnClickListener(view -> {
            reference.child(username).child("incoming").setValue(null);
            binding.callLayout.setVisibility(View.GONE);
            onBackPressed();
        });

    }

    public static String getUniqueID() {

        return UUID.randomUUID().toString();
    }

    private void switchToControls() {
        binding.inputLayout.setVisibility(View.GONE);
        binding.callControlLayout.setVisibility(View.VISIBLE);
    }

    public final void onPeerConnected() {
        isPeerConnected = true;
    }

    private void callJavascriptFunction(String functionString) {

        binding.webView.post(() -> binding.webView.evaluateJavascript(functionString, null));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        reference.child(username).setValue(null);
        binding.webView.loadUrl("about:blank");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        reference.child(username).setValue(null);
        binding.webView.loadUrl("about:blank");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case AppUtils.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    permissionStatus = true;

                } else {
                    permissionStatus = false;
                    String msg = "Please Allow Permission to share.";
                    customAlert(msg);

                }
                return;
        }
    }

    private void customAlert(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialogBox.dismiss();
            }
        }).show();
    }
}


