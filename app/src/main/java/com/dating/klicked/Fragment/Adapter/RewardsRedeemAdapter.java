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
import com.dating.klicked.Model.ResponseRepo.GiftResponse;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomRewardRedeemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class RewardsRedeemAdapter extends RecyclerView.Adapter<RewardsRedeemAdapter.RewardsRedeemView> {
    Context context;
    List<GiftResponse> list;
    RewardsClickedListener listener;

    public RewardsRedeemAdapter(Context context, List<GiftResponse> list, RewardsClickedListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RewardsRedeemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomRewardRedeemLayoutBinding binding = CustomRewardRedeemLayoutBinding.inflate(inflater, parent, false);

        return new RewardsRedeemView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsRedeemView holder, int position) {
        if (list.get(position).getImages().size()>0){

            Glide.with(context).load(PrefConf.IMAGE_URL + list.get(position).getImages().get(0))
                    .thumbnail(0.05f)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    // .placeholder(R.drawable.ic_placeholder)
                    .into(holder.binding.proImg);

        }else {
            holder.binding.proImg.setVisibility(View.INVISIBLE);
        }


        holder.binding.textName.setText(list.get(position).getName());

        holder.binding.textPrice.setText(list.get(position).getRewardCharges() + "PT");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRewardsClickedListener(list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class RewardsRedeemView extends RecyclerView.ViewHolder {
        CustomRewardRedeemLayoutBinding binding;

        public RewardsRedeemView(@NonNull CustomRewardRedeemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface RewardsClickedListener {
        void onRewardsClickedListener(String productId);
    }
}
