package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.RequestRepo.FilterBody;
import com.dating.klicked.Model.RequestRepo.SpeedChatDeleteBody;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.HomeResponse;
import com.dating.klicked.Model.ResponseRepo.HomeResponse;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {

    private HomeView view;

    public HomePresenter(HomeView view) {
        this.view = view;
    }


    public void GetProfile(Context context, FilterBody body) {
        view.showHideProgress(true);
        Call<HomeResponse> userCall = AppUtils.KlickedApi(context).getHomeProfile(body);
        userCall.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetProfileSuccess(response.body(), response.message());
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
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void GetAllUserHomeProfile(Context context) {
        view.showHideProgress(true);
        Call<HomeResponse> userCall = AppUtils.KlickedApi(context).getAllHomeProfile();
        userCall.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetProfileSuccess(response.body(), response.message());
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
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void addUserFavourite(Context context, String userid) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).addFavouriteUser(userid);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onAddFavouriteSuccess(response.body(), response.message());
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

    public void SendRequest(Context context, String senderId, String receiverId) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).SendRequest(senderId, receiverId);
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

    public void BlockUser(Context context, String receiverId) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).BlockUser(receiverId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onBlockUserSuccess(response.body(), response.message());
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

    public void ReportUser(Context context, String receiverId, String reason) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).ReportUser(receiverId, reason);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onReportUserSuccess(response.body(), response.message());
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

    public void GetUserProfile(Context context) {
        view.showHideProgress(true);
        Call<UserProfileResponse> userCall = AppUtils.KlickedApi(context).GetUserProfile();
        userCall.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                //   view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetUserProfileSuccess(response.body(), response.message());
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
                // view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }


    public void DeleteSeenUser(Context context, SpeedChatDeleteBody body) {
       // view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).addSeenSwipeProfile(body);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
          //      view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onDeleteUserSeenSuccess(response.body(), response.message());
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
             //   view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }



    public interface HomeView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetProfileSuccess(HomeResponse homeResponse, String message);


        void onAddFavouriteSuccess(ResponseBody responseBody, String message);

        void onDeleteFavouriteSuccess(ResponseBody responseBody, String message);

        void onSendRequestSuccess(ResponseBody responseBody, String message);

        void onBlockUserSuccess(ResponseBody responseBody, String message);

        void onShareUserSuccess(String  shareUserId, String message);

        void onReportUserSuccess(ResponseBody responseBody, String message);

        void onGetUserProfileSuccess(UserProfileResponse userProfileResponse, String message);

        void onDeleteUserSeenSuccess(ResponseBody responseBody, String message);


        void onFailure(Throwable t);
    }
}
