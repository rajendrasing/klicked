package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFeedbackPresenter {
    
    private AddFeedbackView view;

    public AddFeedbackPresenter(AddFeedbackView view) {
        this.view = view;
    }

    public void AddFeedbacks(Context context, String title,String descriptions) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).AddFeedbacks(title,descriptions);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onAddFeedbackSuccess(response.body(), response.message());
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



    public interface AddFeedbackView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onAddFeedbackSuccess(ResponseBody responseBody, String message);


        void onFailure(Throwable t);
    }
}
