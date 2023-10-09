package com.dating.klicked.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.dating.klicked.Activity.PlanAdapter.PlanParentAdapter;
import com.dating.klicked.Fragment.Adapter.PlanAdapter;
import com.dating.klicked.Model.ResponseRepo.PlanResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.ViewPresenter.PlanPurchasePresenter;
import com.dating.klicked.ViewPresenter.PlansPresenter;
import com.dating.klicked.databinding.ActivityPlanDetailsBinding;
import com.dating.klicked.databinding.ActivityPlanPurchaseBinding;
import com.dating.klicked.utils.AppUtils;


import com.irozon.sneaker.Sneaker;

import java.util.List;

public class PlanDetailsActivity extends AppCompatActivity implements PlansPresenter.PlansView, View.OnClickListener {
    ActivityPlanDetailsBinding binding;
    private View view;
    private Context context;
    private Dialog mDialog;
    PlansPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan_details);

        view = binding.getRoot();
        context = PlanDetailsActivity.this;

        presenter = new PlansPresenter(this);

        mDialog = AppUtils.hideShowProgress(context);
        presenter.getAllPlans(context);
        binding.back.setOnClickListener(this);

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
        Sneaker.with(PlanDetailsActivity.this)
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();

    }

    @Override
    public void onGetAllPlanSuccess(List<PlanResponse> planResponseList, String message) {
        if (message.equalsIgnoreCase("ok") && planResponseList.size() > 0 && planResponseList != null) {
            PlanParentAdapter planAdapter = new PlanParentAdapter(planResponseList, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false);
            binding.recyclerView.setLayoutManager(layoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setHasFixedSize(false);
            binding.recyclerView.setAdapter(planAdapter);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(binding.recyclerView);
            binding.indicator.attachToRecyclerView(binding.recyclerView);

        }

    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(PlanDetailsActivity.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
            onBackPressed();
            break;
        }
    }
}