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

import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.FAQPresenter;
import com.dating.klicked.databinding.FragmentAddFaqBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.List;

import okhttp3.ResponseBody;


public class Add_Faq_Fragment extends Fragment implements View.OnClickListener, FAQPresenter.FAQView {

    FragmentAddFaqBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    private AppUtils psDialogMsg;

    FAQPresenter presenter;

    public Add_Faq_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add__faq, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        binding.txtDone.setOnClickListener(this);

        presenter = new FAQPresenter(this);

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

            case R.id.txt_done:
                String Question = binding.description.getText().toString().trim();
                if (Question.isEmpty()){
                    Sneaker.with(getActivity())
                            .setTitle("Please Enter Your Ask Question ")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakError();
                }else {
                    presenter.AddFAQ(getContext(),Question);
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
    public void onAddFAQSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            String des = getContext().getString(R.string.txt_faq_dia);
            psDialogMsg = new AppUtils(getActivity(), false);
            psDialogMsg.showFAQLogoutDialog("Successful", des, "FAQ", getActivity().getDrawable(R.drawable.ic_faq_pop_image));
            psDialogMsg.show();
            psDialogMsg.txt_ready.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    psDialogMsg.cancel();
                    getActivity().onBackPressed();
                }
            });
        }

    }

    @Override
    public void onGETFeedbackSuccess(List<FAQResponse> list, String message) {

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