package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.RequestRepo.UpdateProfile;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_Flipper_Presenter {
    private Flipper_View view;

    public View_Flipper_Presenter(Flipper_View view) {
        this.view = view;
    }

    public void getCountry(Context context) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).GetCountry();
        //  view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //        view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onCountrySuccess(response.body(), response.message());
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
                //      view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }

    public void getState(Context context, String id) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).GetState(id);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onStateSuccess(response.body(), response.message());
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

    public void getCity(Context context, String id) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).GetCity(id);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                 view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onCitySuccess(response.body(), response.message());
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

    public void getAllCard(Context context) {
        Call<List<CardResponse>> call = AppUtils.KlickedApi(context).GetAllCard();
        view.showHideProgress(true);
        call.enqueue(new Callback<List<CardResponse>>() {
            @Override
            public void onResponse(Call<List<CardResponse>> call, Response<List<CardResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetCardSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<CardResponse>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }

    public void getAllSubCard(Context context,String name) {
        Call<List<CardResponse>> call = AppUtils.KlickedApi(context).GetAllSubCard(name);
     //   view.showHideProgress(true);
        call.enqueue(new Callback<List<CardResponse>>() {
            @Override
            public void onResponse(Call<List<CardResponse>> call, Response<List<CardResponse>> response) {
        //        view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetSubCardSuccess(response.body(), response.message(),name);
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
            public void onFailure(Call<List<CardResponse>> call, Throwable t) {
            //    view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public void UpdateUserProfile(Context context, UpdateProfile profile) {
        Call<ResponseBody> call = AppUtils.KlickedApi(context).UpdateUserProfile(profile);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onUpdateProfileSuccess(response.body(), response.message());
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


    public interface Flipper_View {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onCountrySuccess(ResponseBody responseBody, String message);

        void onStateSuccess(ResponseBody responseBody, String message);

        void onCitySuccess(ResponseBody responseBody, String message);

        void onGetCardSuccess(List<CardResponse> cardResponses, String message);

        void onGetSubCardSuccess(List<CardResponse> cardResponses, String message,String name);

        void onUpdateProfileSuccess(ResponseBody responseBody, String message);


        void onFailure(Throwable t);
    }
}
