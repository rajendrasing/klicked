package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.FavouriteResponse;
import com.dating.klicked.Model.ResponseRepo.KlickedResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KlickedUserPresenter {

    private KlickedUserView view;

    public KlickedUserPresenter(KlickedUserView view) {
        this.view = view;
    }

    public void GetKlickedUserSuccess(Context context) {
        view.showHideProgress(true);
        Call<KlickedResponse> userCall = AppUtils.KlickedApi(context).getKlickedUser();
        userCall.enqueue(new Callback<KlickedResponse>() {
            @Override
            public void onResponse(Call<KlickedResponse> call, Response<KlickedResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetKlickedUserSuccess(response.body(), response.message());
                } else if (response.code() == 400) {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject object = new JSONObject(errorRes);
                        String err_msg = object.getString("error");
                        view.onError(err_msg);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<KlickedResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }


    public void GetKlickedDeleteUser(Context context, String userId) {
       view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).UserDelete(userId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onDeleteKlickedUserSuccess(response.body(), response.message());
                } else if (response.code() == 400) {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject object = new JSONObject(errorRes);
                        String err_msg = object.getString("error");
                        view.onError(err_msg);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }


    public interface KlickedUserView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetKlickedUserSuccess(KlickedResponse klickedResponse, String message);

        void onDeleteKlickedUserSuccess(ResponseBody body, String message);


        void onFailure(Throwable t);
    }
}
