package com.dating.klicked.Fragment.SideMenu;

import android.app.Dialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Fragment.Adapter.HintsAdapter;
import com.dating.klicked.Fragment.Adapter.SubHintsAdapter;
import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.Model.ResponseRepo.SubHintsResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.HintsPresenter;
import com.dating.klicked.databinding.FragmentSubHintsBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

public class SubHintsFragment extends Fragment implements SubHintsAdapter.onClickSubHints, HintsPresenter.HintsView {
    FragmentSubHintsBinding binding;
    private Dialog dialog;
    private View view;
    NavController navController;
    HintsPresenter presenter;


    public SubHintsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //   return inflater.inflate(R.layout.fragment_sub_hints, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_hints, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new HintsPresenter(this);

        if (getArguments().getString("quesId")!=null){
            presenter.GetSubHints(getContext(),getArguments().getString("quesId"));
        }


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }


    @Override
    public void onClickSubHintsListener(int position , List<SubHintsResponse>list) {
        SubHintsResponse subHintsResponse  = new SubHintsResponse();
        subHintsResponse.setId(list.get(position).getId());
        subHintsResponse.setTitle(list.get(position).getTitle());
        subHintsResponse.setContent(list.get(position).getContent());
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("SubHints",subHintsResponse);

        navController.navigate(R.id.action_subHintsFragment_to_fullHintsFragment,mBundle);
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
    public void onGETHintsSuccess(List<FAQResponse> list, String message) {

    }

    @Override
    public void onSubHintsSuccess(List<SubHintsResponse> list, String message) {
        if (message.equalsIgnoreCase("ok") && list.size() > 0 && list != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.recyclerView.setLayoutManager(layoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setHasFixedSize(true);
            SubHintsAdapter subHintsAdapter = new SubHintsAdapter(getContext(), list,this);
            binding.recyclerView.setAdapter(subHintsAdapter);
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
}