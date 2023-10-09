package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.CardSubCardResponse;
import com.dating.klicked.Model.ResponseRepo.FavMusicResponse;
import com.dating.klicked.Model.ResponseRepo.MainCardResponse;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviewPresenter {
    PreviewView view;

    public PreviewPresenter(PreviewView view) {
        this.view = view;
    }

    public void GetProfile(Context context) {
        view.showHideProgress(true);
        Call<UserProfileResponse> userCall = AppUtils.KlickedApi(context).GetUserProfile();
        userCall.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetProfileSuccess(response.body(), response.message());
                } else if (response.code() == 400) {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject object = new JSONObject(errorRes);
                        String err_msg = object.getString("body");
                        view.onError(err_msg);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void GetCardByUserId(Context context, String userId) {
        view.showHideProgress(true);
        Call<CardSubCardResponse> userCall = AppUtils.KlickedApi(context).getUserCardsById(userId);
        userCall.enqueue(new Callback<CardSubCardResponse>() {
            @Override
            public void onResponse(Call<CardSubCardResponse> call, Response<CardSubCardResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetCardSuccess(response.body(), response.message());
                } else if (response.code() == 400) {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject object = new JSONObject(errorRes);
                        String err_msg = object.getString("body");
                        view.onError(err_msg);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<CardSubCardResponse> call, Throwable t) {
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

    public interface PreviewView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetProfileSuccess(UserProfileResponse userProfileResponse, String message);


        void onGetCardSuccess(CardSubCardResponse cardSubCardResponse, String message);

        void onGetUSerCardSuccess(MainCardResponse cardResponses, String message);


        void onFailure(Throwable t);
    }
}

