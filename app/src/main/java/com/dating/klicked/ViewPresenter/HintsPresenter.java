package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.Model.ResponseRepo.SubHintsResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HintsPresenter {

    private HintsView view;

    public HintsPresenter(HintsView view) {
        this.view = view;

    }

    public void GetHints(Context context) {
        Call<List<FAQResponse>> call = AppUtils.KlickedApi(context).GetHints();
        view.showHideProgress(true);
        call.enqueue(new Callback<List<FAQResponse>>() {
            @Override
            public void onResponse(Call<List<FAQResponse>> call, Response<List<FAQResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGETHintsSuccess(response.body(), response.message());
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

    public void GetSubHints(Context context , String quesId) {
        Call<List<SubHintsResponse>> call = AppUtils.KlickedApi(context).GetSubHintsResponse(quesId);
        view.showHideProgress(true);
        call.enqueue(new Callback<List<SubHintsResponse>>() {
            @Override
            public void onResponse(Call<List<SubHintsResponse>> call, Response<List<SubHintsResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSubHintsSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<SubHintsResponse>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public  interface HintsView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGETHintsSuccess(List<FAQResponse> list, String message);

        void onSubHintsSuccess(List<SubHintsResponse> list, String message);


        void onFailure(Throwable t);

    }
}
