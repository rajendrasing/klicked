package com.dating.klicked.Fragment.BottomMenu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Activity.CallActivity;
import com.dating.klicked.Fragment.Adapter.Chat_messageAdapter;
import com.dating.klicked.MainActivity;
import com.dating.klicked.Model.RequestRepo.SendMessageBody;
import com.dating.klicked.Model.ResponseRepo.SendMessageResponse;
import com.dating.klicked.Model.ResponseRepo.UserChatResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.UserChatPresenter;
import com.dating.klicked.databinding.FragmentUserChatBinding;
import com.dating.klicked.utils.AppUtils;
import com.google.firebase.FirebaseApp;
import com.irozon.sneaker.Sneaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;
import okhttp3.ResponseBody;


public class UserChat_Fragment extends Fragment implements UserChatPresenter.UserChatView, View.OnClickListener {
    FragmentUserChatBinding binding;
    private Context context;
    private Dialog dialog, blockDialog;
    private View view;
    String chatId, userId;
    UserChatPresenter presenter;
    User_Data userData;
    List<UserChatResponse.Array> arrayList = new ArrayList<UserChatResponse.Array>();
    Boolean checkOnline = false, callingUser = true;

    /* private Socket mSocket;

     {
         try {
             mSocket = IO.socket("http://13.213.30.50:6969");


         } catch (URISyntaxException e) {
         }
     }
 */
    Activity mActivity;

    public UserChat_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_chat, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        presenter = new UserChatPresenter(this);
        userData = SharedPrefManager.getInstance(context).getLoginDATA();
        FirebaseApp.initializeApp(getContext());
        if (getArguments().getString("userId") != null) {
            userId = getArguments().getString("userId");
        }
        if (getArguments().getString("Name") != null) {

            binding.txtName.setText(getArguments().getString("Name"));
        } else {
            binding.txtName.setText("");
        }
        if (getArguments().getString("profile") != null) {
            if (getArguments().getString("profile").equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

                binding.imgProfile.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.profile1));
            } else {
                Glide.with(getContext()).load(PrefConf.IMAGE_URL + getArguments().getString("profile"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().circleCrop())
                        .into(binding.imgProfile);
            }
        }
        if (getArguments().getString("ChatId") != null) {
            chatId = getArguments().getString("ChatId");
        }


        presenter.GetAllChatUser(getContext(), chatId);
        binding.imgSendBtn.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        binding.imgCall.setOnClickListener(this);
        binding.imgThreeDots.setOnClickListener(this);

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

        //  mSocket.connect();
        // mSocket.on("hello", onNewMessage);
        MainActivity.mSocket.on("messagesToAdmin", getmessage);
        MainActivity.mSocket.on("onlineStatus", getUserOnline);
        MainActivity.mSocket.on("readData", getAllReadData);
        MainActivity.mSocket.on("incommingCall", incommingCall);


        return binding.getRoot();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    Log.d("hello111", data.toString());


                    Toast.makeText(getContext(), "" + data.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    private Emitter.Listener getmessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            mActivity.runOnUiThread(new Runnable() {
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

                    Chat_messageAdapter chatMessageAdapter = new Chat_messageAdapter(getContext(), arrayList, userData.getId());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setStackFromEnd(true);
                    chatMessageAdapter.notifyDataSetChanged();
                    chatMessageAdapter.update(arrayList);
                    binding.chatRecycler.setLayoutManager(linearLayoutManager);
                    binding.chatRecycler.setHasFixedSize(true);
                    binding.chatRecycler.setAdapter(chatMessageAdapter);


                    // Log.d("hello11111111111", data.toString());
                    //  Toast.makeText(getContext(), "" + data.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    private Emitter.Listener getUserOnline = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    try {
                        callingUser = data.getBoolean("status");
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                    Log.d("hello11111111111", String.valueOf(callingUser));
                }
            });
        }
    };

    private Emitter.Listener getAllReadData = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = (JSONObject) args[0];
                    if(data !=null){
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


                        Chat_messageAdapter chatMessageAdapter = new Chat_messageAdapter(getContext(), arrayList, userData.getId());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setStackFromEnd(true);
                        chatMessageAdapter.notifyDataSetChanged();
                        chatMessageAdapter.update(arrayList);
                        binding.chatRecycler.setLayoutManager(linearLayoutManager);
                        binding.chatRecycler.setHasFixedSize(true);
                        binding.chatRecycler.setAdapter(chatMessageAdapter);

                    }


                //    Log.d("readData", data.toString());
                }
            });


        }
    };

    private Emitter.Listener incommingCall = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    JSONObject data = (JSONObject) args[0];


                    //  try {
                    //   Log.d("ststst", String.valueOf(data.getBoolean("status")));

                    //   if (data.getBoolean("status")){
//                    Intent intent = new Intent(getActivity(), CallActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("friendsUsername", userId);
//                    bundle.putString("username", userData.getId());
//                    bundle.putString("name", getArguments().getString("Name"));
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                        }else {
//                            Toast.makeText(getContext(), "Call cannot be connect Because user is Not Available", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException jsonException) {
//                        jsonException.printStackTrace();
//                    }

                    Log.d("hello11111111111", String.valueOf(data));
                }
            });

        }
    };

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    dialog.dismiss();
                }


            }, 1000);

        }
    }

    @Override
    public void onError(String message) {
        Sneaker.with(getActivity())
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();

    }

    @Override
    public void onSendMessageSuccess(SendMessageResponse response, String message) {

        if (message.equalsIgnoreCase("ok")) {
            binding.edSend.setText("");


            if (!response.getResult().get(response.getResult().size() - 1).getUserId().equalsIgnoreCase(userData.getId())) {

                if (checkOnline == true) {
                    JSONObject message1 = new JSONObject();
                    try {
                        message1.put("chatId", chatId);
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
                            message1.put("chatId", chatId);
                            message1.put("messageId", response.getArray().get(i).getId());

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                        Log.d("messageIdddd", message1.toString());
                        MainActivity.mSocket.emit("readChat", message1);


                    }

                }


            }


            Chat_messageAdapter chatMessageAdapter = new Chat_messageAdapter(getContext(), arrayList, userData.getId());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
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
    public void onBlockUserSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("This user has been successfully blocked")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();

            //  blockDialog.dismiss();

        }

    }


    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(getActivity())
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
                if (!message.matches("[a-zA-Z ]+")) {
                    Sneaker.with(getActivity())
                            .setTitle("ENTER ONLY ALPHABETICAL CHARACTER")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakError();

                } else if (!message.isEmpty()) {
                    List<SendMessageBody.OutgoingMessage> outgoingMessages = new ArrayList<SendMessageBody.OutgoingMessage>();
                    SendMessageBody.OutgoingMessage message1 = new SendMessageBody.OutgoingMessage(message, userData.getId(), checkOnline);
                    outgoingMessages.add(message1);
                    SendMessageBody body = new SendMessageBody(chatId, outgoingMessages);
                    presenter.SendMessage(getContext(), body);

                } else {

                    Toast.makeText(getContext(), "Please Enter message", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.img_back:

                getActivity().onBackPressed();
                break;
            case R.id.img_call:
                Sneaker.with(getActivity())
                        .setTitle("Coming Soon")
                        .setMessage("")
                        .setCornerRadius(4)
                        .setDuration(1500)
                        .sneakSuccess();
               /* dialog.show();
                JSONObject message1 = new JSONObject();
                try {
                    message1.put("callerId", userData.getId());
                    message1.put("callerName", userData.getUserName());
                    message1.put("reciverId", userId);

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

                MainActivity.mSocket.emit("call", message1);

                Intent intent = new Intent(getActivity(), CallActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("friendsUsername", userId);
                bundle.putString("username", userData.getId());
                bundle.putString("name", getArguments().getString("Name"));
                intent.putExtras(bundle);
                startActivity(intent);
                break;*/
            case R.id.img_three_dots:
                blockDialog(getActivity());
                break;
        }
    }

    public void blockDialog(Activity activity) {
        blockDialog = new Dialog(activity);


        blockDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        blockDialog.setCancelable(true);
        blockDialog.setContentView(R.layout.custom_speed_chat_pop);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();


        lp.copyFrom(blockDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        blockDialog.getWindow().setAttributes(lp);
        blockDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txt_title = blockDialog.findViewById(R.id.txt_title);
        TextView txt_des = blockDialog.findViewById(R.id.txt_des);
        TextView okButton = blockDialog.findViewById(R.id.txt_send);
        TextView cancelButton = blockDialog.findViewById(R.id.txt_cancel);

        txt_title.setText("Are you Sure You want to Block This User  ?");
        txt_des.setText(" If you block this user, you won't receive any messages from the user.");
        okButton.setText("Block");

        cancelButton.setOnClickListener(view1 -> {
            blockDialog.dismiss();
        });

        okButton.setOnClickListener(view1 -> {
            blockDialog.dismiss();
            presenter.BlockUser(getContext(), userId);
        });

        blockDialog.show();
    }

}