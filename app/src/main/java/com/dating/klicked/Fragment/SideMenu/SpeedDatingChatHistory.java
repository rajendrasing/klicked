package com.dating.klicked.Fragment.SideMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Activity.SpeedDatingChating;
import com.dating.klicked.Fragment.Adapter.ChatAdapter;
import com.dating.klicked.Fragment.Adapter.SpeedChatHistoryAdapter;
import com.dating.klicked.Model.RequestRepo.SpeedChatDeleteBody;
import com.dating.klicked.Model.ResponseRepo.ChatResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.ChatPresenter;
import com.dating.klicked.ViewPresenter.SpeedChatHistoryPresenter;
import com.dating.klicked.databinding.FragmentChatBinding;
import com.dating.klicked.databinding.FragmentSpeedDatingChatHistoryBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;


public class SpeedDatingChatHistory extends Fragment implements SpeedChatHistoryPresenter.SpeedChatHistoryView, SpeedChatHistoryAdapter.SpeedChatHistoryViewClicked {
    FragmentSpeedDatingChatHistoryBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    private SpeedChatHistoryPresenter presenter;
    User_Data userData;

    public SpeedDatingChatHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_speed_dating_chat_history, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_speed_dating_chat_history, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        presenter = new SpeedChatHistoryPresenter(this);

        presenter.getOldSpeedChat(getContext());

        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();

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
    public void onGetSpeedChatHistorySuccess(ChatResponse response, String message) {
        List<String >idList = new ArrayList<String>();
        idList.clear();
        if (message.equalsIgnoreCase("ok") && response.getResult().size() > 0 && response.getResult() != null) {
            for (int i = 0; i < response.getResult().size(); i++){
               String data = response.getResult().get(i).getCreatedAt();
               if (AppUtils.getCompareDate(data)==true){
                   idList.add(response.getResult().get(i).getId());
               }
            }

            if (idList.size()==0){
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                binding.recyclerView.setHasFixedSize(true);
                binding.recyclerView.setLayoutManager(layoutManager);
                binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                binding.recyclerView.setAdapter(new SpeedChatHistoryAdapter(getContext(), response, this, userData.getId()));
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.imdNoDta.setVisibility(View.GONE);
                binding.unshowText.setVisibility(View.GONE);

            }else {
                SpeedChatDeleteBody speedChatDeleteBody = new SpeedChatDeleteBody(idList);
                presenter.DeleteOldSpeedChat(getContext(),speedChatDeleteBody);
            }

        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDeleteSpeedChatHistorySuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")){
            presenter.getOldSpeedChat(getContext());
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
    public void onSpeedChatHistoryViewClickedListener(String ChatId, String Name, String userId) {
        startActivity(new Intent(getActivity(), SpeedDatingChating.class)
                .putExtra("userId", userId)
                .putExtra("chatId", ChatId)
                .putExtra("name", Name));
    }
}