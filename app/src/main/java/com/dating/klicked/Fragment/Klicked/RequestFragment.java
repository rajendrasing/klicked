package com.dating.klicked.Fragment.Klicked;

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

import com.dating.klicked.Fragment.Adapter.PendingAdapter;
import com.dating.klicked.Fragment.Adapter.RequestAdapter;
import com.dating.klicked.Model.ResponseRepo.PendingResponse;
import com.dating.klicked.R;
import com.dating.klicked.ViewPresenter.RequestUserPresenter;
import com.dating.klicked.databinding.FragmentRequestBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;

import okhttp3.ResponseBody;

public class RequestFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestUserPresenter.RequestUserView, RequestAdapter.onRequestClicked {
    FragmentRequestBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    RequestUserPresenter presenter;
    public MediaPlayer mediaPlayer;
    boolean check = true;

    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_request, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        presenter = new RequestUserPresenter(this);

        presenter.GetRequestUserSuccess(getContext());

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

        presenter.GetRequestUserSuccess(getContext());
        binding.SwipeRefresh.setRefreshing(false);
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
        if (message.equalsIgnoreCase("Do not created, try again")) {
            presenter.GetRequestUserSuccess(getContext());
        } else {
            Sneaker.with(getActivity())
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        }
    }

    @Override
    public void onGetRequestUserSuccess(PendingResponse response, String message) {

        if (message.equalsIgnoreCase("ok") && response.getResult() != null && response.getResult().size() > 0) {
            check = true;
            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.otherRecyclerView.setHasFixedSize(true);
            binding.otherRecyclerView.setLayoutManager(layoutManager1);
            binding.otherRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.otherRecyclerView.setAdapter(new RequestAdapter(getContext(), response, this));
            binding.otherRecyclerView.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.GONE);
        } else {
            binding.otherRecyclerView.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onCancelRequestUserSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            presenter.GetRequestUserSuccess(getContext());
        }

    }

    @Override
    public void onAcceptRequestUserSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            presenter.GetRequestUserSuccess(getContext());
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

    @Override
    public void onRequestProfileClicked(String userId, String docId) {
        Bundle bundle = new Bundle();
        bundle.putString("UserId", userId);
        bundle.putString("Type", "request");
        bundle.putString("docId", docId);
        navController.navigate(R.id.profile_Info_Fragment, bundle);
    }

    @Override
    public void onAcceptClickedListener(String documentId) {
        if (check) {
            presenter.GetRequestAcceptUser(getContext(), documentId);
            check = false;
        }



    }

    @Override
    public void onCancelClickedListener(String documentId) {
        presenter.GetRequestCancelUser(getContext(), documentId);

    }

    @Override
    public void onAudioPlayClicked(ImageView play, ImageView pause, String Song) {
        stopPlaying();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getContext(), Uri.parse(Song));
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    Toast.makeText(getContext(), "Playing", Toast.LENGTH_SHORT).show();

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlaying();
                    pause.setVisibility(View.GONE);
                    play.setVisibility(View.VISIBLE);

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
    public void onAudioPauseClicked() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();

        Toast.makeText(getContext(), "pause", Toast.LENGTH_SHORT).show();
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
}