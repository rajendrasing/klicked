package com.dating.klicked.Fragment.SideMenu;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Fragment.Adapter.RewardsFaqAdapter;
import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.OtherFAQPresenter;
import com.dating.klicked.databinding.BoostFaqBottomsheetBinding;
import com.dating.klicked.utils.AppUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.irozon.sneaker.Sneaker;

import java.util.List;

public class BoostFAQBottomSheet extends BottomSheetDialogFragment implements OtherFAQPresenter.OtherFAQView {

    BoostFaqBottomsheetBinding binding;
    private View view;
    private Dialog dialog;
    OtherFAQPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.boost_faq_bottomsheet, container, false);

        view = binding.getRoot();

        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new OtherFAQPresenter(this);

        presenter.getAllOtherFAQData(getContext(),"Boost");

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
    public void onGetAllOtherFAQSuccess(List<FAQResponse> response, String message) {
        if (message.equalsIgnoreCase("Ok") && response.size()>0 && response!=null){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(layoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(new RewardsFaqAdapter(getContext(), response));
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);
        }else {
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
}
