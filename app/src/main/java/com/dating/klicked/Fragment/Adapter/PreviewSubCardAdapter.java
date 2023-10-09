package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.CardSubCardResponse;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomPreviewCardLayoutBinding;
import com.dating.klicked.databinding.CustomSubpreviewCardLayoutBinding;

public class PreviewSubCardAdapter extends RecyclerView.Adapter<PreviewSubCardAdapter.PreviewSubCardView> {

    Context context;
    CardSubCardResponse cardSubCardResponse;
    int positions;

    public PreviewSubCardAdapter(Context context, CardSubCardResponse cardSubCardResponse, int positions) {
        this.context = context;
        this.cardSubCardResponse = cardSubCardResponse;
        this.positions = positions;
    }

    @NonNull
    @Override
    public PreviewSubCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomSubpreviewCardLayoutBinding binding = CustomSubpreviewCardLayoutBinding.inflate(inflater, parent, false);

        return new PreviewSubCardView(binding);
        // return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewSubCardView holder, int position) {

        if (position == 3) {
            holder.binding.card.setVisibility(View.VISIBLE);
            holder.binding.setimage.setVisibility(View.GONE);
            holder.binding.txtsize.setText("+" + (cardSubCardResponse.getResult().get(positions).getSubcard().size() - 3));
        } else {
            holder.binding.card.setVisibility(View.GONE);
            holder.binding.setimage.setVisibility(View.VISIBLE);
            Glide.with(context).load(PrefConf.IMAGE_URL + cardSubCardResponse.getResult().get(positions).getSubcard().get(position).getBackgroundImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.binding.setimage);

        }

    }

    @Override
    public int getItemCount() {
        int size = cardSubCardResponse.getResult().get(positions).getSubcard().size();

        if (size >= 4) {
            size = 4;
        } else {
            size = cardSubCardResponse.getResult().get(positions).getSubcard().size();
        }
        return size;
    }


    public class PreviewSubCardView extends RecyclerView.ViewHolder {
        CustomSubpreviewCardLayoutBinding binding;

        public PreviewSubCardView(@NonNull CustomSubpreviewCardLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
