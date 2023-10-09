package com.dating.klicked.Fragment.Profile;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dating.klicked.Fragment.Adapter.ChatAdapter;
import com.dating.klicked.Fragment.Adapter.ViewedProfileAdapter;
import com.dating.klicked.Model.ResponseRepo.ViewedProfiledResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.ViewPresenter.ViewedProfilePresenter;
import com.dating.klicked.databinding.FragmentProfileViewedBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;


public class Profile_ViewedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ViewedProfileAdapter.OnViewedProfileClicked, ViewedProfilePresenter.ViewedProfileView {
    FragmentProfileViewedBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    public MediaPlayer mediaPlayer;
    ViewedProfilePresenter presenter;


    public Profile_ViewedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile__viewed, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new ViewedProfilePresenter(this);
        presenter.ViewProfile(getContext());

        binding.SwipeRefresh.setOnRefreshListener(this);
        binding.SwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.global__primary));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }


    @Override
    public void onRefresh() {

        presenter.ViewProfile(getContext());

        binding.SwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onPlayProfileMusicClickListener(String song, ImageView play, ImageView pause) {
        stopPlaying();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getContext(), Uri.parse(PrefConf.IMAGE_URL + song));
            mediaPlayer.prepare();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlaying();
                    pause.setVisibility(View.GONE);
                    play.setVisibility(View.VISIBLE);

                    // imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_wave_audio));

                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    pause.setVisibility(View.GONE);
                    play.setVisibility(View.VISIBLE);

                    Toast.makeText(view.getContext(), "Not Supported this audio", Toast.LENGTH_SHORT).show();

                    return true;
                }
            });

        } catch (Exception e) {
            System.out.println(e.toString());
            pause.setVisibility(View.GONE);
            play.setVisibility(View.VISIBLE);

            Toast.makeText(view.getContext(), "Not Supported this audio", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onPauseProfileMusicClickListener() {

        if (mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlaying();
    }

    private void stopPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    dialog.dismiss();
                }


            }, 1000);

        }
    }

    @Override
    public void onError(String message) {
        Sneaker.with(getActivity())
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();

    }

    @Override
    public void onGetViewedProfileSuccess(List<ViewedProfiledResponse> list, String message) {
        if (message.equalsIgnoreCase("ok") && list.size() > 0) {
            if (list.get(0).getUsers() != null && list.get(0).getUsers().size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                binding.recyclerView.setHasFixedSize(true);
                binding.recyclerView.setLayoutManager(layoutManager);
                binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                binding.recyclerView.setAdapter(new ViewedProfileAdapter(getContext(), list, this));
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.imdNoDta.setVisibility(View.GONE);
                binding.unshowText.setVisibility(View.GONE);
            } else {
                binding.recyclerView.setVisibility(View.GONE);
                binding.imdNoDta.setVisibility(View.VISIBLE);
                binding.unshowText.setVisibility(View.VISIBLE);
            }
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(getActivity())
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();

    }
}