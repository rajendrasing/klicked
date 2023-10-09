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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Fragment.Adapter.FeedbackAdapter;
import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.FAQPresenter;
import com.dating.klicked.databinding.FragmentFaqBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;


public class FaqFragment extends Fragment implements View.OnClickListener, FAQPresenter.FAQView {

    FragmentFaqBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    FAQPresenter presenter;


    public FaqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_faq, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        binding.addFaq.setOnClickListener(this);
        presenter = new FAQPresenter(this);

        presenter.GetFAQ(getContext());

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
            case R.id.add_faq:
                navController.navigate(R.id.action_faqFragment_to_add_Faq_Fragment);
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
    public void onAddFAQSuccess(ResponseBody responseBody, String message) {

    }

    @Override
    public void onGETFeedbackSuccess(List<FAQResponse> list, String message) {
        if (message.equalsIgnoreCase("ok") && list!=null && list.size()>0 ){

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
            binding.faqRecycler.setLayoutManager(layoutManager);
            binding.faqRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.faqRecycler.setHasFixedSize(true);
            binding.faqRecycler.setAdapter(new FeedbackAdapter(getContext(),list,false));
            binding.faqRecycler.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);
        }else {
            binding.faqRecycler.setVisibility(View.GONE);
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