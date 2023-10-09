package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.ResponseRepo.RewardHistoryResponse;
import com.dating.klicked.databinding.CustomRewardsLayoutBinding;
import com.dating.klicked.utils.AppUtils;

import java.util.ArrayList;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.RewardsView> {

    Context context;
    RewardHistoryResponse list;
    String userId,partten="dd-MMM-YYYY";

    public RewardsAdapter(Context context, RewardHistoryResponse list, String userId) {
        this.context = context;
        this.list = list;
        this.userId = userId;
    }

    @NonNull
    @Override
    public RewardsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomRewardsLayoutBinding binding = CustomRewardsLayoutBinding.inflate(inflater, parent, false);

        return new RewardsView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsView holder, int position) {
        holder.binding.textUserName.setText(list.getReward().get(position).getShareId().getFirstName());
        holder.binding.textDate.setText(AppUtils.getDateTime1(list.getReward().get(position).getCreatedAt(),partten));
        if (list.getReward().get(position).getUserId().getId().equalsIgnoreCase(userId)){
            holder.binding.textPoint.setText(list.getReward().get(position).getUserpoint());
        }else {
            holder.binding.textPoint.setText(list.getReward().get(position).getSharepoint());

        }
    }

    @Override
    public int getItemCount() {
        return list.getReward().size();
    }


    public class RewardsView extends RecyclerView.ViewHolder {
        CustomRewardsLayoutBinding binding;

        public RewardsView(@NonNull CustomRewardsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
