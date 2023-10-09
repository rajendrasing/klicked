package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.RequestRepo.UpdateProfile;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.MainCardResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardPresenter {

    CardView view;

    public CardPresenter(CardView view) {
        this.view = view;
    }

    public void getAllCard(Context context) {
        Call<List<CardResponse>> call = AppUtils.KlickedApi(context).GetAllCard();
        view.showHideProgress(true);
        call.enqueue(new Callback<List<CardResponse>>() {
            @Override
            public void onResponse(Call<List<CardResponse>> call, Response<List<CardResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetCardSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<CardResponse>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }

    public void getAllUserCard(Context context, String userId) {
        Call<MainCardResponse> call = AppUtils.KlickedApi(context).MAIN_CARD_RESPONSE_CALL(userId);
        view.showHideProgress(true);
        call.enqueue(new Callback<MainCardResponse>() {
            @Override
            public void onResponse(Call<MainCardResponse> call, Response<MainCardResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetUSerCardSuccess(response.body(), response.message());
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
            public void onFailure(Call<MainCardResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public void UpdateUserCard(Context context, UpdateProfile profile) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).UpdateUserProfile(profile);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onUpdateCardUser(response.body(), response.message());
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


    public interface CardView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetCardSuccess(List<CardResponse> cardResponses, String message);

        void onGetUSerCardSuccess(MainCardResponse cardResponses, String message);

        void onUpdateCardUser(ResponseBody responseBody, String message);


        void onFailure(Throwable t);
    }
}
