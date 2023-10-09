package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.RequestRepo.SendMessageBody;
import com.dating.klicked.Model.ResponseRepo.SendMessageResponse;
import com.dating.klicked.Model.ResponseRepo.UserChatResponse;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChatPresenter {
    private UserChatView view;

    public UserChatPresenter(UserChatView view) {
        this.view = view;
    }

    public void SendMessage(Context context, SendMessageBody body) {
      //  view.showHideProgress(true);

        Call<SendMessageResponse> call = AppUtils.KlickedApi(context).SendMessage(body);

        call.enqueue(new Callback<SendMessageResponse>() {
            @Override
            public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
          //      view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSendMessageSuccess(response.body(), response.message());
                } else if (response.code() == 400) {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject object = new JSONObject(errorRes);
                        String err_msg = object.getString("error");
                        view.onError(err_msg);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SendMessageResponse> call, Throwable t) {
             //   view.showHideProgress(false);
                view.onFailure(t);

            }
        });
    }

    public void GetAllChatUser(Context context, String chatId) {
        view.showHideProgress(true);
        Call<UserChatResponse> call = AppUtils.KlickedApi(context).UserChat(chatId);

        call.enqueue(new Callback<UserChatResponse>() {
            @Override
            public void onResponse(Call<UserChatResponse> call, Response<UserChatResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetAllChatSucces(response.body(), response.message());
                } else if (response.code() == 400) {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject object = new JSONObject(errorRes);
                        String err_msg = object.getString("error");
                        view.onError(err_msg);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    view.onError(response.message());
                }

            }

            @Override
            public void onFailure(Call<UserChatResponse> call, Throwable t) {

                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void BlockUser(Context context, String receiverId) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).BlockUser(receiverId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onBlockUserSuccess(response.body(), response.message());
                } else if (response.code() == 400) {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject object = new JSONObject(errorRes);
                        String err_msg = object.getString("error");
                        view.onError(err_msg);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }


    public interface UserChatView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onSendMessageSuccess(SendMessageResponse response, String message);

        void onGetAllChatSucces(UserChatResponse response, String message);

        void onBlockUserSuccess(ResponseBody responseBody, String message);


        void onFailure(Throwable t);
    }
}
