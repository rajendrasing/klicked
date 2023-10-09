package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.CardSubCardResponse;
import com.dating.klicked.Model.ResponseRepo.HomeResponse;
import com.dating.klicked.Model.ResponseRepo.MainCardResponse;
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

public class UserDetailsByIdPresenter {

    private UserDetailsByIdView view;

    public UserDetailsByIdPresenter(UserDetailsByIdView view) {
        this.view = view;
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


    public void GetUserByIDProfile(Context context, String UserID, List<CardResponse> cardResponses) {
        Call<UserProfileResponse> call = AppUtils.KlickedApi(context).GetUserByIdProfile(UserID);
        view.showHideProgress(true);
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetUserByIDProfileSuccess(response.body(), response.message(), cardResponses);
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
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
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

    public void GetPendingCancelUser(Context context, String docId) {
        //  view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).CancelPending(docId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onCancelUserSuccess(response.body(), response.message());
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
                // view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void GetRequestCancelUser(Context context, String docId) {
        //   view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).CancelRequest(docId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onCancelRequestUserSuccess(response.body(), response.message());
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
                // view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void GetRequestAcceptUser(Context context, String docId) {
        //  view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).AcceptRequest(docId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onAcceptRequestUserSuccess(response.body(), response.message());
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
                // view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void CreateChatDocumentUser(Context context, String ReceiverID, String ReceiverName) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).crateChatDocument(ReceiverID, ReceiverName);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onCreateChatUserSuccess(response.body(), response.message());
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

    public void ViewedProfile(Context context, String userid) {

        Call<ResponseBody> call = AppUtils.KlickedApi(context).PutDataViewedProfile(userid);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onViewedProfileSuccess(response.body(), response.message());
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

    public void ShareUserProfile(Context context, String profileUserId, String loginUserid) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).ShareUserProfile(loginUserid, profileUserId);
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


    public void OnGetKlickedMeter(Context context, String ReceiverId) {
        //  view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).GetKlickedMeter(ReceiverId);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //   view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetKlickedMeterSuccess(response.body(), response.message());
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
                // view.showHideProgress(false);
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


    public interface UserDetailsByIdView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetCardSuccess(CardSubCardResponse cardResponses, String message);

        void onGetUserByIDProfileSuccess(UserProfileResponse userProfileResponse, String message, List<CardResponse> cardResponses);

        void onAddFavouriteSuccess(ResponseBody responseBody, String message);

        void onDeleteFavouriteSuccess(ResponseBody responseBody, String message);

        void onSendRequestSuccess(ResponseBody responseBody, String message);

        void onCancelUserSuccess(ResponseBody response, String message);

        void onCancelRequestUserSuccess(ResponseBody response, String message);

        void onAcceptRequestUserSuccess(ResponseBody response, String message);

        void onCreateChatUserSuccess(ResponseBody response, String message);

        void onViewedProfileSuccess(ResponseBody response, String message);

        void onBlockUserSuccess(ResponseBody responseBody, String message);

        void onReportUserSuccess(ResponseBody responseBody, String message);

        void onShareUserSuccess(String shareUserId, String message);

        void onGetKlickedMeterSuccess(ResponseBody responseBody, String message);

        void onGetUSerCardSuccess(MainCardResponse cardResponses, String message);


        void onFailure(Throwable t);
    }
}
