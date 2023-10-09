package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.ViewedProfiledResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomViewProfileLayoutBinding;
import com.dating.klicked.databinding.CustomViewProfileLayoutBinding;
import com.dating.klicked.databinding.CustomViewProfileLayoutBinding;
import com.dating.klicked.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewedProfileAdapter extends RecyclerView.Adapter<ViewedProfileAdapter.ViewedProfile> {
    Context context;
    List<ViewedProfiledResponse> list;
    OnViewedProfileClicked onProfileClicked;

    public ViewedProfileAdapter(Context context, List<ViewedProfiledResponse> list, OnViewedProfileClicked onProfileClicked) {
        this.context = context;
        this.list = list;
        this.onProfileClicked = onProfileClicked;
    }

    @NonNull
    @Override
    public ViewedProfileAdapter.ViewedProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomViewProfileLayoutBinding binding = CustomViewProfileLayoutBinding.inflate(inflater, parent, false);

        return new ViewedProfileAdapter.ViewedProfile(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewedProfileAdapter.ViewedProfile holder, int position) {

        holder.binding.name.setText(list.get(0).getUsers().get(position).getId().getFirstName());
        if (list.get(0).getUsers().get(position).getId().getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

            holder.binding.imgProfile.setImageDrawable(context.getResources().getDrawable(R.mipmap.profile1));
        } else {
            Glide.with(context).load(PrefConf.IMAGE_URL + list.get(0).getUsers().get(position).getId().getProfileImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.binding.imgProfile);
        }

        String currentString = list.get(0).getUsers().get(position).getId().getDob();
        if (currentString!=null){
            String[] separated = currentString.split("/");
            String age = AppUtils.getAge(Integer.parseInt(separated[2]),Integer.parseInt(separated[1]),Integer.parseInt(separated[0]));
            if(list.get(0).getUsers().get(position).getId().getAddress().size()>0){
                holder.binding.location.setText(list.get(0).getUsers().get(position).getId().getAddress().get(0).getCity() + "," + list.get(0).getUsers().get(position).getId().getAddress().get(0).getState() + "," + list.get(0).getUsers().get(position).getId().getAddress().get(0).getCountry()+"   "+age+" yr");
            }else {

                holder.binding.location.setText(""+age+" yr");
            }
        }else {
            if(list.get(0).getUsers().get(position).getId().getAddress().size()>0){
                holder.binding.location.setText(list.get(0).getUsers().get(position).getId().getAddress().get(0).getCity() + "," + list.get(0).getUsers().get(position).getId().getAddress().get(0).getState() + "," + list.get(0).getUsers().get(position).getId().getAddress().get(0).getCountry());
            }else {

                holder.binding.location.setVisibility(View.GONE);
            }

        }



        if (list.get(0).getUsers().get(position).getId().getAudioDescription()!=null){
            holder.binding.imgAudio.setVisibility(View.VISIBLE);
        }else {
            holder.binding.imgAudio.setVisibility(View.GONE);
        }
        holder.binding.imgAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.imgAudio.setVisibility(View.GONE);
                holder.binding.imgPasue.setVisibility(View.VISIBLE);
                onProfileClicked.onPlayProfileMusicClickListener(list.get(0).getUsers().get(position).getId().getAudioDescription(),holder.binding.imgAudio,holder.binding.imgPasue);
            }
        });


        holder.binding.imgPasue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.imgAudio.setVisibility(View.VISIBLE);
                holder.binding.imgPasue.setVisibility(View.GONE);
                onProfileClicked.onPauseProfileMusicClickListener();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.get(0).getUsers().size();
    }

    public class ViewedProfile extends RecyclerView.ViewHolder {

        CustomViewProfileLayoutBinding binding;

        public ViewedProfile(@NonNull CustomViewProfileLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnViewedProfileClicked {
        void onPlayProfileMusicClickListener(String song, ImageView play, ImageView pause);
        void onPauseProfileMusicClickListener();
    }

}
