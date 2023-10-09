package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.ViewedProfiledResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewedProfilePresenter {

    private ViewedProfileView view;

    public ViewedProfilePresenter(ViewedProfileView view) {
        this.view = view;
    }

    public void ViewProfile(Context context){
        view.showHideProgress(true);
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



    public interface ViewedProfileView{

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetViewedProfileSuccess(List<ViewedProfiledResponse> list, String message);


        void onFailure(Throwable t);

    }
}
