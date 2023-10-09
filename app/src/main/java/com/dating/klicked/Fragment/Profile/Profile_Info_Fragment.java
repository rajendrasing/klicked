package com.dating.klicked.Fragment.Profile;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Authentication.Adapter.FavMusicAdapter;
import com.dating.klicked.Fragment.Adapter.HomeCardAdapter;
import com.dating.klicked.Fragment.Adapter.PreviewCardAdapter;
import com.dating.klicked.Fragment.Adapter.PreviewShowSubCardAdapter;
import com.dating.klicked.Fragment.Adapter.ShowSpecialFeatureAdapter;
import com.dating.klicked.FullScreenImage.Fullscreen_view;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.CardSubCardResponse;
import com.dating.klicked.Model.ResponseRepo.FavMusicResponse;
import com.dating.klicked.Model.ResponseRepo.MainCardResponse;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.UserDetailsByIdPresenter;
import com.dating.klicked.databinding.FragmentProfileInfoBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.ResponseBody;


public class Profile_Info_Fragment extends Fragment implements UserDetailsByIdPresenter.UserDetailsByIdView, View.OnClickListener, FavMusicAdapter.onFavMusicClick, PreviewCardAdapter.onPreviewCardClick, ShowSpecialFeatureAdapter.onShowSpecialFeatureClicked {
    FragmentProfileInfoBinding binding;
    private Context context;
    private Dialog dialog, reportDialog, blockDialog, subDialog;
    private View view;
    NavController navController;
    UserDetailsByIdPresenter presenter;
    public MediaPlayer mediaPlayer;
    String song, type, klickedMeter = "0";
    Boolean fav, sendRequest;
    User_Data userData;
    PopupWindow mypopupWindow;
    TextView text_viewProfie, text_Report, text_Block;
    String profileImg;


    public Profile_Info_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile__info, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile__info, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();

        setPopUpWindow();

        presenter = new UserDetailsByIdPresenter(this);
        binding.imgPlay.setOnClickListener(this);
        binding.imgPause.setOnClickListener(this);

        type = getArguments().getString("Type");

        if (type != null) {
            if (type.equalsIgnoreCase("adduser")) {
                fav = getArguments().getBoolean("fav", false);
                sendRequest = getArguments().getBoolean("sendRequest", false);
                binding.linearFavAddShare.setVisibility(View.VISIBLE);
               /* if (fav == true) {
                    binding.fav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_group_circle_bookmark));
                } else {*/
                binding.fav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_unfill_fav));

                //}

                if (sendRequest == true) {
                    binding.addFriend.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_circle_check));
                } else {

                    binding.addFriend.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_group_circle_addfri));
                }
            } else if (type.equalsIgnoreCase("klickedMeter")) {
                binding.relativeKlickedMeter.setVisibility(View.VISIBLE);
            } else if (type.equalsIgnoreCase("pending")) {
                binding.linearCancel.setVisibility(View.VISIBLE);
            } else if (type.equalsIgnoreCase("request")) {
                binding.linearCanAcc.setVisibility(View.VISIBLE);
            }
        }

        binding.fav.setOnClickListener(this);
        binding.addFriend.setOnClickListener(this);
        binding.linearCancel.setOnClickListener(this);
        binding.txtAccept.setOnClickListener(this);
        binding.txtCancel.setOnClickListener(this);
        binding.viewProfile.setOnClickListener(this);
        binding.txtChat.setOnClickListener(this);
        binding.share.setOnClickListener(this);

        presenter.getAllUserCard(getContext(), getArguments().getString("UserId"));

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
        if (message.equalsIgnoreCase("Card Not Exist")) {
            if (getArguments().getString("UserId") != null) {
                presenter.GetUserByIDProfile(getContext(), getArguments().getString("UserId"), null);
                presenter.ViewedProfile(getContext(), getArguments().getString("UserId"));
            }
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
    public void onGetCardSuccess(CardSubCardResponse cardResponses, String message) {
        if (message.equalsIgnoreCase("ok") && cardResponses.getResult().size() > 0) {
          /*  RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.cardRecycler.setHasFixedSize(true);
            binding.cardRecycler.setLayoutManager(layoutManager1);
            binding.cardRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.cardRecycler.setAdapter(new PreviewCardAdapter(getContext(), cardResponses, this));
    */    }

        if (getArguments().getString("UserId") != null) {
            presenter.OnGetKlickedMeter(getContext(), getArguments().getString("UserId"));
            presenter.GetUserByIDProfile(getContext(), getArguments().getString("UserId"), null);

            presenter.ViewedProfile(getContext(), getArguments().getString("UserId"));
        }

    }

    @Override
    public void onGetUserByIDProfileSuccess(UserProfileResponse userProfileResponse, String
            message, List<CardResponse> cardResponses) {
        if (message.equalsIgnoreCase("ok") && userProfileResponse != null) {

            Log.d("mvnjkkjc", userProfileResponse.getId());

            binding.name.setText(userProfileResponse.getFirstName());
            profileImg = userProfileResponse.getProfileImage();
            if (userProfileResponse.getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

                binding.imgProfile.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.profile1));
            } else {
                Glide.with(getContext()).load(PrefConf.IMAGE_URL + userProfileResponse.getProfileImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().circleCrop())
                        .into(binding.imgProfile);
            }

            String age = "";
            if (userProfileResponse.getDob() != null) {
                String currentString = userProfileResponse.getDob();
                String[] separated = currentString.split("/");
                age = AppUtils.getAge(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));

            }


            if (userProfileResponse.getAddress().size() > 0 && userProfileResponse.getAddress() != null) {
                binding.location.setVisibility(View.VISIBLE);
                binding.location.setText(userProfileResponse.getAddress().get(0).getCity() + "," + userProfileResponse.getAddress().get(0).getState() + "," + userProfileResponse.getAddress().get(0).getCountry() + "  " + age + " yr");

            } else {
                binding.location.setVisibility(View.INVISIBLE);
            }

            if (userProfileResponse.getBio() != null) {
                binding.txtAbout.setVisibility(View.VISIBLE);
                binding.txtDescription.setVisibility(View.VISIBLE);
                binding.txtDescription.setText(userProfileResponse.getBio());
            } else {
                binding.txtAbout.setVisibility(View.GONE);
                binding.txtDescription.setVisibility(View.GONE);

            }
            if (userProfileResponse.getOccupation() != null) {
                binding.txtOccupation.setVisibility(View.VISIBLE);
                binding.txt1.setVisibility(View.VISIBLE);
                binding.txtOccupation.setText(userProfileResponse.getOccupation());

            } else {
                binding.txt1.setVisibility(View.GONE);
                binding.txtOccupation.setVisibility(View.GONE);

            }

            if (userProfileResponse.getZodiacSign() != null) {
                binding.txtZodiac.setVisibility(View.VISIBLE);
                binding.txt2.setVisibility(View.VISIBLE);
                binding.txtZodiac.setText(userProfileResponse.getZodiacSign());
            } else {
                binding.txtZodiac.setVisibility(View.GONE);
                binding.txt2.setVisibility(View.GONE);

            }

            int radius = 0;
            if (type.equalsIgnoreCase("klickedMeter")) {
                if (userProfileResponse.getKlickedMeter() != null) {
                    binding.textProgress.setText(Math.round(Float.parseFloat(klickedMeter)) + "%");
                    binding.progressBar.setMax(100);
                    binding.progressBar.setProgress(Math.round(Float.parseFloat(klickedMeter)));
                    if (klickedMeter.equalsIgnoreCase("100")) {
                        radius = 1;
                    } else {
                        Log.d("radius", String.valueOf(klickedMeter));

                        radius = 100 - Math.round(Float.parseFloat(klickedMeter));
                        Log.d("radius", String.valueOf(radius));

                    }


                } else {
                    radius = 100;
                }
            } else {
                radius = 100;
            }


            Log.d("sizeeee", String.valueOf(userProfileResponse.getSpecialFeature().size()));

            if (userProfileResponse.getSpecialFeature().size() > 0 && userProfileResponse.getSpecialFeature() != null) {
                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                binding.specialRecyclerView.setHasFixedSize(true);
                binding.specialRecyclerView.setLayoutManager(layoutManager1);
                binding.specialRecyclerView.setItemAnimator(new DefaultItemAnimator());
                binding.specialRecyclerView.setAdapter(new ShowSpecialFeatureAdapter(userProfileResponse.getSpecialFeature(), getContext(), this, radius));
                binding.txt3.setVisibility(View.VISIBLE);
                binding.specialRecyclerView.setVisibility(View.VISIBLE);
            } else {
                binding.txt3.setVisibility(View.GONE);
                binding.specialRecyclerView.setVisibility(View.GONE);
            }



            song = userProfileResponse.getAudioDescription();

            if (userProfileResponse.getAudioDescription() != null) {
                binding.txt4.setVisibility(View.VISIBLE);
                binding.relative1.setVisibility(View.VISIBLE);
            } else {
                binding.txt4.setVisibility(View.GONE);
                binding.relative1.setVisibility(View.GONE);
            }


            if (userProfileResponse.getFavMusic().size() > 0 && userProfileResponse.getFavMusic() != null) {
                FavMusicResponse favMusicResponse = new FavMusicResponse();
                favMusicResponse.setFavMusic(userProfileResponse.getFavMusic());
                FavMusicAdapter favMusicAdapter = new FavMusicAdapter(getContext(), favMusicResponse, this, true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                binding.favRecycler.setLayoutManager(layoutManager);
                binding.favRecycler.setItemAnimator(new DefaultItemAnimator());
                binding.favRecycler.setHasFixedSize(true);
                binding.favRecycler.setAdapter(favMusicAdapter);
                binding.favRecycler.setVisibility(View.GONE);
                binding.txt5.setVisibility(View.GONE);

            } else {

                binding.txt5.setVisibility(View.GONE);
                binding.favRecycler.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onAddFavouriteSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully add user in Favourites")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            binding.fav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_group_circle_bookmark));

        }
    }

    @Override
    public void onDeleteFavouriteSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully Removed user in Favourites")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            sendRequest = false;
            binding.fav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_unfill_fav));

        }
    }

    @Override
    public void onSendRequestSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully Send Request in User")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            binding.addFriend.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_circle_check));

        }
    }

    @Override
    public void onCancelUserSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully  Cancel Pending Request in Klicks")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            binding.linearFavAddShare.setVisibility(View.VISIBLE);
            binding.linearCancel.setVisibility(View.GONE);

        }

    }

    @Override
    public void onCancelRequestUserSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully  Cancel Pending Request in Klicks")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            binding.linearFavAddShare.setVisibility(View.VISIBLE);
            binding.linearCanAcc.setVisibility(View.GONE);

        }
    }

    @Override
    public void onAcceptRequestUserSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully  add  Request in Klicks")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            binding.relativeKlickedMeter.setVisibility(View.VISIBLE);
            binding.linearCanAcc.setVisibility(View.GONE);

        }
    }

    @Override
    public void onCreateChatUserSuccess(ResponseBody response, String message) {
        String s = null;

        if (message.equalsIgnoreCase("ok")) {

            try {
                s = response.string();
                JSONObject jsonObject = new JSONObject(s);
                String res = jsonObject.getString("res");
                JSONObject jsonObject1 = new JSONObject(res);
                String ChatId = jsonObject1.getString("_id");
                Bundle bundle = new Bundle();
                bundle.putString("ChatId", ChatId);
                bundle.putString("Name", binding.name.getText().toString());
                bundle.putString("profile", profileImg);
                bundle.putString("userId", getArguments().getString("UserId"));
                navController.navigate(R.id.action_profile_Info_Fragment_to_userChat_Fragment, bundle);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onViewedProfileSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("View Profile Successfully ")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();


        }
    }

    @Override
    public void onBlockUserSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("This user has been successfully blocked")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();

            // blockDialog.dismiss();
        }
    }

    @Override
    public void onReportUserSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("This user's report has been successfully sent to the admin")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
        }
    }

    @Override
    public void onShareUserSuccess(String shareUserId, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Toast.makeText(getApplicationContext(), "Link Successful Generated", Toast.LENGTH_SHORT).show();
            AppUtils.shareUserProfile(getContext(), shareUserId);
        }
    }

    @Override
    public void onGetKlickedMeterSuccess(ResponseBody responseBody, String message) {
        String s = null;
        if (responseBody != null && message.equalsIgnoreCase("ok")) {
            try {
                s = responseBody.string();
                JSONObject jsonObject = new JSONObject(s);
                klickedMeter = jsonObject.getString("klickedMeter");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

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

        if (getArguments().getString("UserId") != null) {
            presenter.OnGetKlickedMeter(getContext(), getArguments().getString("UserId"));
            presenter.GetUserByIDProfile(getContext(), getArguments().getString("UserId"), null);

            presenter.ViewedProfile(getContext(), getArguments().getString("UserId"));
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
                stopPlaying();
                binding.imgPause.setVisibility(View.VISIBLE);
                binding.imgPlay.setVisibility(View.GONE);
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
                            binding.imgPause.setVisibility(View.GONE);
                            binding.imgPlay.setVisibility(View.VISIBLE);

                            // imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_wave_audio));

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
                binding.imgPlay.setVisibility(View.VISIBLE);
                binding.imgPause.setVisibility(View.GONE);
                if (mediaPlayer != null && mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;

            case R.id.fav:
                // if (fav == false) {
                if (getArguments().getString("UserId") != null) {
                    presenter.addUserFavourite(getContext(), getArguments().getString("UserId"));
                }
              /*  } else {
                    if (getArguments().getString("UserId") != null) {
                        presenter.deleteFavouriteUser(getContext(), getArguments().getString("UserId"));
                    }
                }*/
                break;

            case R.id.add_friend:
                if (getArguments().getString("UserId") != null) {
                    presenter.SendRequest(getContext(), userData.getId(), getArguments().getString("UserId"));
                }
                break;

            case R.id.linear_cancel:
                if (getArguments().getString("docId") != null) {
                    presenter.GetPendingCancelUser(getContext(), getArguments().getString("docId"));
                }
                break;

            case R.id.txt_cancel:
                if (getArguments().getString("docId") != null) {
                    presenter.GetRequestCancelUser(getContext(), getArguments().getString("docId"));
                }
                break;
            case R.id.txt_accept:
                if (getArguments().getString("docId") != null) {
                    presenter.GetRequestAcceptUser(getContext(), getArguments().getString("docId"));
                }
                break;

            case R.id.view_profile:
                mypopupWindow.showAsDropDown(view, -200, 20);
                break;

            case R.id.text_Report:
                ReportDialog(getActivity());
                mypopupWindow.dismiss();
                break;

            case R.id.text_Block:
                blockDialog(getActivity());
                mypopupWindow.dismiss();

                break;

            case R.id.txt_chat:
                presenter.CreateChatDocumentUser(getContext(), getArguments().getString("UserId"), binding.name.getText().toString().trim());
                break;

            case R.id.share:
                presenter.ShareUserProfile(getContext(), getArguments().getString("UserId"), userData.getId());
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
    public void onPlayFavMusicClickListener(String song, ImageView play, ImageView pause) {
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
    public void onPauseFavMusicClickListener() {
        if (mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.pause();

    }

    @Override
    public void onDeleteFavMusicClickListener(int position, FavMusicResponse favMusicResponse) {

    }

    private void setPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_popup_layout, null);

        text_viewProfie = (TextView) view.findViewById(R.id.text_viewProfie);
        text_viewProfie.setVisibility(View.GONE);
        text_Report = (TextView) view.findViewById(R.id.text_Report);
        text_Block = (TextView) view.findViewById(R.id.text_Block);

        text_Report.setOnClickListener(this);
        text_Block.setOnClickListener(this);

        mypopupWindow = new PopupWindow(view, 300, LinearLayout.LayoutParams.WRAP_CONTENT, true);


    }

    public void ReportDialog(Activity activity) {
        reportDialog = new Dialog(activity);


        reportDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reportDialog.setCancelable(false);
        reportDialog.setContentView(R.layout.custom_reports_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();


        lp.copyFrom(reportDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        reportDialog.getWindow().setAttributes(lp);
        reportDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView okButton = reportDialog.findViewById(R.id.txt_report);
        TextView cancelButton = reportDialog.findViewById(R.id.txt_cancel);
        RadioGroup radioGroup = reportDialog.findViewById(R.id.radio_group);

        cancelButton.setOnClickListener(view1 -> reportDialog.dismiss());

        okButton.setOnClickListener(view1 -> {
            RadioButton rb = (RadioButton) reportDialog.findViewById(radioGroup.getCheckedRadioButtonId());
            String reportText = rb.getText().toString();

            presenter.ReportUser(getContext(), getArguments().getString("UserId"), reportText);
            reportDialog.dismiss();
        });

        reportDialog.show();
    }

    public void blockDialog(Activity activity) {
        blockDialog = new Dialog(activity);


        blockDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        blockDialog.setCancelable(true);
        blockDialog.setContentView(R.layout.custom_speed_chat_pop);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();


        lp.copyFrom(blockDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        blockDialog.getWindow().setAttributes(lp);
        blockDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txt_title = blockDialog.findViewById(R.id.txt_title);
        TextView txt_des = blockDialog.findViewById(R.id.txt_des);
        TextView okButton = blockDialog.findViewById(R.id.txt_send);
        TextView cancelButton = blockDialog.findViewById(R.id.txt_cancel);

        txt_title.setText("Are you Sure You want to Block This User  ?");
        txt_des.setText(" If you block this user, you won't receive any messages from the user.");
        okButton.setText("Block");

        cancelButton.setOnClickListener(view1 -> {
            blockDialog.dismiss();
        });

        okButton.setOnClickListener(view1 -> {
            blockDialog.dismiss();
            presenter.BlockUser(getContext(), getArguments().getString("UserId"));
        });

        blockDialog.show();
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

    @Override
    public void onShowSpecialFeatureClickedListener(List<UserProfileResponse.SpecialFeature> list, int position, int blurSize) {
        // Toast.makeText(getContext(), ""+list.get(position).getImage(), Toast.LENGTH_SHORT).show();
        ArrayList<String> images = new ArrayList<String>();
        images.clear();
        for (int i = 0; i < list.size(); i++) {
            images.add(PrefConf.IMAGE_URL + list.get(i).getImage());
        }
        Intent fullImageIntent = new Intent(getActivity(), Fullscreen_view.class);
        fullImageIntent.putExtra(Fullscreen_view.URI_LIST_DATA, images);
        fullImageIntent.putExtra(Fullscreen_view.BLURSIZE, blurSize);
        fullImageIntent.putExtra(Fullscreen_view.CURRENTPOSITION, position);
        startActivity(fullImageIntent);
    }
}