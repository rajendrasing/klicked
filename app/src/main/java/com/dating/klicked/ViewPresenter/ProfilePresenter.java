package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.KlickedResponse;
import com.dating.klicked.Model.ResponseRepo.ViewedProfiledResponse;
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

public class ProfilePresenter {
    private ProfileView view;

    public ProfilePresenter(ProfileView view) {
        this.view = view;
    }

    public void uploadProfileImage(Context context, MultipartBody.Part ProfileImage, RequestBody PhoneNo) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).uploadProfileImage(ProfileImage, PhoneNo);
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

    public void GetKlickedUserSuccess(Context context) {
        view.showHideProgress(true);
        Call<KlickedResponse> userCall = AppUtils.KlickedApi(context).getKlickedUser();
        userCall.enqueue(new Callback<KlickedResponse>() {
            @Override
            public void onResponse(Call<KlickedResponse> call, Response<KlickedResponse> response) {
            //    view.showHideProgress(false);
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
              //  view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }


    public void ViewProfile(Context context){
     //   view.showHideProgress(true);
        Call<List<ViewedProfiledResponse>>call = AppUtils.KlickedApi(context).GetDataViewedProfile();
        call.enqueue(new Callback<List<ViewedProfiledResponse>>() {
            @Override
            public void onResponse(Call<List<ViewedProfiledResponse>> call, Response<List<ViewedProfiledResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetViewedProfileSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<ViewedProfiledResponse>> call, Throwable t) {

                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }


    public interface ProfileView {


        void showHideProgress(boolean show);

        void onError(String message);

        void onUploadspecialFeatureImageSuccess(ResponseBody responseBody, String message);

        void onGetKlickedUserSuccess(KlickedResponse klickedResponse, String message);


        void onGetViewedProfileSuccess(List<ViewedProfiledResponse> list, String message);

        void onFailure(Throwable t);
    }
}
