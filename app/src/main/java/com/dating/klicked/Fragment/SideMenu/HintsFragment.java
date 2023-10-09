package com.dating.klicked.Fragment.SideMenu;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Fragment.Adapter.ChatAdapter;
import com.dating.klicked.Fragment.Adapter.FeedbackAdapter;
import com.dating.klicked.Fragment.Adapter.HintsAdapter;
import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.Model.ResponseRepo.SubHintsResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.HintsPresenter;
import com.dating.klicked.databinding.FragmentHintsBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;


public class HintsFragment extends Fragment implements HintsAdapter.onClickHints, View.OnClickListener, HintsPresenter.HintsView {
    FragmentHintsBinding binding;
    private Dialog dialog;
    private View view;
    HintsPresenter presenter;
    NavController navController;
    CharSequence search = "";
    HintsAdapter hintsAdapter;

    public HintsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_hints, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hints, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new HintsPresenter(this);

        presenter.GetHints(getContext());
      //  SetData();
        binding.textGo.setOnClickListener(this);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }

    @Override
    public void onClickHintsListener(String quesId) {
        Bundle bundle = new Bundle();
        bundle.putString("quesId",quesId);
        navController.navigate(R.id.action_hintsFragment_to_subHintsFragment,bundle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_go:
                navController.navigate(R.id.action_hintsFragment_to_add_Faq_Fragment);
                break;
        }
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
        if (message.equalsIgnoreCase("ok") && list.size() > 0 && list != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.recyclerView.setLayoutManager(layoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setHasFixedSize(true);
            hintsAdapter = new HintsAdapter(getContext(), list,this);
            binding.recyclerView.setAdapter(hintsAdapter);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);


            binding.searchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hintsAdapter.getFilter().filter(s);
                    search = s;
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSubHintsSuccess(List<SubHintsResponse> list, String message) {

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