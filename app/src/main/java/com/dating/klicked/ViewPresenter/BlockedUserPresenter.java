package com.dating.klicked.ViewPresenter;

import android.content.Context;

import com.dating.klicked.Model.ResponseRepo.BlockUserListResponse;

import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockedUserPresenter {
    private BlockedUserView view;

    public BlockedUserPresenter(BlockedUserView view) {
        this.view = view;
    }

    public void getAllBlockedUserList(Context context) {
        Call<BlockUserListResponse> call = AppUtils.KlickedApi(context).BLOCK_USER_LIST();
        view.showHideProgress(true);
        call.enqueue(new Callback<BlockUserListResponse>() {
            @Override
            public void onResponse(Call<BlockUserListResponse> call, Response<BlockUserListResponse> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetBlockedUserListSuccess(response.body(), response.message());
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
            public void onFailure(Call<BlockUserListResponse> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }

    public void UnBlockUser(Context context, String id){
        Call<ResponseBody>call = AppUtils.KlickedApi(context).UnBlockUser(id);
     //   view.showHideProgress(true);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
       //         view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onUnBlockedUserSuccess(response.body(), response.message());
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
           //     view.showHideProgress(false);
                view.onFailure(t);

            }
        });
    }

    public interface BlockedUserView {

        void showHideProgress(boolean isShow);

        void onError(String message);

        void onGetBlockedUserListSuccess(BlockUserListResponse response, String message);

        void onUnBlockedUserSuccess(ResponseBody response, String message);


        void onFailure(Throwable t);
    }
}
