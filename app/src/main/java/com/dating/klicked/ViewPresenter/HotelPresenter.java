package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.HotelResponse;
import com.dating.klicked.Model.ResponseRepo.HotelResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelPresenter {
    
    private HotelView view;

    public HotelPresenter(HotelView view) {
        this.view = view;
    }

    public void getAllHotel(Context context) {
        Call<List<HotelResponse>> call = AppUtils.KlickedApi(context).getAllHotel();
        view.showHideProgress(true);
        call.enqueue(new Callback<List<HotelResponse>>() {
            @Override
            public void onResponse(Call<List<HotelResponse>> call, Response<List<HotelResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetAllHotelSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<HotelResponse>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }
    
    public interface HotelView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetAllHotelSuccess(List<HotelResponse> HotelResponseList, String message);


        void onFailure(Throwable t);
    }

}
