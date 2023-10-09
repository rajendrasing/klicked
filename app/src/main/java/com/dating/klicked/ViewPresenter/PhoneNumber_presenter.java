package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.CountryCode;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneNumber_presenter {

    private PhoneNumberView view;

    public PhoneNumber_presenter(PhoneNumberView view) {
        this.view = view;
    }

    public void GetCountryCode(Context context) {
        view.showHideProgress(true);
        Call<List<CountryCode>> userCall = AppUtils.PostmanApi(context).GetAllCountriesCode();
        userCall.enqueue(new Callback<List<CountryCode>>() {
            @Override
            public void onResponse(Call<List<CountryCode>> call, Response<List<CountryCode>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetCountryCodeSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<CountryCode>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void SignUP(Context context,String Phone) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).SignUp(Phone);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSignUPSuccess(response.body(), response.message());
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

    public void SendOTP(Context context, String Phone) {
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.KlickedApi(context).SendOtp(Phone);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSendOTPSuccess(response.body(), response.message());
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

    public interface PhoneNumberView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetCountryCodeSuccess(List<CountryCode> countryCodesList, String message);

        void onSignUPSuccess(ResponseBody responseBody, String message);

        void onSendOTPSuccess(ResponseBody responseBody, String message);

        void onFailure(Throwable t);
    }
}
