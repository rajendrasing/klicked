package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.RequestRepo.RazorCaptureBody;
import com.dating.klicked.Model.RequestRepo.RazorSaveDataBody;
import com.dating.klicked.Model.ResponseRepo.CapturePaymentSuccessResp;
import com.dating.klicked.Model.ResponseRepo.PaymentDataSaveResp;
import com.dating.klicked.Model.ResponseRepo.RazorOrderResp;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanPurchasePresenter {

    private  PlanPurchaseView view;

    public PlanPurchasePresenter(PlanPurchaseView view) {
        this.view = view;
    }

    public void razorOrder(String amount, Context context){
        view.showHideProgress(true);
        Call<RazorOrderResp> call = AppUtils.KlickedApi(context).createRazorOrder(amount);
        call.enqueue(new Callback<RazorOrderResp>() {
            @Override
            public void onResponse(Call<RazorOrderResp> call, Response<RazorOrderResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onRazorOrderSuccess(response.body(),response.message());
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject =new JSONObject(errorRes);
                        String err_msg  = jsonObject.getString("error");
                        int status= jsonObject.getInt("status");
                        view.onError(err_msg);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RazorOrderResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void razorCapture(RazorCaptureBody razorCaptureBody, Context context){
        view.showHideProgress(true);
        Call<CapturePaymentSuccessResp>call = AppUtils.KlickedApi(context).razorCapturePayment(razorCaptureBody);
        call.enqueue(new Callback<CapturePaymentSuccessResp>() {
            @Override
            public void onResponse(Call<CapturePaymentSuccessResp> call, Response<CapturePaymentSuccessResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onRazorCaptureSuccess(response.body(),response.message());
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject =new JSONObject(errorRes);
                        String err_msg  = jsonObject.getString("error");
                        int status= jsonObject.getInt("status");
                        view.onError(err_msg);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CapturePaymentSuccessResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void savePaymentData(String orderId, RazorSaveDataBody razorSaveDataBody, Context context){
        view.showHideProgress(true);
        Call<PaymentDataSaveResp>call = AppUtils.KlickedApi(context).razorDataSave(orderId,razorSaveDataBody);
        call.enqueue(new Callback<PaymentDataSaveResp>() {
            @Override
            public void onResponse(Call<PaymentDataSaveResp> call, Response<PaymentDataSaveResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    System.out.println("ssss "+response.body());
                    view.onRazorDataSaved(response.body(), response.message());
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        System.out.println("ssss er "+errorRes);
                        JSONObject jsonObject =new JSONObject(errorRes);
                        String err_msg  = jsonObject.getString("error");
                        int status= jsonObject.getInt("status");
                        view.onError(err_msg);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentDataSaveResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public interface PlanPurchaseView {
        void showHideProgress(boolean isShow);

        void onError(String message);

        void onRazorOrderSuccess(RazorOrderResp razorOrderResp, String message);

        void onRazorCaptureSuccess(CapturePaymentSuccessResp successResp, String message);

        void onRazorDataSaved(PaymentDataSaveResp responseBody, String message);

        void onFailure(Throwable t);
    }
}
