package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.databinding.CustomRewardfaqRecyclerLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class RewardsFaqAdapter  extends RecyclerView.Adapter<RewardsFaqAdapter.RewardsFaqView>{
    Context context;
    List<FAQResponse> list;

    public RewardsFaqAdapter(Context context, List<FAQResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RewardsFaqView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  =  LayoutInflater.from(parent.getContext());
        CustomRewardfaqRecyclerLayoutBinding  binding = CustomRewardfaqRecyclerLayoutBinding.inflate(inflater,parent,false);

        return new RewardsFaqView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsFaqView holder, int position) {

        holder.binding.textQ.setText(list.get(position).getQuestion());
        holder.binding.textA.setText(list.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RewardsFaqView extends RecyclerView.ViewHolder{
        CustomRewardfaqRecyclerLayoutBinding binding;
        public RewardsFaqView(@NonNull CustomRewardfaqRecyclerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
