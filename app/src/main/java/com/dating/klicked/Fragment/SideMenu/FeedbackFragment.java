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
import com.dating.klicked.Model.ResponseRepo.FeedbackResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.FeedbacksPresenter;
import com.dating.klicked.databinding.FragmentFeedbackBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;


public class FeedbackFragment extends Fragment implements View.OnClickListener, FeedbacksPresenter.FeedbacksView {
    FragmentFeedbackBinding binding;
    private Dialog dialog;
    private View view;
    NavController navController;
    FeedbacksPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_feedback, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        binding.addFeed.setOnClickListener(this);

        presenter = new FeedbacksPresenter(this);
        presenter.getAllFeedbackData(getContext());


   //     SetData();
        return binding.getRoot();

    }

   /* private void SetData() {
        ArrayList<String > list = new ArrayList<String>();
        list.add(getResources().getString(R.string.txt_dummy));
        list.add(getResources().getString(R.string.txt_dummy));
        list.add(getResources().getString(R.string.txt_dummy));
        list.add(getResources().getString(R.string.txt_dummy));
        list.add(getResources().getString(R.string.txt_dummy));


    }
*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add_feed:
                navController.navigate(R.id.action_feedbackFragment_to_add_feedback);
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
    public void onGetAllFeedbacksSuccess(FeedbackResponse response, String message) {
        if (message.equalsIgnoreCase("ok") && response.getResult().size()>0 && response.getResult()!=null){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
            binding.feedbackRecycler.setLayoutManager(layoutManager);
            binding.feedbackRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.feedbackRecycler.setHasFixedSize(true);
            binding.feedbackRecycler.setAdapter(new FeedbackAdapter(getContext(),response,true));
            binding.feedbackRecycler.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);
        }else {
            binding.feedbackRecycler.setVisibility(View.GONE);
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