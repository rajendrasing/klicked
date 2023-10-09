package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.BlockUserListResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomBlockUserListLayoutBinding;
import com.dating.klicked.databinding.CustomChatLayoutBinding;
import com.dating.klicked.utils.AppUtils;

public class BlockedUserListAdapter extends RecyclerView.Adapter<BlockedUserListAdapter.BlockedUserListView> {
    public Context context;
    BlockUserListResponse response;
    BlockedUserListViewClicked viewClicked;
    public MediaPlayer mediaPlayer;

    public BlockedUserListAdapter(Context context, BlockUserListResponse response, BlockedUserListViewClicked viewClicked) {
        this.context = context;
        this.response = response;
        this.viewClicked = viewClicked;
    }

    @NonNull
    @Override
    public BlockedUserListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomBlockUserListLayoutBinding binding = CustomBlockUserListLayoutBinding.inflate(inflater, parent, false);

        return new BlockedUserListView(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull BlockedUserListView holder, int position) {
        if (response.getBlockedUser().get(position).getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

            holder.binding.imgProfile.setImageDrawable(context.getResources().getDrawable(R.mipmap.profile1));
        } else {
            Glide.with(context).load(PrefConf.IMAGE_URL + response.getBlockedUser().get(position).getProfileImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.binding.imgProfile);
        }

        holder.binding.name.setText(response.getBlockedUser().get(position).getFirstName());
        if (response.getBlockedUser().get(position).getKycVerified().equalsIgnoreCase("Done") || response.getBlockedUser().get(position).getKycVerified().equalsIgnoreCase("verified")) {
            holder.binding.name.setCompoundDrawables(null,null,context.getResources().getDrawable(R.drawable.ic_noun_verified),null);
        } else {
            holder.binding.name.setCompoundDrawables(null,null,null,null);

        }
        String currentString = response.getBlockedUser().get(position).getDob();
        if (currentString != null) {
            String[] separated = currentString.split("/");
            String age = AppUtils.getAge(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));
            if (response.getBlockedUser().get(position).getAddress().size() > 0) {
                holder.binding.location.setText(response.getBlockedUser().get(position).getAddress().get(0).getCity() + "," + response.getBlockedUser().get(position).getAddress().get(0).getState() + "," + response.getBlockedUser().get(position).getAddress().get(0).getCountry() + "   " + age+" yr");
            } else {

                holder.binding.location.setText("" +age+" yr");
            }
        } else {
            if (response.getBlockedUser().get(position).getAddress().size() > 0) {
                holder.binding.location.setText(response.getBlockedUser().get(position).getAddress().get(0).getCity() + "," + response.getBlockedUser().get(position).getAddress().get(0).getState() + "," + response.getBlockedUser().get(position).getAddress().get(0).getCountry());
            } else {

                holder.binding.location.setVisibility(View.GONE);
            }


        }

        holder.binding.txtUnblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewClicked.onBlockedUserListViewClickedListener(response.getBlockedUser().get(position).getId());
            }
        });
        if (response.getBlockedUser().get(position).getAudioDescription() != null) {
            holder.binding.relative1.setVisibility(View.VISIBLE);
        } else {
            holder.binding.relative1.setVisibility(View.GONE);
        }

        holder.binding.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                mediaPlayer = MediaPlayer.create(context, Uri.parse(PrefConf.IMAGE_URL + response.getBlockedUser().get(position).getAudioDescription()));

                try {
                    mediaPlayer.start();
                    //  mediaPlayer.setLooping(true);
                    holder.binding.imgPause.setVisibility(View.VISIBLE);
                    holder.binding.imgPlay.setVisibility(View.GONE);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlaying();
                            holder.binding.imgPlay.setVisibility(View.VISIBLE);
                            holder.binding.imgPause.setVisibility(View.GONE);
                        }
                    });

                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                            Toast.makeText(view.getContext(), "Not Supported this audio", Toast.LENGTH_SHORT).show();
                            holder.binding.imgPlay.setVisibility(View.VISIBLE);
                            holder.binding.imgPause.setVisibility(View.GONE);

                            return true;
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Not Supported this audio", Toast.LENGTH_SHORT).show();
                    holder.binding.imgPlay.setVisibility(View.VISIBLE);
                    holder.binding.imgPause.setVisibility(View.GONE);
                }


            }
        });

        holder.binding.imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null && mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                holder.binding.imgPlay.setVisibility(View.VISIBLE);
                holder.binding.imgPause.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public int getItemCount() {
        return response.getBlockedUser().size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BlockedUserListView holder) {
        super.onViewDetachedFromWindow(holder);
        stopPlaying();
    }


    public class BlockedUserListView extends RecyclerView.ViewHolder {
        CustomBlockUserListLayoutBinding binding;

        public BlockedUserListView(@NonNull CustomBlockUserListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface BlockedUserListViewClicked {
        void onBlockedUserListViewClickedListener(String userId);

    }

    private void stopPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
