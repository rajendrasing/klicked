package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.RequestRepo.SendMessageBody;
import com.dating.klicked.Model.ResponseRepo.SendMessageResponse;
import com.dating.klicked.Model.ResponseRepo.UserChatResponse;
import com.dating.klicked.Model.ResponseRepo.UserResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpeedDatingChatPresenter {

    private SeedDatingUserView view;

    public SpeedDatingChatPresenter(SeedDatingUserView view) {
        this.view = view;
    }

    public void OnlineUser(Context context) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).OnlineUser();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onOnlineUserSuccess(response.body(), response.message());
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorRes);
                        String err_msg = jsonObject.getString("error");
                        view.onError(err_msg);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                view.onFailure(t);
            }
        });

    }

    public void OfflineUser(Context context) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).OfflineUser();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onOfflineUserSuccess(response.body(), response.message());
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorRes);
                        String err_msg = jsonObject.getString("error");
                        view.onError(err_msg);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                view.onFailure(t);
            }
        });

    }


    public void SendMessage(Context context, SendMessageBody body) {
        //  view.showHideProgress(true);

        Call<SendMessageResponse> call = AppUtils.KlickedApi(context).SpeedSendMessage(body);

        call.enqueue(new Callback<SendMessageResponse>() {
            @Override
            public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
                //      view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSendSpeedMessageSuccess(response.body(), response.message());
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
        Call<UserChatResponse> call = AppUtils.KlickedApi(context).SpeedUserChat(chatId);

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


    public void SendRequest(Context context, String senderId, String receiverId) {
       // view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).SendRequest(senderId, receiverId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
           //     view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSendRequestSuccess(response.body(), response.message());
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
              //  view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }


    public interface SeedDatingUserView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onOnlineUserSuccess(ResponseBody responseBody, String message);

        void onOfflineUserSuccess(ResponseBody responseBody, String message);

        void onSendSpeedMessageSuccess(SendMessageResponse response, String message);

        void onGetAllChatSucces(UserChatResponse response, String message);


        void onSendRequestSuccess(ResponseBody response, String message);

        void onFailure(Throwable t);
    }

}
