package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.RequestRepo.SendMessageBody;
import com.dating.klicked.Model.ResponseRepo.UserResponse;
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

public class SeedDatingUserPresenter {

    private SeedDatingUserView view;

    public SeedDatingUserPresenter(SeedDatingUserView view) {
        this.view = view;
    }

    public void GetSeedDatingUser(Context context) {
        view.showHideProgress(true);

        Call<UserResponse> call = AppUtils.KlickedApi(context).GetSeedDatingUser();

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSeedDatingUserSuccess(response.body(), response.message());
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
            public void onFailure(Call<UserResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);

            }
        });
    }

    public void CreateSpeedChatDocumentUser(Context context, String ReceiverID, String ReceiverName) {
        // view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).crateSpeedDocument(ReceiverID, ReceiverName);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onCreateChatUserSuccess(response.body(), response.message());
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
                // view.showHideProgress(false);
                view.onFailure(t);
            }
        });
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


    public void UpdateSpeedtime(Context context,String time,String phone) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).updateSpeedDatingTime(time,phone);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onUpdateSpeedTimeSuccess(response.body(), response.message());
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



    public interface SeedDatingUserView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onSeedDatingUserSuccess(UserResponse response, String message);

        void onCreateChatUserSuccess(ResponseBody response, String message);

        void onOnlineUserSuccess(ResponseBody responseBody, String message);

        void onUpdateSpeedTimeSuccess(ResponseBody responseBody, String message);

        void onFailure(Throwable t);
    }

}
