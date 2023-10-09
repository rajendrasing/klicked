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
import com.dating.klicked.databinding.CustomRequestLayoutBinding;
import com.dating.klicked.utils.AppUtils;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestView> {
    Context context;
    PendingResponse requestResponse;
    onRequestClicked onRequestClicked;


    public RequestAdapter(Context context, PendingResponse requestResponse, onRequestClicked onRequestClicked) {
        this.context = context;
        this.requestResponse = requestResponse;
        this.onRequestClicked = onRequestClicked;
    }

    @NonNull
    @Override
    public RequestView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomRequestLayoutBinding binding = CustomRequestLayoutBinding.inflate(inflater, parent, false);

        return new RequestView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestView holder, int position) {
        if (requestResponse.getResult().get(position).getSender().getFirstName() != null) {
            holder.binding.name.setText(requestResponse.getResult().get(position).getSender().getFirstName());

        } else {
            holder.binding.name.setText(" ");

        }
        if (requestResponse.getResult().get(position).getSender().getKycVerified().equalsIgnoreCase("Done") || requestResponse.getResult().get(position).getSender().getKycVerified().equalsIgnoreCase("verified")) {
            holder.binding.name.setCompoundDrawables(null,null,context.getResources().getDrawable(R.drawable.ic_noun_verified),null);
        } else {
            holder.binding.name.setCompoundDrawables(null,null,null,null);

        }
        if (requestResponse.getResult().get(position).getSender().getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

            holder.binding.imgProfile.setImageDrawable(context.getResources().getDrawable(R.mipmap.profile1));
        } else {
            Glide.with(context).load(PrefConf.IMAGE_URL + requestResponse.getResult().get(position).getSender().getProfileImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.binding.imgProfile);
        }
        String currentString = requestResponse.getResult().get(position).getSender().getDob();
        if (currentString != null) {
            String[] separated = currentString.split("/");
            String age = AppUtils.getAge(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));

            if (requestResponse.getResult().get(position).getSender().getAddress().size() > 0 && requestResponse.getResult().get(position).getSender().getAddress() != null) {
                holder.binding.location.setVisibility(View.VISIBLE);
                holder.binding.location.setText(requestResponse.getResult().get(position).getSender().getAddress().get(0).getCity() + "," + requestResponse.getResult().get(position).getSender().getAddress().get(0).getState() + "," + requestResponse.getResult().get(position).getSender().getAddress().get(0).getCountry() + "   " + age + " yr");
            } else {
                holder.binding.location.setVisibility(View.INVISIBLE);
            }
        } else {
            if (requestResponse.getResult().get(position).getSender().getAddress().size() > 0 && requestResponse.getResult().get(position).getSender().getAddress() != null) {
                holder.binding.location.setVisibility(View.VISIBLE);

                holder.binding.location.setText(requestResponse.getResult().get(position).getSender().getAddress().get(0).getCity() + "," + requestResponse.getResult().get(position).getSender().getAddress().get(0).getState() + "," + requestResponse.getResult().get(position).getSender().getAddress().get(0).getCountry());

            } else {
                holder.binding.location.setVisibility(View.INVISIBLE);
            }
        }
        if (requestResponse.getResult().get(position).getSender().getAudioDescription() != null) {
            holder.binding.relative1.setVisibility(View.VISIBLE);
        } else {
            holder.binding.relative1.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestClicked.onRequestProfileClicked(requestResponse.getResult().get(position).getSender().getId(), requestResponse.getResult().get(position).getId());
            }
        });

        holder.binding.txtAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestClicked.onAcceptClickedListener(requestResponse.getResult().get(position).getId());
            }
        });

        holder.binding.txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestClicked.onCancelClickedListener(requestResponse.getResult().get(position).getId());
            }
        });

        holder.binding.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.imgPause.setVisibility(View.VISIBLE);
                holder.binding.imgPlay.setVisibility(View.GONE);

                onRequestClicked.onAudioPlayClicked(holder.binding.imgPlay, holder.binding.imgPause, PrefConf.IMAGE_URL + requestResponse.getResult().get(position).getReceiver().getAudioDescription());
            }
        });

        holder.binding.imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.imgPause.setVisibility(View.GONE);
                holder.binding.imgPlay.setVisibility(View.VISIBLE);

                onRequestClicked.onAudioPauseClicked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestResponse.getResult().size();
    }


    public class RequestView extends RecyclerView.ViewHolder {
        CustomRequestLayoutBinding binding;

        public RequestView(@NonNull CustomRequestLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onRequestClicked {

        void onRequestProfileClicked(String userId, String docId);


        void onAcceptClickedListener(String documentId);

        void onCancelClickedListener(String documentId);

        void onAudioPlayClicked(ImageView play, ImageView pause, String Song);


        void onAudioPauseClicked();
    }
}
