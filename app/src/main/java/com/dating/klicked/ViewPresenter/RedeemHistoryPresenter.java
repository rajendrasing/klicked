package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.Model.ResponseRepo.RedeemHistoryResponse;
import com.dating.klicked.Model.ResponseRepo.RedeemHistoryResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedeemHistoryPresenter {

    private  RedeemHistoryView view;

    public RedeemHistoryPresenter(RedeemHistoryView view) {
        this.view = view;
    }

    public void getAllRedeemHistory(Context context) {
        Call<RedeemHistoryResponse> call = AppUtils.KlickedApi(context).RedeemHistory();
        view.showHideProgress(true);
        call.enqueue(new Callback<RedeemHistoryResponse>() {
            @Override
            public void onResponse(Call<RedeemHistoryResponse> call, Response<RedeemHistoryResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetRedeemHistorySuccess(response.body(), response.message());
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
            public void onFailure(Call<RedeemHistoryResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public interface RedeemHistoryView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetRedeemHistorySuccess(RedeemHistoryResponse response, String message);

        void onFailure(Throwable t);
    }
}
