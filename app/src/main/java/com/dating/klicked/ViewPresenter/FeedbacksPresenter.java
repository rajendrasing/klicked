package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.FeedbackResponse;
import com.dating.klicked.Model.ResponseRepo.FeedbackResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbacksPresenter {
    
    private FeedbacksView view;

    public FeedbacksPresenter(FeedbacksView view) {
        this.view = view;
    }

    public void getAllFeedbackData(Context context) {
        Call<FeedbackResponse> call = AppUtils.KlickedApi(context).GetAllFeedbackData();
        view.showHideProgress(true);
        call.enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetAllFeedbacksSuccess(response.body(), response.message());
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
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }

    public interface FeedbacksView{
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetAllFeedbacksSuccess(FeedbackResponse response, String message);


        void onFailure(Throwable t);
    }
}
