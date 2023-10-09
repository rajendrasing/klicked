package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.RequestRepo.VerifyOTP_Body;
import com.dating.klicked.Model.ResponseRepo.FavMusicResponse;
import com.dating.klicked.Model.ResponseRepo.UserResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddImagePresenter {

    private AddImageView view;

    public AddImagePresenter(AddImageView view) {
        this.view = view;
    }


    public void uploadProfileImage(Context context, MultipartBody.Part profileImage, RequestBody PhoneNo) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).uploadProfileImage(profileImage, PhoneNo);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onUploadProfileImageSuccess(response.body(), response.message());
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

    public void uploadSpecialFeatureImage(Context context, MultipartBody.Part specialFeatureImage, RequestBody PhoneNo,RequestBody Position) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).uploadSpecialFeatureImage(specialFeatureImage, PhoneNo,Position);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onUploadspecialFeatureImageSuccess(response.body(), response.message());
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


    public void UserLogin(Context context, VerifyOTP_Body body) {
        Call<UserResponse> call = AppUtils.KlickedApi(context).UserLogin(body);
        view.showHideProgress(true);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onUserLoginSuccess(response.body(), response.message());
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
            public void onFailure(Call<UserResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public interface AddImageView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onUploadProfileImageSuccess(ResponseBody responseBody, String message);

        void onUploadspecialFeatureImageSuccess(ResponseBody responseBody, String message);

        void onUserLoginSuccess(UserResponse responseBody, String message);

        void onFailure(Throwable t);
    }
}
