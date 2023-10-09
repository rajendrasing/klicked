package com.dating.klicked.Fragment.BottomMenu;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Fragment.Adapter.ChatAdapter;
import com.dating.klicked.Model.ResponseRepo.ChatResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.ChatPresenter;
import com.dating.klicked.databinding.FragmentChatBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;


public class Chat_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ChatPresenter.ChatView, ChatAdapter.ChatViewClicked {
    FragmentChatBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    private ChatPresenter presenter;
    CharSequence search = "";
    User_Data userData;

    public Chat_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        presenter = new ChatPresenter(this);

        presenter.getHomeChat(getContext());

        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();

        binding.SwipeRefresh.setOnRefreshListener(this);
        binding.SwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.global__primary));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }


    @Override
    public void onRefresh() {
        presenter.getHomeChat(getContext());


        binding.SwipeRefresh.setRefreshing(false);

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
    public void onGetChatSuccess(ChatResponse response, String message) {
        if (message.equalsIgnoreCase("ok") && response.getResult().size() > 0 && response.getResult() != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.chatRecycler.setHasFixedSize(true);
            binding.chatRecycler.setLayoutManager(layoutManager);
            binding.chatRecycler.setItemAnimator(new DefaultItemAnimator());
            ChatAdapter chatAdapter = new ChatAdapter(getContext(), response, this, userData.getId());
            binding.chatRecycler.setAdapter(chatAdapter);
            binding.chatRecycler.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);

            binding.edSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    chatAdapter.getFilter().filter(s);
                    search = s;
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {
            binding.chatRecycler.setVisibility(View.GONE);
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
    public void onChatViewClickedListener(String ChatId, String Name, String profile, String userId) {

        Bundle bundle = new Bundle();
        bundle.putString("ChatId", ChatId);
        bundle.putString("Name", Name);
        bundle.putString("profile", profile);
        bundle.putString("userId", userId);

        navController.navigate(R.id.action_chat_Fragment_to_userChat_Fragment, bundle);

    }
}