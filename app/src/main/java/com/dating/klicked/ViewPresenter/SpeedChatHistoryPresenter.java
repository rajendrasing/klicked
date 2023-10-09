package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.RequestRepo.SpeedChatDeleteBody;
import com.dating.klicked.Model.ResponseRepo.ChatResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpeedChatHistoryPresenter {
    private SpeedChatHistoryView view;

    public SpeedChatHistoryPresenter(SpeedChatHistoryView view) {
        this.view = view;
    }

    public void getOldSpeedChat(Context context) {
        Call<ChatResponse> call = AppUtils.KlickedApi(context).getOldSpeedChat();
        view.showHideProgress(true);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetSpeedChatHistorySuccess(response.body(), response.message());
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
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }

    public void DeleteOldSpeedChat(Context context , SpeedChatDeleteBody speedChatDeleteBody) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).DeleteOldSpeedChat(speedChatDeleteBody);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onDeleteSpeedChatHistorySuccess(response.body(), response.message());
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
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }



    public interface SpeedChatHistoryView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetSpeedChatHistorySuccess(ChatResponse response, String message);

        void onDeleteSpeedChatHistorySuccess(ResponseBody response, String message);

        void onFailure(Throwable t);
    }
}
