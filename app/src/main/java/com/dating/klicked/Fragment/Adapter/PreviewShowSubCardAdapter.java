package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dating.klicked.Model.ResponseRepo.CardSubCardResponse;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomSubcardLayoutBinding;

import java.util.List;

public class PreviewShowSubCardAdapter extends RecyclerView.Adapter<PreviewShowSubCardAdapter.PreviewShowSubCardView> {
    Context context;
    List<CardSubCardResponse.Result.Subcard> subcard;

    public PreviewShowSubCardAdapter(Context context, List<CardSubCardResponse.Result.Subcard> subcard) {
        this.context = context;
        this.subcard = subcard;
    }

    @NonNull
    @Override
    public PreviewShowSubCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomSubcardLayoutBinding binding = CustomSubcardLayoutBinding.inflate(inflater, parent, false);

        return new PreviewShowSubCardView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewShowSubCardView holder, int position) {
        Glide.with(context).load(PrefConf.IMAGE_URL + subcard.get(position).getBackgroundImg()).into(holder.binding.backImg);
        Glide.with(context).load(PrefConf.IMAGE_URL + subcard.get(position).getIcon()).into(holder.binding.iconImg);
        holder.binding.text.setText(subcard.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return subcard.size();
    }

    public class PreviewShowSubCardView extends RecyclerView.ViewHolder {

        CustomSubcardLayoutBinding binding;

        public PreviewShowSubCardView(@NonNull CustomSubcardLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }


    }

}
