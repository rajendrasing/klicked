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
import com.dating.klicked.Model.ResponseRepo.PendingResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomPendingLayoutBinding;
import com.dating.klicked.utils.AppUtils;

import java.util.ArrayList;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingView> {
    Context context;
    PendingResponse pendingResponse;
    onPendingClicked pendingClicked;

    public PendingAdapter(Context context, PendingResponse pendingResponse, onPendingClicked pendingClicked) {
        this.context = context;
        this.pendingResponse = pendingResponse;
        this.pendingClicked = pendingClicked;
    }

    @NonNull
    @Override
    public PendingView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomPendingLayoutBinding binding = CustomPendingLayoutBinding.inflate(inflater, parent, false);
        return new PendingView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingView holder, int position) {

        // holder.binding.name.setText(list.get(position));

        holder.binding.name.setText(pendingResponse.getResult().get(position).getReceiver().getFirstName());

        if (pendingResponse.getResult().get(position).getReceiver().getKycVerified() != null) {
            if (pendingResponse.getResult().get(position).getReceiver().getKycVerified().equalsIgnoreCase("Done") || pendingResponse.getResult().get(position).getReceiver().getKycVerified().equalsIgnoreCase("verified")) {
                holder.binding.name.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_noun_verified), null);
            } else {
                holder.binding.name.setCompoundDrawables(null, null, null, null);

            }
        } else {
            holder.binding.name.setCompoundDrawables(null, null, null, null);

        }

        if (pendingResponse.getResult().get(position).getReceiver().getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

            holder.binding.imgProfile.setImageDrawable(context.getResources().getDrawable(R.mipmap.profile1));
        } else {
            Glide.with(context).load(PrefConf.IMAGE_URL + pendingResponse.getResult().get(position).getReceiver().getProfileImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.binding.imgProfile);
        }


        String currentString = pendingResponse.getResult().get(position).getReceiver().getDob();

        if (currentString != null) {
            String[] separated = currentString.split("/");
            String age = AppUtils.getAge(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));

            if (pendingResponse.getResult().get(position).getReceiver().getAddress().size() > 0 && pendingResponse.getResult().get(position).getReceiver().getAddress() != null) {
                holder.binding.location.setVisibility(View.VISIBLE);
                holder.binding.location.setText(pendingResponse.getResult().get(position).getReceiver().getAddress().get(0).getCity() + "," + pendingResponse.getResult().get(position).getReceiver().getAddress().get(0).getState() + "," + pendingResponse.getResult().get(position).getReceiver().getAddress().get(0).getCountry() + "   " + age + " yr");
            } else {
                holder.binding.location.setVisibility(View.INVISIBLE);
            }
        } else {
            if (pendingResponse.getResult().get(position).getReceiver().getAddress().size() > 0 && pendingResponse.getResult().get(position).getReceiver().getAddress() != null) {
                holder.binding.location.setVisibility(View.VISIBLE);

                holder.binding.location.setText(pendingResponse.getResult().get(position).getReceiver().getAddress().get(0).getCity() + "," + pendingResponse.getResult().get(position).getReceiver().getAddress().get(0).getState() + "," + pendingResponse.getResult().get(position).getReceiver().getAddress().get(0).getCountry());

            } else {
                holder.binding.location.setVisibility(View.INVISIBLE);
            }
        }


        if (pendingResponse.getResult().get(position).getReceiver().getAudioDescription() != null) {
            holder.binding.relative1.setVisibility(View.VISIBLE);
        } else {
            holder.binding.relative1.setVisibility(View.GONE);
        }


        holder.binding.txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendingClicked.onCancelClickedListener(pendingResponse.getResult().get(position).getId());
            }
        });

        holder.binding.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.imgPause.setVisibility(View.VISIBLE);
                holder.binding.imgPlay.setVisibility(View.GONE);
                pendingClicked.onAudioPlayClicked(holder.binding.imgPlay, holder.binding.imgPause, PrefConf.IMAGE_URL + pendingResponse.getResult().get(position).getReceiver().getAudioDescription());
            }
        });

        holder.binding.imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.imgPause.setVisibility(View.GONE);
                holder.binding.imgPlay.setVisibility(View.VISIBLE);
                pendingClicked.onAudioPauseClicked();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendingClicked.onPendingProfileClicked(pendingResponse.getResult().get(position).getReceiver().getId(), pendingResponse.getResult().get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return pendingResponse.getResult().size();
    }


    public class PendingView extends RecyclerView.ViewHolder {
        CustomPendingLayoutBinding binding;

        public PendingView(@NonNull CustomPendingLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onPendingClicked {

        void onPendingProfileClicked(String userId, String documentId);


        void onCancelClickedListener(String documentId);

        void onAudioPlayClicked(ImageView play, ImageView pause, String Song);


        void onAudioPauseClicked();
    }
}
