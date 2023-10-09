package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.KlickedResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomKlickedLayoutBinding;
import com.dating.klicked.utils.AppUtils;

public class KlickedAdapter extends RecyclerView.Adapter<KlickedAdapter.KlickedViewHolder> {
    Context context;
    KlickedResponse klickedResponse;
    KlickedClicked klickedClicked;

    public KlickedAdapter(Context context, KlickedResponse klickedResponse, KlickedClicked klickedClicked) {
        this.context = context;
        this.klickedResponse = klickedResponse;
        this.klickedClicked = klickedClicked;
    }

    @NonNull
    @Override
    public KlickedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomKlickedLayoutBinding binding = CustomKlickedLayoutBinding.inflate(inflater, parent, false);


        return new KlickedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull KlickedViewHolder holder, int position) {

        if (klickedResponse.getUsers().get(position).getUserId() != null) {
            holder.binding.name.setText(klickedResponse.getUsers().get(position).getUserId().getFirstName());
            if (klickedResponse.getUsers().get(position).getUserId().getKycVerified().equalsIgnoreCase("Done") || klickedResponse.getUsers().get(position).getUserId().getKycVerified().equalsIgnoreCase("verified")) {
                holder.binding.name.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_noun_verified), null);
            } else {
                holder.binding.name.setCompoundDrawables(null, null, null, null);

            }
            if (klickedResponse.getUsers().get(position).getUserId().getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

                holder.binding.imgProfile.setImageDrawable(context.getResources().getDrawable(R.mipmap.profile1));
            } else {
                Glide.with(context).load(PrefConf.IMAGE_URL + klickedResponse.getUsers().get(position).getUserId().getProfileImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.binding.imgProfile);
            }
            String currentString = klickedResponse.getUsers().get(position).getUserId().getDob();
            if (currentString != null) {
                String[] separated = currentString.split("/");
                String age = AppUtils.getAge(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));

                if (klickedResponse.getUsers().get(position).getUserId().getAddress().size() > 0 && klickedResponse.getUsers().get(position).getUserId().getAddress() != null) {
                    holder.binding.location.setVisibility(View.VISIBLE);
                    holder.binding.location.setText(klickedResponse.getUsers().get(position).getUserId().getAddress().get(0).getCity() + "," + klickedResponse.getUsers().get(position).getUserId().getAddress().get(0).getState() + "," + klickedResponse.getUsers().get(position).getUserId().getAddress().get(0).getCountry() + "   " + age + " yr");
                } else {
                    holder.binding.location.setVisibility(View.INVISIBLE);
                }
            } else {
                if (klickedResponse.getUsers().get(position).getUserId().getAddress().size() > 0 && klickedResponse.getUsers().get(position).getUserId().getAddress() != null) {
                    holder.binding.location.setVisibility(View.VISIBLE);

                    holder.binding.location.setText(klickedResponse.getUsers().get(position).getUserId().getAddress().get(0).getCity() + "," + klickedResponse.getUsers().get(position).getUserId().getAddress().get(0).getState() + "," + klickedResponse.getUsers().get(position).getUserId().getAddress().get(0).getCountry());

                } else {
                    holder.binding.location.setVisibility(View.INVISIBLE);
                }
            }

            if (klickedResponse.getUsers().get(position).getUserId().getAudioDescription() != null) {
                holder.binding.relative1.setVisibility(View.VISIBLE);
            } else {
                holder.binding.relative1.setVisibility(View.GONE);
            }

            holder.binding.imgPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.imgPause.setVisibility(View.VISIBLE);
                    holder.binding.imgPlay.setVisibility(View.GONE);
                    klickedClicked.onAudioPlayClicked(holder.binding.imgPlay, holder.binding.imgPause, PrefConf.IMAGE_URL + klickedResponse.getUsers().get(position).getUserId().getAudioDescription());
                }
            });

            holder.binding.imgPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.imgPause.setVisibility(View.GONE);
                    holder.binding.imgPlay.setVisibility(View.VISIBLE);
                    klickedClicked.onAudioPauseClicked();
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    klickedClicked.onKlickedViewProfileClicked(klickedResponse.getUsers().get(position).getUserId().getId());
                }
            });

            holder.binding.imgDelete.setOnClickListener(view -> {
                klickedClicked.onUserDeleteProfileClicked(klickedResponse.getUsers().get(position).getUserId().getId());

            });

        }


    }


    @Override
    public int getItemCount() {

        return klickedResponse.getUsers().size();
    }

    public class KlickedViewHolder extends RecyclerView.ViewHolder {
        CustomKlickedLayoutBinding binding;

        public KlickedViewHolder(@NonNull CustomKlickedLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }


    }

    public interface KlickedClicked {

        void onKlickedViewProfileClicked(String userId);

        void onAudioPlayClicked(ImageView play, ImageView pause, String Song);

        void onAudioPauseClicked();

        void onUserDeleteProfileClicked(String userId);


    }
}
