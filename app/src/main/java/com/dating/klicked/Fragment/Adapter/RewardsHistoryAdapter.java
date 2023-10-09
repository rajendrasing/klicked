package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dating.klicked.Model.ResponseRepo.RedeemHistoryResponse;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomRewardRedeemLayoutBinding;
import com.dating.klicked.databinding.CutomRedeemHistoryLayoutBinding;
import com.dating.klicked.utils.AppUtils;

public class RewardsHistoryAdapter extends RecyclerView.Adapter<RewardsHistoryAdapter.RewardsRedeemView> {
    Context context;
    RedeemHistoryResponse redeemHistoryResponse;
    RewardsRedeemListener listener;

    public RewardsHistoryAdapter(Context context, RedeemHistoryResponse redeemHistoryResponse, RewardsRedeemListener listener) {
        this.context = context;
        this.redeemHistoryResponse = redeemHistoryResponse;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RewardsRedeemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CutomRedeemHistoryLayoutBinding binding = CutomRedeemHistoryLayoutBinding.inflate(inflater, parent, false);

        return new RewardsRedeemView(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull RewardsRedeemView holder, int position) {
        if (redeemHistoryResponse.getResult().get(position).getGiftId()!=null){
            Glide.with(context).load(PrefConf.IMAGE_URL + redeemHistoryResponse.getResult().get(position).getGiftId().getImages().get(0))
                    .thumbnail(0.05f)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.proImg);
            holder.binding.name.setText(redeemHistoryResponse.getResult().get(position).getGiftId().getName());

        }else {
            holder.binding.proImg.setVisibility(View.GONE);
            holder.binding.name.setText(" ");

        }

     //   holder.binding.name.setText(redeemHistoryResponse.getResult().get(position).getGiftId().getName());
        holder.binding.price.setText(redeemHistoryResponse.getResult().get(position).getAmount()+"PT");
        holder.binding.txtDate.setText(AppUtils.getDateTime1(redeemHistoryResponse.getResult().get(position).getCreatedAt(),"dd-MM-YYYY"));
        holder.binding.txtStatus.setText(redeemHistoryResponse.getResult().get(position).getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRewardsRedeemListener(redeemHistoryResponse,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return redeemHistoryResponse.getResult().size();
    }

    public class RewardsRedeemView extends RecyclerView.ViewHolder {
        CutomRedeemHistoryLayoutBinding binding;

        public RewardsRedeemView(@NonNull CutomRedeemHistoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface RewardsRedeemListener {
        void onRewardsRedeemListener(RedeemHistoryResponse response, int position);
    }
}
