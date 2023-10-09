package com.dating.klicked.Fragment.Profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dating.klicked.Model.ResponseRepo.RedeemHistoryResponse;
import com.dating.klicked.Model.ResponseRepo.SubHintsResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.ViewPresenter.RedeemHistoryPresenter;
import com.dating.klicked.databinding.FragmentRedeemHistoryBinding;
import com.dating.klicked.databinding.FragmentRedeemHistoryDetailsBinding;
import com.dating.klicked.utils.AppUtils;

public class RedeemHistoryDetails extends Fragment {
    FragmentRedeemHistoryDetailsBinding binding;
    private Context context;
    private View view;
    RedeemHistoryResponse redeemHistoryResponse;
    int position;

    public RedeemHistoryDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_redeem_history_details, container, false);
        view = binding.getRoot();

        redeemHistoryResponse = (RedeemHistoryResponse) getArguments().getSerializable("redeemHistoryResponse");
        position = getArguments().getInt("position");


        Glide.with(getContext()).load(PrefConf.IMAGE_URL + redeemHistoryResponse.getResult().get(position).getGiftId().getImages().get(0))
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.proImg);

        binding.name.setText(redeemHistoryResponse.getResult().get(position).getGiftId().getName());
        binding.price.setText(redeemHistoryResponse.getResult().get(position).getAmount() + "PT");

        if (redeemHistoryResponse.getResult().get(position).getName() != null) {
            binding.txtName.setText(redeemHistoryResponse.getResult().get(position).getName());

        }
        binding.txtAddress.setText(redeemHistoryResponse.getResult().get(position).getAddress() + "\n" + redeemHistoryResponse.getResult().get(position).getPhone());


        binding.txtdate.setText(AppUtils.getDateTime1(redeemHistoryResponse.getResult().get(position).getCreatedAt(), "dd-MM-YYYY"));
        binding.txtstatus.setText(redeemHistoryResponse.getResult().get(position).getStatus());


        return binding.getRoot();

        // return inflater.inflate(R.layout.fragment_redeem_history_details, container, false);

    }
}