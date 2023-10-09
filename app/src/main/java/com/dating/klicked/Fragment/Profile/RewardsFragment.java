package com.dating.klicked.Fragment.Profile;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dating.klicked.Authentication.View_Flipper;
import com.dating.klicked.Fragment.Adapter.ProfileAdapter;
import com.dating.klicked.Fragment.Adapter.RewardsAdapter;
import com.dating.klicked.Fragment.Adapter.RewardsFaqAdapter;
import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.Model.ResponseRepo.RewardHistoryResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.RewardsPresenter;
import com.dating.klicked.databinding.FragmentRewardsBinding;
import com.dating.klicked.utils.AppUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.irozon.sneaker.Sneaker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class RewardsFragment extends Fragment implements View.OnClickListener, RewardsPresenter.RewardsView {
    FragmentRewardsBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    RewardsPresenter presenter;
    BottomSheetDialog bottomSheetDialog;
    User_Data userData;

    public RewardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rewards, container, false);
        view = binding.getRoot();
        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();
        dialog = AppUtils.hideShowProgress(getContext());
        presenter = new RewardsPresenter(this);
        presenter.getAllRewardsHistory(getContext());

        binding.imgBack.setOnClickListener(this);
        binding.imgFaq.setOnClickListener(this);
        binding.cardRedeem.setOnClickListener(this);
        binding.cardHistory.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_back:
                getActivity().onBackPressed();
                break;

            case R.id.img_Faq:
                presenter.getAllOtherFAQData(getContext(), "Reward");
                break;

            case R.id.card_redeem:
                navController.navigate(R.id.action_rewardsFragment_to_rewardRedeemFragment);
                break;

            case R.id.cardHistory:
                navController.navigate(R.id.action_rewardsFragment_to_redeemHistory);
                break;

        }
    }

    private void Gender_Bottom_Sheet(List<FAQResponse> response) {

        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.custom_rewardfaq_layout);
        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recyclerView);
        TextView unshow_text = bottomSheetDialog.findViewById(R.id.unshow_text);
        ImageView imd_no_dta = bottomSheetDialog.findViewById(R.id.imd_no_dta);

        if (response.size() > 0 && response != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(new RewardsFaqAdapter(getContext(), response));
            recyclerView.setVisibility(View.VISIBLE);
            imd_no_dta.setVisibility(View.GONE);
            unshow_text.setVisibility(View.GONE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            imd_no_dta.setVisibility(View.VISIBLE);
            unshow_text.setVisibility(View.VISIBLE);
        }

        bottomSheetDialog.show();
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


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
        if (message.equalsIgnoreCase("You Have no rewards Yet")) {

            binding.recyclerView.setVisibility(View.GONE);
            binding.text2.setVisibility(View.GONE);
            binding.txtPoint.setText("00");
        } else {

            Sneaker.with(getActivity())
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        }
    }

    @Override
    public void onGetRewardHistorySuccess(RewardHistoryResponse response, String message) {
        if (message.equalsIgnoreCase("ok") && response != null && response.getReward() != null && response.getReward().size() > 0) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(layoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(new RewardsAdapter(getContext(), response, userData.getId()));
            binding.txtPoint.setText(response.getReward().get(0).getUserId().getTotalReward());
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.text2.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.text2.setVisibility(View.GONE);
            binding.txtPoint.setText("00");
        }

    }

    @Override
    public void onGetAllOtherFAQSuccess(List<FAQResponse> response, String message) {
        if (message.equalsIgnoreCase("Ok")) {
            Gender_Bottom_Sheet(response);
        }
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
}