package com.dating.klicked.Fragment.Profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Fragment.Adapter.FavPersonAdapter;
import com.dating.klicked.Fragment.Adapter.RecentFavAdapter;
import com.dating.klicked.Fragment.Adapter.RewardsAdapter;
import com.dating.klicked.Fragment.Adapter.RewardsRedeemAdapter;
import com.dating.klicked.Model.ResponseRepo.GiftResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.RewardsRedeemPresenter;
import com.dating.klicked.databinding.FragmentRewardRedeemBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.GridSpacingItemDecoration;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class RewardRedeemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RewardsRedeemPresenter.RewardsView, RewardsRedeemAdapter.RewardsClickedListener {
    FragmentRewardRedeemBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    RewardsRedeemPresenter presenter;


    public RewardRedeemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_reward_redeem, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reward_redeem, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new RewardsRedeemPresenter(this);

        presenter.getAllGiftData(getContext());
        binding.SwipeRefresh.setOnRefreshListener(this);
        binding.SwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.global__primary));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }


    @Override
    public void onRefresh() {

        presenter.getAllGiftData(getContext());

        binding.SwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 1000);

        }
    }

    @Override
    public void onError(String message) {
        Sneaker.with(getActivity())
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onGetRewardGiftSuccess(List<GiftResponse> response, String message) {
        if (message.equalsIgnoreCase("ok") && response != null && response.size() > 0) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
            int spanCount = 2; // 3 columns
            int spacing = 40; // 50px
            boolean includeEdge = false;
            binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(layoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(new RewardsRedeemAdapter(getContext(), response, this));

            binding.recyclerView.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onGetRewardGiftByIDSuccess(GiftResponse response, String message) {

    }

    @Override
    public void onRedeemRewardsSuccess(ResponseBody response, String message) {

    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(getActivity())
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onRewardsClickedListener(String productId) {

        Bundle bundle = new Bundle();
        bundle.putString("id",productId);
        navController.navigate(R.id.action_rewardRedeemFragment_to_rewardsDetailsFragment,bundle);

    }
}