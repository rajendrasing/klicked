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
import androidx.navigation.ui.NavigationUI;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Model.ResponseRepo.GiftResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.ViewPresenter.RewardsRedeemPresenter;
import com.dating.klicked.databinding.FragmentRewardsDetailsBinding;
import com.dating.klicked.utils.AppUtils;
import com.denzcoskun.imageslider.models.SlideModel;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;


public class RewardsDetailsFragment extends Fragment implements RewardsRedeemPresenter.RewardsView, View.OnClickListener {
    String productId;
    FragmentRewardsDetailsBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    RewardsRedeemPresenter presenter;


    public RewardsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_rewards_details, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rewards_details, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new RewardsRedeemPresenter(this);

        presenter.getGiftDataById(getContext(), getArguments().getString("id"));

        binding.txtRedeem.setOnClickListener(this);
        return binding.getRoot();
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

    }

    @Override
    public void onGetRewardGiftByIDSuccess(GiftResponse response, String message) {
        List<SlideModel> BannerManufacturer = new ArrayList<>();

        if (message.equalsIgnoreCase("ok") && response != null) {
            for (int i = 0; i < response.getImages().size(); i++) {
                BannerManufacturer.add(new SlideModel(PrefConf.IMAGE_URL + response.getImages().get(i), null));
            }
        }
        productId = response.getId();
        binding.slider.setImageList(BannerManufacturer);
        binding.txtName.setText(response.getName());
        binding.txtPoint.setText(response.getRewardCharges() + "PT");
        binding.textDis.setText(response.getDescription());


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
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txt_redeem:
                Bundle bundle = new Bundle();
                bundle.putString("id",productId);
                navController.navigate(R.id.action_rewardsDetailsFragment_to_redeemGiftFragment,bundle);
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController =  Navigation.findNavController(view);
    }
}