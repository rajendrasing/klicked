package com.dating.klicked.Fragment.Profile;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Authentication.Adapter.MainCardAdapter;
import com.dating.klicked.Fragment.Adapter.HomeCardAdapter;
import com.dating.klicked.Fragment.Adapter.PreviewCardAdapter;
import com.dating.klicked.Fragment.Adapter.PreviewShowSubCardAdapter;
import com.dating.klicked.Model.ResponseRepo.CardSubCardResponse;
import com.dating.klicked.Model.ResponseRepo.MainCardResponse;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.PreviewPresenter;
import com.dating.klicked.ViewPresenter.UserDetailsByIdPresenter;
import com.dating.klicked.databinding.FragmentPreviewYourProfileBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.List;


public class Preview_your_profile extends Fragment implements PreviewPresenter.PreviewView, View.OnClickListener, PreviewCardAdapter.onPreviewCardClick {

    FragmentPreviewYourProfileBinding binding;
    private Dialog dialog, subDialog;
    private View view;
    NavController navController;
    public MediaPlayer mediaPlayer;
    String song;
    PreviewPresenter presenter;
    User_Data userData;


    public Preview_your_profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_preview_your_profile, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preview_your_profile, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();

        presenter = new PreviewPresenter(this);
        binding.imgPlay.setOnClickListener(this);
        binding.imgPause.setOnClickListener(this);
        binding.textSeeMore.setOnClickListener(this);

        presenter.GetProfile(getContext());
        presenter.getAllUserCard(getContext(), userData.getId());

        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


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
    public void onGetProfileSuccess(UserProfileResponse userProfileResponse, String message) {
        if (message.equalsIgnoreCase("ok") && userProfileResponse != null) {
            binding.name.setText(userProfileResponse.getFirstName());

            if (userProfileResponse.getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

                binding.imgProfile.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.profile1));
            } else {
                Glide.with(getContext()).load(PrefConf.IMAGE_URL + userProfileResponse.getProfileImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().circleCrop())
                        .into(binding.imgProfile);
            }

            String currentString = userProfileResponse.getDob();

            if (currentString != null) {
                String[] separated = currentString.split("/");
                String age = AppUtils.getAge(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));

                if (userProfileResponse.getAddress().size() > 0 && userProfileResponse.getAddress() != null) {
                    binding.location.setVisibility(View.VISIBLE);
                    binding.location.setText(userProfileResponse.getAddress().get(0).getCity() + "," + userProfileResponse.getAddress().get(0).getState() + "," + userProfileResponse.getAddress().get(0).getCountry() + "   " + age + " yr");
                } else {
                    binding.location.setVisibility(View.INVISIBLE);
                }
            } else {
                if (userProfileResponse.getAddress().size() > 0 && userProfileResponse.getAddress() != null) {
                    binding.location.setVisibility(View.VISIBLE);

                    binding.location.setText(userProfileResponse.getAddress().get(0).getCity() + "," + userProfileResponse.getAddress().get(0).getState() + "," + userProfileResponse.getAddress().get(0).getCountry());

                } else {
                    binding.location.setVisibility(View.INVISIBLE);
                }
            }


            if (userProfileResponse.getBio() != null) {
                binding.textSeeMore.setVisibility(View.VISIBLE);
                binding.txtDescription.setVisibility(View.VISIBLE);
                binding.txtDescription.setText(userProfileResponse.getBio());
                // Log.d("lineCount", String.valueOf(binding.txtDescription.getLineCount()));
            } else {
                binding.textSeeMore.setVisibility(View.GONE);
                binding.txtDescription.setVisibility(View.GONE);
            }

            if (userProfileResponse.getAudioDescription() != null) {
                binding.myAudio.setVisibility(View.VISIBLE);
                binding.relative1.setVisibility(View.VISIBLE);
                song = userProfileResponse.getAudioDescription();
            } else {
                binding.myAudio.setVisibility(View.VISIBLE);
                binding.relative1.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void onGetCardSuccess(CardSubCardResponse cardSubCardResponse, String message) {
        if (message.equalsIgnoreCase("ok") && cardSubCardResponse.getResult().size() > 0) {
        /*    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.cardRecycler.setHasFixedSize(true);
            binding.cardRecycler.setLayoutManager(layoutManager1);
            binding.cardRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.cardRecycler.setAdapter(new PreviewCardAdapter(getContext(), cardSubCardResponse, this));
*/
        }
    }

    @Override
    public void onGetUSerCardSuccess(MainCardResponse cardResponses, String message) {
        if (message.equalsIgnoreCase("ok") && cardResponses.getCards().size() > 0) {
            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.cardRecycler.setHasFixedSize(true);
            binding.cardRecycler.setLayoutManager(layoutManager1);
            binding.cardRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.cardRecycler.setAdapter(new PreviewCardAdapter(getContext(), cardResponses ));
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
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_play:
                binding.imgPause.setVisibility(View.VISIBLE);
                binding.imgPlay.setVisibility(View.GONE);

                stopPlaying();
                Uri uri = Uri.parse(PrefConf.IMAGE_URL + song);
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(getContext(), uri);
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
                            binding.imgPause.setVisibility(View.GONE);
                            binding.imgPlay.setVisibility(View.VISIBLE);

                        }
                    });
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                            binding.imgPause.setVisibility(View.GONE);
                            binding.imgPlay.setVisibility(View.VISIBLE);

                            Toast.makeText(view.getContext(), "Not Supported this audio", Toast.LENGTH_SHORT).show();

                            return true;
                        }
                    });

                } catch (Exception e) {
                    System.out.println(e.toString());
                    binding.imgPause.setVisibility(View.GONE);
                    binding.imgPlay.setVisibility(View.VISIBLE);

                    Toast.makeText(view.getContext(), "Not Supported this audio", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.img_pause:
                binding.imgPause.setVisibility(View.GONE);
                binding.imgPlay.setVisibility(View.VISIBLE);

                if (mediaPlayer != null && mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;

            case R.id.text_see_more:
                binding.textSeeMore.setVisibility(View.INVISIBLE);
                binding.txtDescription.setMaxLines(Integer.MAX_VALUE);
                break;

        }
    }

    private void stopPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlaying();
    }

    @Override
    public void onPreviewCardClickListener(List<CardSubCardResponse.Result.Subcard> subcard) {
        subDialog = new Dialog(getActivity());


        subDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        subDialog.setCancelable(true);
        subDialog.setContentView(R.layout.custom_show_subcard_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(subDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        subDialog.getWindow().setAttributes(lp);
        subDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        RecyclerView recyclerView = (RecyclerView) subDialog.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new PreviewShowSubCardAdapter(getContext(), subcard));

        subDialog.show();

    }
}