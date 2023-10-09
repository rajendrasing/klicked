package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dating.klicked.Authentication.Adapter.CardAdapter;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomCardLayoutBinding;
import com.dating.klicked.databinding.CustomCardhomeLayoutBinding;

import java.util.List;

public class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.HomeCardView> {
    List<CardResponse> list;
    Context context;

    public HomeCardAdapter(List<CardResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomCardhomeLayoutBinding binding = CustomCardhomeLayoutBinding.inflate(inflater, parent, false);

        return new HomeCardView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCardView holder, int position) {
        Glide.with(context).load(PrefConf.IMAGE_URL + list.get(position).getBackgroundImg()).into(holder.binding.backImg);
        Glide.with(context).load(PrefConf.IMAGE_URL + list.get(position).getIcon()).into(holder.binding.iconImg);
        holder.binding.text.setText(list.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeCardView extends RecyclerView.ViewHolder {

        CustomCardhomeLayoutBinding binding;

        public HomeCardView(@NonNull CustomCardhomeLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }


    }
}
