package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.Model.ResponseRepo.RewardHistoryResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RewardsPresenter {

    private RewardsView view;

    public RewardsPresenter(RewardsView view) {
        this.view = view;
    }


    public void getAllRewardsHistory(Context context) {
        Call<RewardHistoryResponse> call = AppUtils.KlickedApi(context).rewardHistory();
        view.showHideProgress(true);
        call.enqueue(new Callback<RewardHistoryResponse>() {
            @Override
            public void onResponse(Call<RewardHistoryResponse> call, Response<RewardHistoryResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetRewardHistorySuccess(response.body(), response.message());
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
            public void onFailure(Call<RewardHistoryResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public void getAllOtherFAQData(Context context, String type) {
        Call<List<FAQResponse>> call = AppUtils.KlickedApi(context).GetOtherFAQ(type);
        view.showHideProgress(true);
        call.enqueue(new Callback<List<FAQResponse>>() {
            @Override
            public void onResponse(Call<List<FAQResponse>> call, Response<List<FAQResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetAllOtherFAQSuccess(response.body(), response.message());
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


    public interface RewardsView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetRewardHistorySuccess(RewardHistoryResponse response, String message);


        void onGetAllOtherFAQSuccess(List<FAQResponse> response, String message);

        void onFailure(Throwable t);
    }

}
