package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.FavouriteResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomHomeLayoutBinding;
import com.dating.klicked.databinding.CustomRecentAddedLayputBinding;
import com.dating.klicked.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class RecentFavAdapter extends RecyclerView.Adapter<RecentFavAdapter.RecentFavViewHolder> {
    Context context;
    List<FavouriteResponse> list;
    FavPersonAdapter.FavPersonClicked favPersonClicked;
    Boolean aBoolean = false;

    public RecentFavAdapter(Context context, List<FavouriteResponse> list, FavPersonAdapter.FavPersonClicked favPersonClicked) {
        this.context = context;
        this.list = list;
        this.favPersonClicked = favPersonClicked;

    }

    @NonNull
    @Override
    public RecentFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomRecentAddedLayputBinding binding = CustomRecentAddedLayputBinding.inflate(inflater, parent, false);

        return new RecentFavViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentFavViewHolder holder, int position) {
        holder.binding.name.setText(list.get(position).getUserId().getFirstName());
        if (list.get(position).getUserId().getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

            holder.binding.imgProfile.setImageDrawable(context.getResources().getDrawable(R.mipmap.profile1));
        } else {
            Glide.with(context).load(PrefConf.IMAGE_URL + list.get(position).getUserId().getProfileImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.binding.imgProfile);
        }

        String currentString = list.get(position).getUserId().getDob();
        if (currentString != null) {
            String[] separated = currentString.split("/");
            String age = AppUtils.getAge(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));
            if (list.get(position).getUserId().getAddress().size() > 0 && list.get(position).getUserId().getAddress() != null) {
                holder.binding.location.setText(list.get(position).getUserId().getAddress().get(0).getCity() + "," + list.get(position).getUserId().getAddress().get(0).getState() + "," + list.get(position).getUserId().getAddress().get(0).getCountry() + "   " + age);
            } else {
                holder.binding.location.setText(age + " yr");
            }
        } else {
            if (list.get(position).getUserId().getAddress().size() > 0 && list.get(position).getUserId().getAddress() != null) {
                holder.binding.location.setVisibility(View.VISIBLE);

                holder.binding.location.setText(list.get(position).getUserId().getAddress().get(0).getCity() + "," + list.get(position).getUserId().getAddress().get(0).getState() + "," + list.get(position).getUserId().getAddress().get(0).getCountry());

            } else {
                holder.binding.location.setVisibility(View.INVISIBLE);
            }
        }

        if (list.get(position).getUserId().getAudioDescription() != null) {
            holder.binding.audio.setVisibility(View.VISIBLE);
        } else {
            holder.binding.audio.setVisibility(View.GONE);
        }
        holder.binding.imgAddfri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favPersonClicked.onSendRequestPersonClicked(holder.binding.imgAddfri, list.get(position).getUserId().getId());
            }
        });

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favPersonClicked.onDeleteFavPersonClicked(list.get(position).getUserId().getId());
            }
        });

        holder.binding.audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aBoolean == false) {
                    aBoolean = true;
                    favPersonClicked.onAudioPlayClicked(PrefConf.IMAGE_URL + list.get(position).getUserId().getAudioDescription(), view, aBoolean);

                } else {
                    aBoolean = false;
                    favPersonClicked.onAudioPlayClicked(PrefConf.IMAGE_URL + list.get(position).getUserId().getAudioDescription(), view, aBoolean);

                }

            }
        });
        holder.binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favPersonClicked.onShareProfileUser(list.get(position).getUserId().getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecentFavViewHolder extends RecyclerView.ViewHolder {
        CustomRecentAddedLayputBinding binding;

        public RecentFavViewHolder(@NonNull CustomRecentAddedLayputBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
