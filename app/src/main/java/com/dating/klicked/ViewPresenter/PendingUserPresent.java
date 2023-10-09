package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.PendingResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingUserPresent {

    private PendingUserView view;

    public PendingUserPresent(PendingUserView view) {
        this.view = view;
    }


    public void GetPendingUserSuccess(Context context) {
        view.showHideProgress(true);
        Call<PendingResponse> userCall = AppUtils.KlickedApi(context).GetPendingUser();
        userCall.enqueue(new Callback<PendingResponse>() {
            @Override
            public void onResponse(Call<PendingResponse> call, Response<PendingResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetPendingUserSuccess(response.body(), response.message());
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
            public void onFailure(Call<PendingResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void GetPendingCancelUser(Context context,String docId) {
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


    public interface PendingUserView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetPendingUserSuccess(PendingResponse response, String message);

        void onCancelUserSuccess(ResponseBody response, String message);


        void onFailure(Throwable t);
    }
}
