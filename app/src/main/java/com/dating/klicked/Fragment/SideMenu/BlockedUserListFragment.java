package com.dating.klicked.Fragment.SideMenu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Fragment.Adapter.BlockedUserListAdapter;
import com.dating.klicked.Fragment.Adapter.PendingAdapter;
import com.dating.klicked.Model.ResponseRepo.BlockUserListResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.BlockedUserPresenter;
import com.dating.klicked.ViewPresenter.PendingUserPresent;
import com.dating.klicked.databinding.FragmentBlockedUserListBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import okhttp3.ResponseBody;


public class BlockedUserListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BlockedUserPresenter.BlockedUserView, BlockedUserListAdapter.BlockedUserListViewClicked {
    private Context context;
    private Dialog dialog;
    private View view;
    FragmentBlockedUserListBinding binding;
    BlockedUserPresenter presenter;


    public BlockedUserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //    return inflater.inflate(R.layout.fragment_blocked_user_list, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blocked_user_list, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new BlockedUserPresenter(this);

        presenter.getAllBlockedUserList(getContext());

        // setData();


        binding.SwipeRefresh.setOnRefreshListener(this);
        binding.SwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.global__primary));

        return binding.getRoot();

    }

    @Override
    public void onRefresh() {
        presenter.getAllBlockedUserList(getContext());
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
        if (message.equalsIgnoreCase("User not found")) {
            binding.otherRecyclerView.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
        } else {
            Sneaker.with(getActivity())
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        }
    }

    @Override
    public void onGetBlockedUserListSuccess(BlockUserListResponse response, String message) {
        if (message.equalsIgnoreCase("ok") && response.getBlockedUser() != null && response.getBlockedUser().size() > 0) {
            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.otherRecyclerView.setHasFixedSize(true);
            binding.otherRecyclerView.setLayoutManager(layoutManager1);
            binding.otherRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.otherRecyclerView.setAdapter(new BlockedUserListAdapter(getContext(), response, this));
            binding.otherRecyclerView.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.GONE);
        } else {
            binding.otherRecyclerView.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onUnBlockedUserSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("This user has been successfully Unblocked")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            presenter.getAllBlockedUserList(getContext());
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
    public void onBlockedUserListViewClickedListener(String userId) {
        presenter.UnBlockUser(getContext(), userId);
    }
}