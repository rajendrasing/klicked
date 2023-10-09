package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.FavMusicResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AudioDesPresenter {

    private AudioDesView view;

    public AudioDesPresenter(AudioDesView view) {
        this.view = view;
    }

    public void uploadAudioDes(Context context, MultipartBody.Part audioDes, RequestBody PhoneNo) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).uploadAudioDes(audioDes, PhoneNo);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onAudioDesUploadSuccess(response.body(), response.message());
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

    public void uploadFavMusic(Context context, MultipartBody.Part FavMusic, RequestBody PhoneNo) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).uploadFavMusic(FavMusic, PhoneNo);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onFavMusicUploadSuccess(response.body(), response.message());
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

    public void GetFavMusic(Context context, String PhoneNo) {
        Call<FavMusicResponse> call = AppUtils.KlickedApi(context).GetFavMusic(PhoneNo);
        view.showHideProgress(true);
        call.enqueue(new Callback<FavMusicResponse>() {
            @Override
            public void onResponse(Call<FavMusicResponse> call, Response<FavMusicResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetFavMusicSuccess(response.body(), response.message());
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
            public void onFailure(Call<FavMusicResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }

    public void DeleteFavMusic(Context context, String favMusic,String Phone) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).DeleteFavMusic(favMusic,Phone);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onDeleteFavMusicSuccess(response.body(), response.message());
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



    public interface AudioDesView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onAudioDesUploadSuccess(ResponseBody responseBody, String message);

        void onFavMusicUploadSuccess(ResponseBody responseBody, String message);

        void onGetFavMusicSuccess(FavMusicResponse favMusicResponse, String message);

        void onDeleteFavMusicSuccess(ResponseBody responseBody, String message);

        void onFailure(Throwable t);
    }
}
