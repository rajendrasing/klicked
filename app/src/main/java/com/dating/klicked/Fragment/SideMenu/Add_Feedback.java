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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.AddFeedbackPresenter;
import com.dating.klicked.databinding.FragmentAddFeedbackBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import okhttp3.ResponseBody;


public class Add_Feedback extends Fragment implements AddFeedbackPresenter.AddFeedbackView, View.OnClickListener {
    FragmentAddFeedbackBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    AddFeedbackPresenter presenter;

    public Add_Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add__feedback, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new AddFeedbackPresenter(this);

        binding.txtDone.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


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
    public void onAddFeedbackSuccess(ResponseBody responseBody, String message) {

        if (message.equalsIgnoreCase("ok")){
            Sneaker.with(getActivity())
                    .setTitle("Successfully Add You's Feedback ")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            getActivity().onBackPressed();
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
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txt_done:
                String title = binding.edTittle.getText().toString().trim();
                String descriptions = binding.description.getText().toString().trim();

                if (title.isEmpty()) {
                    Sneaker.with(getActivity())
                            .setTitle("please enter Title.....")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakWarning();
                } else if (descriptions.isEmpty()) {
                    Sneaker.with(getActivity())
                            .setTitle("please enter Descriptions.....")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakWarning();
                } else {

                    presenter.AddFeedbacks(getContext(),title,descriptions);
                }

                break;
        }
    }
}