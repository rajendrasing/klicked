package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.PlanResponse;
import com.dating.klicked.Model.ResponseRepo.PlanResponse;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlansPresenter {

    private PlansView view;

    public PlansPresenter(PlansView plansView) {
        this.view = plansView;
    }

    public void getAllPlans(Context context) {
        Call<List<PlanResponse>> call = AppUtils.KlickedApi(context).GetAllPlan();
        view.showHideProgress(true);
        call.enqueue(new Callback<List<PlanResponse>>() {
            @Override
            public void onResponse(Call<List<PlanResponse>> call, Response<List<PlanResponse>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetAllPlanSuccess(response.body(), response.message());
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
            public void onFailure(Call<List<PlanResponse>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public interface PlansView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetAllPlanSuccess(List<PlanResponse> planResponseList, String message);


        void onFailure(Throwable t);
    }

}
