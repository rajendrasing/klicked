package com.dating.klicked.Fragment.BottomMenu;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dating.klicked.Activity.IntroActivity;
import com.dating.klicked.Fragment.Adapter.HomeAdapter;
import com.dating.klicked.Fragment.Adapter.ZodiacFilterAdapter;
import com.dating.klicked.Fragment.Adapter.filterOrientationAdapter;
import com.dating.klicked.Fragment.SideMenu.PlanDialog;
import com.dating.klicked.Model.RequestRepo.FilterBody;
import com.dating.klicked.Model.RequestRepo.SpeedChatDeleteBody;
import com.dating.klicked.Model.ResponseRepo.HomeResponse;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.Model.Zodiac_filter_model;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.HomePresenter;
import com.dating.klicked.databinding.FragmentHomeBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.GridSpacingItemDecoration;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.irozon.sneaker.Sneaker;
import com.warkiz.widget.IndicatorSeekBar;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import okhttp3.ResponseBody;


public class Home_fragment extends Fragment implements HomeAdapter.onHomeClick, SwipeRefreshLayout.OnRefreshListener, HomePresenter.HomeView, View.OnClickListener, ZodiacFilterAdapter.OnZodiacFilterViewClicked {
    private FragmentHomeBinding binding;
    private Context context;
    private Dialog dialog, reportDialog, blockDialog;
    private View view;
    NavController navController;
    public MediaPlayer mediaPlayer, backMediaPlayer;
    HomePresenter presenter;
    PopupWindow mypopupWindow;
    TextView text_viewProfie, text_Report, text_Block;
    ImageView imageView, addFriend, imgPlay, imgPause;
    User_Data userData;
    String UserId, zodaicSign = null, location = null, Song, checkPlans;
    Integer minHeight = null, height = null, minage = null, maxage = null;
    Boolean fav = false, sendRequest = false, musicPlay, loading = true,INTRO_DEEPLINK;
    BottomSheetDialog bottomSheetDialog;
    private AppUtils psDialogMsg;
    ArrayList<String> userId = new ArrayList<String>();


    public Home_fragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //   return inflater.inflate(R.layout.fragment_home, container, false);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            INTRO_DEEPLINK = MyPreferences.getInstance(getActivity()).getBoolean(PrefConf.INTRO_DEEPLINK, false);

            if (INTRO_DEEPLINK == false) {
                Intent intent = new Intent(getContext(), IntroActivity.class);
                startActivity(intent);
            }
        }


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();

        Song = MyPreferences.getInstance(getContext()).getString(PrefConf.Musicsong, null);

        musicPlay = MyPreferences.getInstance(getContext()).getBoolean(PrefConf.Music, false);
        Log.d("musicPlay", String.valueOf(musicPlay));

        if (musicPlay == true) {
            BackStartPlaying();
        } else {
            binding.imgSound.setColorFilter(getContext().getResources().getColor(R.color.un_select));
            //  MyPreferences.getInstance(getContext()).putBoolean(PrefConf.Music, false);

        }


        presenter = new HomePresenter(this);
        setPopUpWindow();
        // Toast.makeText(getContext(), "nkjklsvnc jklnc", Toast.LENGTH_SHORT).show();
        presenter.GetUserProfile(getContext());

        binding.SwipeRefresh.setOnRefreshListener(this);
        binding.SwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.global__primary));
        binding.imgFilter.setOnClickListener(this);
        binding.imgSound.setOnClickListener(this);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }

    @Override
    public void onGetHomePosition(int position) {
        if (mypopupWindow.isShowing()) {
            mypopupWindow.dismiss();
        }
        stopPlaying();

    }

    @Override
    public void onPlayFavMusicClickListener(String song, ImageView play, ImageView pause, View view) {
        imgPlay = view.findViewById(R.id.img_play);
        imgPause = view.findViewById(R.id.img_pause);
        stopPlaying();
        BackstopPlaying();
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
                    if (musicPlay == true) {
                        BackStartPlaying();
                    }
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
    public void onPauseFavMusicClickListener(ImageView imageView) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_wave_audio));
                mediaPlayer.pause();
            }
        }

        if (musicPlay == true) {
            BackStartPlaying();
        }
    }

    @Override
    public void onPopMenuClickListener(View view, String userid, Boolean aBoolean) {
        UserId = userid;
        if (aBoolean == true) {
            Bundle bundle = new Bundle();
            bundle.putString("UserId", UserId);
            bundle.putString("Type", "adduser");
            bundle.putString("docId", null);
            bundle.putBoolean("fav", fav);
            bundle.putBoolean("sendRequest", sendRequest);
            navController.navigate(R.id.action_home_fragment_to_profile_Info_Fragment, bundle);

        } else {
            mypopupWindow.showAsDropDown(view, -260, 0);
        }
    }

    @Override
    public void onFavClickedListener(View view, String userid, Boolean aBoolean) {

        imageView = view.findViewById(R.id.fav);
        //   if (aBoolean) {

        presenter.addUserFavourite(getContext(), userid);
      /*  } else {
            presenter.deleteFavouriteUser(getContext(), userid);
        }*/


    }

    @Override
    public void onSendRequestClickedListener(View view, String receiverId, Boolean aBoolean) {
        addFriend = view.findViewById(R.id.add_friend);
        if (aBoolean) {
            sendRequest = true;
            presenter.SendRequest(getContext(), userData.getId(), receiverId);
        } else {
            //  presenter.deleteFavouriteUser(getContext(),userid);
            sendRequest = false;
            addFriend.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_group_circle_addfri));

        }
    }

    @Override
    public void onShareProfileUser(String userId) {
        /*Sneaker.with(getActivity())
                .setTitle("Coming Soon")
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakSuccess();*/
        presenter.ShareUserProfile(getContext(), userId, userData.getId());
    }


    @Override
    public void onRefresh() {

      /*  FilterBody.Filters filters = new FilterBody.Filters();
        FilterBody body = new FilterBody(filters);
        presenter.GetProfile(getContext(), body);*/
        presenter.GetUserProfile(getContext());


        binding.SwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlaying();
        BackstopPlaying();
        binding.imgSound.setColorFilter(getContext().getResources().getColor(R.color.un_select));
        MyPreferences.getInstance(getContext()).putBoolean(PrefConf.Music, false);


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void stopPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void BackstopPlaying() {
        if (backMediaPlayer != null && backMediaPlayer.isPlaying()) {
            backMediaPlayer.stop();
            backMediaPlayer.release();
            backMediaPlayer = null;
        }
    }

    private void BackStartPlaying() {
        BackstopPlaying();
        Uri uri = Uri.parse(Song);
        backMediaPlayer = new MediaPlayer();
        backMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            backMediaPlayer.setDataSource(getContext(), uri);
            backMediaPlayer.prepare();
            backMediaPlayer.setLooping(true);
            backMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();

                }
            });
            backMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Toast.makeText(view.getContext(), "Not Supported this audio", Toast.LENGTH_SHORT).show();
                    binding.imgSound.setColorFilter(getContext().getResources().getColor(R.color.un_select));
                    MyPreferences.getInstance(getContext()).putBoolean(PrefConf.Music, false);

                    return true;
                }
            });

        } catch (Exception e) {
            System.out.println(e.toString());
            Toast.makeText(view.getContext(), "Not Supported this audio", Toast.LENGTH_SHORT).show();
            binding.imgSound.setColorFilter(getContext().getResources().getColor(R.color.un_select));
            MyPreferences.getInstance(getContext()).putBoolean(PrefConf.Music, false);

        }
    }


    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if (getActivity()!=null){
            Sneaker.with(getActivity())
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        }

    }


    @Override
    public void onGetProfileSuccess(HomeResponse homeResponse, String message) {

        userId.clear();
        if (homeResponse.getResult() != null && message.equalsIgnoreCase("ok") && homeResponse.getResult().size() > 0) {
            //   binding.viewPager.setAdapter(new HomeAdapter(getContext(), homeResponse, this));


            userId.add(homeResponse.getResult().get(0).getId());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.recyclerView.setLayoutManager(layoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setAdapter(new HomeAdapter(getContext(), homeResponse, this));
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);
            SnapHelper snapHelper = new PagerSnapHelper();
            if (binding.recyclerView.getOnFlingListener() == null)
                snapHelper.attachToRecyclerView(binding.recyclerView);

            binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int position = getCurrentItem();
                        userId.add(homeResponse.getResult().get(position).getId());

                        Set<String> set = new LinkedHashSet<>();
                        set.addAll(userId);

                        // delete al elements of arraylist
                        userId.clear();

                        // add element from set to arraylist
                        userId.addAll(set);
                        Log.d("visibleItemCount", String.valueOf(position));
                        Log.d("visibleItemCount", String.valueOf(userId));

                        //onPageChanged(position);
                        // Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (dy > 0) { //check for scroll down
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int pastVisiblesItems = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        if (loading) {
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                loading = true;
                                if (checkPlans == null) {
                                    psDialogMsg = new AppUtils(getActivity(), true);
                                    psDialogMsg.showFAQLogoutDialog("Boost your profile", "your account is not completed.\nverify your account and get more\n reache and connections.", "Boost Now", getActivity().getDrawable(R.drawable.ic_boost_profile_icon));
                                    psDialogMsg.show();
                                    psDialogMsg.txt_ready.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            psDialogMsg.cancel();
                                            PlanDialog planDialog = new PlanDialog(getActivity());
                                            planDialog.startLoadingdialog();
                                        }
                                    });
                                }
                                loading = false;
                            }
                        }
                    }
                }
            });

        } else {

            binding.recyclerView.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.VISIBLE);
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
            sendRequest = true;
            imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_group_circle_bookmark));

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
            imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_unfill_fav));

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
            addFriend.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_circle_check));

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

            //    blockDialog.dismiss();

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
    public void onReportUserSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {

            Sneaker.with(getActivity())
                    .setTitle("This user's report has been successfully sent to the admin")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();

            //   reportDialog.dismiss();

        }
    }

    @Override
    public void onGetUserProfileSuccess(UserProfileResponse userProfileResponse, String message) {
        if (message.equalsIgnoreCase("ok")) {
            checkPlans = userProfileResponse.getPurchasedPlan();
            Double aDouble = Double.parseDouble(userProfileResponse.getRemaingSpeedDatingTime());
            MyPreferences.getInstance(getContext()).getLong(PrefConf.ReamingTimmer,Math.round(aDouble));
            /*FilterBody.Filters filters = new FilterBody.Filters();
            FilterBody body = new FilterBody(filters);*/
            presenter.GetAllUserHomeProfile(getContext());
        }
    }

    @Override
    public void onDeleteUserSeenSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Log.d("seenUser", message);
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

    private void setPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_popup_layout, null);

        text_viewProfie = (TextView) view.findViewById(R.id.text_viewProfie);
        text_Report = (TextView) view.findViewById(R.id.text_Report);
        text_Block = (TextView) view.findViewById(R.id.text_Block);

        text_viewProfie.setOnClickListener(this);
        text_Report.setOnClickListener(this);
        text_Block.setOnClickListener(this);

        mypopupWindow = new PopupWindow(view, 400, LinearLayout.LayoutParams.WRAP_CONTENT, true);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.text_viewProfie:
                deleteSeenUser();
                Bundle bundle = new Bundle();
                bundle.putString("UserId", UserId);
                bundle.putString("Type", "adduser");
                bundle.putString("docId", null);
                bundle.putBoolean("fav", fav);
                bundle.putBoolean("sendRequest", sendRequest);
                navController.navigate(R.id.action_home_fragment_to_profile_Info_Fragment, bundle);
                mypopupWindow.dismiss();
                break;


            case R.id.text_Report:
                ReportDialog(getActivity());
                mypopupWindow.dismiss();
                break;

            case R.id.text_Block:
                blockDialog(getActivity());
                mypopupWindow.dismiss();
                break;

            case R.id.img_filter:
                zodaicSign = null;
                location = null;
                minHeight = null;
                height = null;
                minage = null;
                maxage = null;
                filterOrientationAdapter.selectedOrientation.clear();
                Filter_Bottom_Sheet();
                break;

            case R.id.img_sound:
                if (musicPlay == true) {
                    musicPlay = false;
                    if (backMediaPlayer.isPlaying()) {
                        backMediaPlayer.pause();
                    }
                    binding.imgSound.setColorFilter(getContext().getResources().getColor(R.color.un_select));
                    MyPreferences.getInstance(getContext()).putBoolean(PrefConf.Music, false);


                } else if (musicPlay == false) {
                    musicPlay = true;
                    MyPreferences.getInstance(getContext()).putBoolean(PrefConf.Music, true);
                    binding.imgSound.setColorFilter(getContext().getResources().getColor(R.color.global__primary));
                    BackStartPlaying();

                }
                break;
        }
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

            // Toast.makeText(getContext(), ""+reportText, Toast.LENGTH_SHORT).show();

            presenter.ReportUser(getContext(), UserId, reportText);
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
            presenter.BlockUser(getContext(), UserId);
        });

        blockDialog.show();
    }

    private void Filter_Bottom_Sheet() {


        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.custom_filter_layout);

        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recyclerViewOrientation);
        RecyclerView zodiacRecyclerView = bottomSheetDialog.findViewById(R.id.recyclerViewZodiac);
        EditText ed_location = bottomSheetDialog.findViewById(R.id.ed_location);
        EditText ed_minAge = bottomSheetDialog.findViewById(R.id.ed_minAge);
        EditText ed_maxAge = bottomSheetDialog.findViewById(R.id.ed_maxAge);
        IndicatorSeekBar seekBar = bottomSheetDialog.findViewById(R.id.seekbar);
        LinearLayout txt_apply = bottomSheetDialog.findViewById(R.id.txt_apply);
        TextView reset = bottomSheetDialog.findViewById(R.id.txt_reset);

        ZodicList(zodiacRecyclerView);
        OrientationList(recyclerView);


        setupFullHeight(bottomSheetDialog);
        bottomSheetDialog.show();

        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        txt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minage1 = ed_minAge.getText().toString().trim();
                String maxage1 = ed_maxAge.getText().toString().trim();

                if (seekBar.getProgress() > 30) {
                    minHeight = 30;
                    height = seekBar.getProgress();
                }
                if (!ed_location.getText().toString().isEmpty()) {
                    location = ed_location.getText().toString().trim();
                }
                if (!minage1.isEmpty()) {
                    minage = Integer.valueOf(minage1);
                }
                if (!maxage1.isEmpty()) {
                    maxage = Integer.valueOf(maxage1);
                }

                bottomSheetDialog.dismiss();
                if (filterOrientationAdapter.selectedOrientation.size() > 0) {
                    FilterBody.Filters filters = new FilterBody.Filters(location, minage, maxage, height, minHeight, filterOrientationAdapter.selectedOrientation, zodaicSign);

                    FilterBody body = new FilterBody(filters);

                    presenter.GetProfile(getContext(), body);

                } else {
                    FilterBody.Filters filters = new FilterBody.Filters(location, minage, maxage, height, minHeight, null, zodaicSign);

                    FilterBody body = new FilterBody(filters);

                    presenter.GetProfile(getContext(), body);
                }
                Log.d("selectedOrientation", filterOrientationAdapter.selectedOrientation.toString() + ed_location.getText().toString());
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_location.setText("");
                ed_minAge.setText("");
                ed_maxAge.setText("");
                seekBar.setProgress(30f);
                ZodicList(zodiacRecyclerView);
                OrientationList(recyclerView);
                filterOrientationAdapter.selectedOrientation.clear();
                zodaicSign = null;
            }
        });


    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public void onZodiacFilterClickListener(String name) {
        zodaicSign = name.toLowerCase();
    }

    public void OrientationList(RecyclerView recyclerView) {
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        list.add("Straight");
        list.add("Gay");
        list.add("Lesbian");
        list.add("Bisexual");
        list.add("Asexual");
        list.add("Demisexual");
        list.add("Pansexual");
        list.add("Queer");
        list.add("Bicurious");

        /*Orientation*/
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        int spanCount = 3; // 3 columns
        int spacing = 5; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        filterOrientationAdapter adapter = new filterOrientationAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void ZodicList(RecyclerView zodiacRecyclerView) {
        ArrayList<Zodiac_filter_model> arrayList = new ArrayList<Zodiac_filter_model>();
        arrayList.clear();
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_aries, "Aries"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_taurus, "Taurus"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_gemini, "Gemini"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_cancer, "Cancer"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_leo, "Leo"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_virgo, "Virgo"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_libra, "Libra"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_scorpio, "Scorpio"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_sagittarius, "Saggitarius"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_capricorn, "Capricorn"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_aquarius, "Aquarius"));
        arrayList.add(new Zodiac_filter_model(R.drawable.ic_pisces, "Pisces"));

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        zodiacRecyclerView.setHasFixedSize(true);
        zodiacRecyclerView.setLayoutManager(layoutManager1);
        zodiacRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ZodiacFilterAdapter adapter = new ZodiacFilterAdapter(getContext(), arrayList, this);
        zodiacRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) binding.recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    public void deleteSeenUser() {

        SpeedChatDeleteBody speedChatDeleteBody = new SpeedChatDeleteBody(userId);
        presenter.DeleteSeenUser(getContext(), speedChatDeleteBody);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        deleteSeenUser();

    }
}