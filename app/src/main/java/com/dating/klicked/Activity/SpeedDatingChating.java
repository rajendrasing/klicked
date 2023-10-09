package com.dating.klicked.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dating.klicked.Fragment.Adapter.Chat_messageAdapter;
import com.dating.klicked.MainActivity;
import com.dating.klicked.Model.RequestRepo.SendMessageBody;
import com.dating.klicked.Model.ResponseRepo.SendMessageResponse;
import com.dating.klicked.Model.ResponseRepo.UserChatResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.SpeedDatingChatPresenter;
import com.dating.klicked.databinding.ActivitySpeedDatingChatingBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.ResponseBody;

public class SpeedDatingChating extends AppCompatActivity implements SpeedDatingChatPresenter.SeedDatingUserView, View.OnClickListener {
    ActivitySpeedDatingChatingBinding binding;
    private View view;
    private Context context;
    private Dialog mDialog, SpeedDialog;
    String name, userId, ChatId;

    SpeedDatingChatPresenter presenter;
    User_Data userData;
    List<UserChatResponse.Array> arrayList = new ArrayList<UserChatResponse.Array>();
    Boolean checkOnline = false;

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("https://api.klicked.co");


        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_speed_dating_chating);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_speed_dating_chating);

        view = binding.getRoot();
        context = SpeedDatingChating.this;

        userData = SharedPrefManager.getInstance(context).getLoginDATA();


        presenter = new SpeedDatingChatPresenter(this);
        presenter.OnlineUser(context);
        name = getIntent().getStringExtra("name");
        userId = getIntent().getStringExtra("userId");
        ChatId = getIntent().getStringExtra("chatId");
        if (name != null) {
            binding.txtName.setText(name);
        }


        mDialog = AppUtils.hideShowProgress(context);

        binding.imgSendBtn.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);

        presenter.GetAllChatUser(context, ChatId);

        binding.edSend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String message = charSequence.toString();
                if (message.isEmpty()) {
                    binding.imgSendBtn.setAlpha(0.5f);
                } else {
                    binding.imgSendBtn.setAlpha(0.9f);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSocket.connect();
        // mSocket.on("hello", onNewMessage);
        mSocket.on("messagesToAdmin", getmessage);
        mSocket.on("onlineStatus", getUserOnline);
        //mSocket.on("readData", getAllReadData);
    }


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    Log.d("hello111", data.toString());


                    Toast.makeText(context, "" + data.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    private Emitter.Listener getmessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];


                    arrayList.clear();


                    for (int i = 0; i <= data.length(); i++) {
                        try {

                            JSONObject object = data.getJSONObject(i);
                            String array = object.getString("array");
                            JSONArray jsonArray = new JSONArray(array);
                            for (int j = 0; j <= jsonArray.length(); j++) {

                                JSONObject object1 = jsonArray.getJSONObject(j);
                                UserChatResponse.Array array1 = new UserChatResponse.Array();
                                array1.setRole(object1.getString("role"));
                                array1.setUserId(object1.getString("userId"));
                                array1.setOutgoingMessage(object1.getString("outgoingMessage"));
                                array1.setRead(object1.getBoolean("read"));
                                array1.setId(object1.getString("_id"));
                                array1.setUpdatedAt(object1.getString("updatedAt"));
                                array1.setCreatedAt(object1.getString("createdAt"));
                                arrayList.add(array1);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    Chat_messageAdapter chatMessageAdapter = new Chat_messageAdapter(context, arrayList, userData.getId());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    linearLayoutManager.setStackFromEnd(true);
                    chatMessageAdapter.notifyDataSetChanged();
                    chatMessageAdapter.update(arrayList);
                    binding.chatRecycler.setLayoutManager(linearLayoutManager);
                    binding.chatRecycler.setHasFixedSize(true);
                    binding.chatRecycler.setAdapter(chatMessageAdapter);


                    // Log.d("hello11111111111", data.toString());
                    //  Toast.makeText(context, "" + data.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    private Emitter.Listener getUserOnline = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];


                    Log.d("hello11111111111", data.toString());
                }
            });
        }
    };

    private Emitter.Listener getAllReadData = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = (JSONObject) args[0];
                    Log.d("readData", data.toString());
                    arrayList.clear();

                    try {
                        String outgoingMessages = data.getString("outgoingMessages");
                        JSONArray jsonArray = new JSONArray(outgoingMessages);

                        for (int i = 0; i <= jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            UserChatResponse.Array array1 = new UserChatResponse.Array();
                            array1.setRole(jsonObject.getString("role"));
                            array1.setUserId(jsonObject.getString("userId"));
                            array1.setOutgoingMessage(jsonObject.getString("outgoingMessage"));
                            array1.setRead(jsonObject.getBoolean("read"));
                            array1.setId(jsonObject.getString("_id"));
                            array1.setUpdatedAt(jsonObject.getString("updatedAt"));
                            array1.setCreatedAt(jsonObject.getString("createdAt"));
                            arrayList.add(array1);

                        }

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }


                    Chat_messageAdapter chatMessageAdapter = new Chat_messageAdapter(context, arrayList, userData.getId());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    linearLayoutManager.setStackFromEnd(true);
                    chatMessageAdapter.notifyDataSetChanged();
                    chatMessageAdapter.update(arrayList);
                    binding.chatRecycler.setLayoutManager(linearLayoutManager);
                    binding.chatRecycler.setHasFixedSize(true);
                    binding.chatRecycler.setAdapter(chatMessageAdapter);

                }
            });


        }
    };


    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            mDialog.show();
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            }, 1000);

        }


    }

    @Override
    public void onError(String message) {
        if (message.equalsIgnoreCase("Already You Are Matched")) {
            SpeedDialog.dismiss();
            super.onBackPressed();
        } else {
            Sneaker.with(SpeedDatingChating.this)
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        }
    }

    @Override
    public void onOnlineUserSuccess(ResponseBody responseBody, String message) {

        if (message.equalsIgnoreCase("ok")) {
            Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onOfflineUserSuccess(ResponseBody responseBody, String message) {

        if (message.equalsIgnoreCase("ok")) {
            Toast.makeText(this, "offline", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSendSpeedMessageSuccess(SendMessageResponse response, String message) {

        if (message.equalsIgnoreCase("ok")) {
            if (message.equalsIgnoreCase("ok")) {
                binding.edSend.setText("");


                if (!response.getResult().get(response.getResult().size() - 1).getUserId().equalsIgnoreCase(userData.getId())) {

                    if (checkOnline == true) {
                        JSONObject message1 = new JSONObject();
                        try {
                            message1.put("chatId", ChatId);
                            message1.put("messageId", response.getResult().get(response.getResult().size() - 1).getId());

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                        Log.d("messageIdddd", message1.toString());
                        MainActivity.mSocket.emit("readChat", message1);
                    }
                }

            }

        }
    }

    @Override
    public void onGetAllChatSucces(UserChatResponse response, String message) {
        if (message.equalsIgnoreCase("ok") && response != null) {

            if (response.getArray().size() > 0 && response.getArray() != null) {
                arrayList.clear();

                for (int i = 0; i < response.getArray().size(); i++) {
                    UserChatResponse.Array array = new UserChatResponse.Array();
                    array.setId(response.getArray().get(i).getId());
                    array.setRead(response.getArray().get(i).getRead());
                    array.setUserId(response.getArray().get(i).getUserId());
                    array.setRole(response.getArray().get(i).getRole());
                    array.setOutgoingMessage(response.getArray().get(i).getOutgoingMessage());
                    array.setCreatedAt(response.getArray().get(i).getCreatedAt());
                    array.setUpdatedAt(response.getArray().get(i).getUpdatedAt());
                    arrayList.add(array);

                    if (!response.getArray().get(i).getUserId().equalsIgnoreCase(userData.getId())) {

                        JSONObject message1 = new JSONObject();
                        try {
                            message1.put("chatId", ChatId);
                            message1.put("messageId", response.getArray().get(i).getId());

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                        Log.d("messageIdddd", message1.toString());
                        MainActivity.mSocket.emit("readChat", message1);


                    }

                }


            }


            Chat_messageAdapter chatMessageAdapter = new Chat_messageAdapter(context, arrayList, userData.getId());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setStackFromEnd(true);
            chatMessageAdapter.notifyDataSetChanged();
            chatMessageAdapter.update(arrayList);
            binding.chatRecycler.setLayoutManager(linearLayoutManager);
            binding.chatRecycler.setHasFixedSize(true);
            binding.chatRecycler.setAdapter(chatMessageAdapter);
        } else {

        }

    }

    @Override
    public void onSendRequestSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(this)
                    .setTitle("Successfully Send Request in User")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            SpeedDialog.dismiss();
            super.onBackPressed();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(SpeedDatingChating.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_send_btn:
                String message = binding.edSend.getText().toString();
                if (!message.isEmpty()) {
                    List<SendMessageBody.OutgoingMessage> outgoingMessages = new ArrayList<SendMessageBody.OutgoingMessage>();
                    SendMessageBody.OutgoingMessage message1 = new SendMessageBody.OutgoingMessage(message, userData.getId(), checkOnline);
                    outgoingMessages.add(message1);
                    SendMessageBody body = new SendMessageBody(ChatId, outgoingMessages);
                    presenter.SendMessage(context, body);

                } else {

                    Toast.makeText(context, "Please Enter message", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.img_back:

                onBackPressed();
                break;
        }
    }

    public void SpeedDialog(Activity activity) {
        SpeedDialog = new Dialog(activity);


        SpeedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SpeedDialog.setCancelable(true);
        SpeedDialog.setContentView(R.layout.custom_speed_chat_pop);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();


        lp.copyFrom(SpeedDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        SpeedDialog.getWindow().setAttributes(lp);
        SpeedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView okButton = SpeedDialog.findViewById(R.id.txt_send);
        TextView cancelButton = SpeedDialog.findViewById(R.id.txt_cancel);

        cancelButton.setOnClickListener(view1 -> {
            SpeedDialog.dismiss();
            // onSaveInstanceState(new Bundle());
            presenter.OfflineUser(context);
            super.onBackPressed();
        });

        okButton.setOnClickListener(view1 -> {
            presenter.OfflineUser(context);
            presenter.SendRequest(context, userData.getId(), userId);

        });

        SpeedDialog.show();
    }

    @Override
    public void onBackPressed() {
        //
        SpeedDialog(this);

    }
}