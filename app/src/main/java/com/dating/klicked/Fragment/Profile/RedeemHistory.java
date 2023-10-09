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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dating.klicked.Fragment.Adapter.KlickedAdapter;
import com.dating.klicked.Fragment.Adapter.RewardsHistoryAdapter;
import com.dating.klicked.Model.ResponseRepo.RedeemHistoryResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.KlickedUserPresenter;
import com.dating.klicked.ViewPresenter.RedeemHistoryPresenter;
import com.dating.klicked.databinding.FragmentRedeemHistoryBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;


public class RedeemHistory extends Fragment implements RedeemHistoryPresenter.RedeemHistoryView, RewardsHistoryAdapter.RewardsRedeemListener {
    FragmentRedeemHistoryBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    RedeemHistoryPresenter presenter;


    public RedeemHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_redeem_history, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_redeem_history, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new RedeemHistoryPresenter(this);

        presenter.getAllRedeemHistory(getContext());


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
    public void onGetRedeemHistorySuccess(RedeemHistoryResponse response, String message) {
        if (message.equalsIgnoreCase("ok") && response != null && response.getResult().size() > 0 && response.getResult() != null) {
            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(layoutManager1);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(new RewardsHistoryAdapter(getContext(), response, this));

            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.VISIBLE);
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

    @Override
    public void onRewardsRedeemListener(RedeemHistoryResponse response, int position) {

       RedeemHistoryResponse redeemHistoryResponse = new RedeemHistoryResponse();
       redeemHistoryResponse.setResult(response.getResult());
       Bundle bundle = new Bundle();
       bundle.putInt("position",position);
       bundle.putSerializable("redeemHistoryResponse",redeemHistoryResponse);

       navController.navigate(R.id.action_redeemHistory_to_redeemHistoryDetails,bundle);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }
}