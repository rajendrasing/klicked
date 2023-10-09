package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.GiftResponse;
import com.dating.klicked.Model.ResponseRepo.GiftResponse;
import com.dating.klicked.Model.ResponseRepo.RewardHistoryResponse;
import com.dating.klicked.utils.AppUtils;
import com.google.android.material.badge.BadgeDrawable;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RewardsRedeemPresenter {

    private RewardsView view;

    public RewardsRedeemPresenter(RewardsView view) {
        this.view = view;
    }

    public void getAllGiftData(Context context) {
        Call<List<GiftResponse>> call = AppUtils.KlickedApi(context).GetALLGift();
        view.showHideProgress(true);
        call.enqueue(new Callback<List<GiftResponse>>() {
            @Override
            public void onResponse(Call<List<GiftResponse>> call, Response<List<GiftResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetRewardGiftSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<GiftResponse>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }

    public void getGiftDataById(Context context, String id) {
        Call<GiftResponse> call = AppUtils.KlickedApi(context).GetGiftByID(id);
        view.showHideProgress(true);
        call.enqueue(new Callback<GiftResponse>() {
            @Override
            public void onResponse(Call<GiftResponse> call, Response<GiftResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetRewardGiftByIDSuccess(response.body(), response.message());
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
            public void onFailure(Call<GiftResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public void RedeemGiftData(Context context, String giftId, String Name, String phone, String Address) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).RedeemGift(giftId, Name, phone, Address);
        view.showHideProgress(true);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onRedeemRewardsSuccess(response.body(), response.message());
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

    public interface RewardsView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetRewardGiftSuccess(List<GiftResponse> response, String message);

        void onGetRewardGiftByIDSuccess(GiftResponse response, String message);

        void onRedeemRewardsSuccess(ResponseBody response, String message);


        void onFailure(Throwable t);
    }

}