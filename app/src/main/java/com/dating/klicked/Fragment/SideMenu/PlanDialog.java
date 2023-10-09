package com.dating.klicked.Fragment.SideMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Activity.PlanPurchaseActivity;
import com.dating.klicked.Fragment.Adapter.PlanAdapter;
import com.dating.klicked.Model.ResponseRepo.PlanResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.PlansPresenter;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.List;

public class PlanDialog implements PlansPresenter.PlansView, PlanAdapter.onPlanClick {

    private Activity activity;
    private Dialog dialog, mDialog;

    private RecyclerView recyclerView;
    private LinearLayout btn_next;
    private TextView txt_next;
    PlansPresenter plansPresenter;
    String price, planName;

    public PlanDialog(Activity myActivity) {
        activity = myActivity;
    }

    @SuppressLint("InflateParams")
    public void startLoadingdialog() {


        dialog = new Dialog(activity);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_plan_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mDialog = AppUtils.hideShowProgress(activity);
        plansPresenter = new PlansPresenter(this);

        plansPresenter.getAllPlans(activity);


        recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        btn_next = (LinearLayout) dialog.findViewById(R.id.btn_next);
        txt_next = (TextView) dialog.findViewById(R.id.txt_next);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(activity.getApplicationContext(), PlanPurchaseActivity.class);
                        intent.putExtra("price", price);
                        intent.putExtra("planName", planName);
                        activity.startActivity(intent);
                        dismissdialog();
                    }
                }, 500);
            }
        });


        dialog.show();
    }

    // dismiss method
    public void dismissdialog() {
        dialog.dismiss();
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            mDialog.show();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            }, 1000);

        }
    }


    @Override
    public void onError(String message) {
        Sneaker.with(activity)
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onGetAllPlanSuccess(List<PlanResponse> planResponseList, String message) {
        if (message.equalsIgnoreCase("ok") && planResponseList.size() > 0 && planResponseList != null) {
            PlanAdapter planAdapter = new PlanAdapter(planResponseList, activity, this);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(dialog.getContext(), 3, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(planAdapter);
        }

    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(activity)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onPlanClickedListener(int position, List<PlanResponse> planResponseList) {
        price = planResponseList.get(position).getPrice();
        planName = planResponseList.get(position).getName();

        txt_next.setText("Get " + planResponseList.get(position).getDuration() + " Months ");


    }
}
