package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.Model.ResponseRepo.UserResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoostProfilePresenter {

    public BoostProfileView view;

    public BoostProfilePresenter(BoostProfileView view) {
        this.view = view;
    }


    public void BoostProfile(Context context, MultipartBody.Part DocImage, RequestBody DocNo,RequestBody DocType) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).uploadBoostProfile(DocImage, DocNo,DocType);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onUploadKYCSuccess(response.body(), response.message());
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


    public interface BoostProfileView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onUploadKYCSuccess(ResponseBody responseBody, String message);

        void onGetProfileSuccess(UserProfileResponse userProfileResponse, String message);


        void onFailure(Throwable t);
    }
}
