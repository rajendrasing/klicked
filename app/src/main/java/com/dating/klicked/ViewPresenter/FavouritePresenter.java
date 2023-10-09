package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.CountryCode;
import com.dating.klicked.Model.ResponseRepo.FavouriteResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritePresenter {

    private FavouriteView view;

    public FavouritePresenter(FavouriteView view) {
        this.view = view;
    }


    public void GetRecentFavouriteSuccess(Context context) {
     //   view.showHideProgress(true);
        Call<List<FavouriteResponse>> userCall = AppUtils.KlickedApi(context).getRecentFavouriteUser();
        userCall.enqueue(new Callback<List<FavouriteResponse>>() {
            @Override
            public void onResponse(Call<List<FavouriteResponse>> call, Response<List<FavouriteResponse>> response) {
          //      view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetRecentFavouriteSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<FavouriteResponse>> call, Throwable t) {
             //   view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void GetFavouriteSuccess(Context context) {
        view.showHideProgress(true);
        Call<List<FavouriteResponse>> userCall = AppUtils.KlickedApi(context).getFavouriteUser();
        userCall.enqueue(new Callback<List<FavouriteResponse>>() {
            @Override
            public void onResponse(Call<List<FavouriteResponse>> call, Response<List<FavouriteResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetFavouriteSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<FavouriteResponse>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void deleteFavouriteUser(Context context, String userid) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).DeleteFavouriteUser(userid);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onDeleteFavouriteSuccess(response.body(), response.message());
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

    public void SendRequest(Context context, String senderId,String receiverId) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).SendRequest(senderId,receiverId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSendRequestSuccess(response.body(), response.message());
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

    public void ShareUserProfile(Context context, String profileUserId,String loginUserid) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).ShareUserProfile(loginUserid,profileUserId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onShareUserSuccess(profileUserId, response.message());
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


    public interface FavouriteView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetRecentFavouriteSuccess(List<FavouriteResponse> RecentFavouriteResponse, String message);

        void onGetFavouriteSuccess(List<FavouriteResponse> favouriteResponses, String message);

        void onDeleteFavouriteSuccess(ResponseBody responseBody, String message);

        void onSendRequestSuccess(ResponseBody responseBody, String message);

        void onShareUserSuccess(String  shareUserId, String message);


        void onFailure(Throwable t);
    }
}
