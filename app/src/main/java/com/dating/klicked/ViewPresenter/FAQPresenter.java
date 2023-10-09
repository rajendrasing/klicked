package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQPresenter {

    private FAQView view;

    public FAQPresenter(FAQView view) {
        this.view = view;
    }

    public void AddFAQ(Context context, String Question) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).AddFAQ(Question);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onAddFAQSuccess(response.body(), response.message());
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


    public void GetFAQ(Context context) {
        Call<List<FAQResponse>> call = AppUtils.KlickedApi(context).GetFAQ();
        view.showHideProgress(true);
        call.enqueue(new Callback<List<FAQResponse>>() {
            @Override
            public void onResponse(Call<List<FAQResponse>> call, Response<List<FAQResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGETFeedbackSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<FAQResponse>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public  interface FAQView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onAddFAQSuccess(ResponseBody responseBody, String message);

        void onGETFeedbackSuccess(List<FAQResponse> list, String message);


        void onFailure(Throwable t);

    }
}
