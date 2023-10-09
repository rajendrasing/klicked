package com.dating.klicked.Fragment.Profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.MainActivity;
import com.dating.klicked.Model.ResponseRepo.GiftResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.RewardsRedeemPresenter;
import com.dating.klicked.databinding.FragmentRedeemGiftBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.List;

import okhttp3.ResponseBody;


public class RedeemGiftFragment extends Fragment implements View.OnClickListener, RewardsRedeemPresenter.RewardsView {
    String productId;
    FragmentRedeemGiftBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    RewardsRedeemPresenter presenter;
    private AppUtils psDialogMsg;

    public RedeemGiftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_redeem_gift, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_redeem_gift, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new RewardsRedeemPresenter(this);

        productId = getArguments().getString("id");
        binding.txtConfirm.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_Confirm:
                String name = binding.edName.getText().toString();
                String phone = binding.edPhoneNo.getText().toString();
                String address = binding.edAddress.getText().toString();
                if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Sneaker.with(getActivity())
                            .setTitle("All Filed is required")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakWarning();
                } else if (phone.length() > 9 || phone.length() < 9) {
                    Sneaker.with(getActivity())
                            .setTitle("Please Enter Valid no.")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakWarning();
                } else {

                    presenter.RedeemGiftData(getContext(), productId, name, phone, address);
                }

                break;
        }

    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {
            dialog.dismiss();
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

    }

    @Override
    public void onRedeemRewardsSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {


            String des = getContext().getString(R.string.txt_rew_dia);
            psDialogMsg = new AppUtils(getActivity(), false);
            psDialogMsg.showFAQLogoutDialog("Successful", des, "Back to Home", getActivity().getDrawable(R.drawable.ic_faq_pop_image));
            psDialogMsg.show();
            psDialogMsg.txt_ready.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    psDialogMsg.cancel();
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }
            });
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