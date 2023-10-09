package com.dating.klicked.ViewPresenter;

import android.content.Context;
import android.util.Log;

import com.dating.klicked.Model.ResponseRepo.CountryCode;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecificFeaturesPresenter {

    private SpecificFeaturesView view;

    public SpecificFeaturesPresenter(SpecificFeaturesView view) {
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

    public void uploadSpecialFeatureImage(Context context, MultipartBody.Part specialFeatureImage, RequestBody PhoneNo, RequestBody Position) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).uploadSpecialFeatureImage(specialFeatureImage, PhoneNo, Position);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onUploadspecialFeatureImageSuccess(response.body(), response.message());
                } else if (response.code()==413) {
                    view.onError("image size is too large");
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorRes);
                        String err_msg = jsonObject.getString("error");
                        view.onError(err_msg);
                        Log.d("response code", String.valueOf(response.code()));
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


    public void DeleteSpecialFeature(Context context, String Id, String phone) {
        //   view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).DeleteSpecialFeature(Id, phone);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //  view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onDeleteSpecialFeature(response.body(), response.message());
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //    view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }


    public interface SpecificFeaturesView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetProfileSuccess(UserProfileResponse userProfileResponse, String message);

        void onUploadspecialFeatureImageSuccess(ResponseBody responseBody, String message);


        void onDeleteSpecialFeature(ResponseBody responseBody, String message);


        void onFailure(Throwable t);
    }
}
