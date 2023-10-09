package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.ChatResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatPresenter {

    private ChatView view;

    public ChatPresenter(ChatView view) {
        this.view = view;
    }

    public void getHomeChat(Context context) {
        Call<ChatResponse> call = AppUtils.KlickedApi(context).getHomeChat();
        view.showHideProgress(true);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetChatSuccess(response.body(), response.message());
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
    
    public interface ChatView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetChatSuccess(ChatResponse response, String message);


        void onFailure(Throwable t);
    }
}
