package com.dating.klicked.Authentication.Adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.ResponseRepo.FavMusicResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomFavMusicLayoutBinding;

import java.io.IOException;

public class FavMusicAdapter extends RecyclerView.Adapter<FavMusicAdapter.FavMusicView> {
    Context context;
    FavMusicResponse favMusicResponse;
    private onFavMusicClick click;
    Boolean aBoolean;


    public FavMusicAdapter(Context context, FavMusicResponse favMusicResponse, onFavMusicClick click, Boolean aBoolean) {
        this.context = context;
        this.favMusicResponse = favMusicResponse;
        this.click = click;
        this.aBoolean = aBoolean;
    }

    public FavMusicAdapter() {
    }

    @NonNull
    @Override
    public FavMusicView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomFavMusicLayoutBinding binding = CustomFavMusicLayoutBinding.inflate(inflater, parent, false);

        return new FavMusicView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMusicView holder, int position) {
        if (aBoolean == true) {
            holder.binding.imgDelete.setVisibility(View.GONE);
        }
        holder.binding.imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.imgPause.setVisibility(View.GONE);
                holder.binding.imgPlay.setVisibility(View.VISIBLE);

                click.onPauseFavMusicClickListener();
            }
        });
        holder.binding.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.imgPause.setVisibility(View.VISIBLE);
                holder.binding.imgPlay.setVisibility(View.GONE);

                click.onPlayFavMusicClickListener(favMusicResponse.getFavMusic().get(position), holder.binding.imgPlay, holder.binding.imgPause);
            }
        });
        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onDeleteFavMusicClickListener(position, favMusicResponse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favMusicResponse.getFavMusic().size();
    }

    public class FavMusicView extends RecyclerView.ViewHolder {

        CustomFavMusicLayoutBinding binding;

        public FavMusicView(@NonNull CustomFavMusicLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onFavMusicClick {
        void onPlayFavMusicClickListener(String song, ImageView play, ImageView pause);

        void onPauseFavMusicClickListener();

        void onDeleteFavMusicClickListener(int position, FavMusicResponse favMusicResponse);
    }
}
