package com.dating.klicked;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Authentication.Add_Image;
import com.dating.klicked.Authentication.Login;
import com.dating.klicked.Fragment.SideMenu.PlanDialog;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.MainActivityPresenter;
import com.dating.klicked.databinding.ActivityMainBinding;
import com.dating.klicked.menu.DrawerAdapter;
import com.dating.klicked.menu.DrawerItem;
import com.dating.klicked.menu.SimpleItem;
import com.dating.klicked.menu.SpaceItem;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.ImagePath;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.irozon.sneaker.Sneaker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragStateListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, Function1<MeowBottomNavigation.Model, Unit>, View.OnClickListener, MainActivityPresenter.MainActivityView {
    ActivityMainBinding binding;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    private AppUtils psDialogMsg;


    private static final int POS_PROFILE = 0;
    private static final int POS_PLAN = 1;
    private static final int POS_REWARDS = 2;
    private static final int POS_HOSTELS = 3;
    private static final int POS_SPEEDCHAT_HISTORY = 4;
    private static final int POS_HINTS = 5;
    private static final int POS_FAQ = 6;
    private static final int POS_BOOST = 7;
    private static final int POS_FEEDBACK = 8;
    private static final int POS_DEACTIVATE = 9;
    private static final int POS_BLOCK = 10;
    private static final int POS_PRIVACY_POLICY=11;
    private static final int POS_LOGOUT = 12;


    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    User_Data user_data;
    ImageView profile_img, profile_upload;
    TextView nav_name, nav_occupation;
    private MainActivityPresenter presenter;
    private Context context;
    public static Socket mSocket;
    private int PICK_PHOTO_FOR_AVATAR = 1;
    File doc_file;
    public boolean permissionStatus;


    private Dialog dialogBox, dialog, deactivate_dialog;
    final PlanDialog planDialog = new PlanDialog(MainActivity.this);


    {
        try {
            mSocket = IO.socket("https://api.klicked.co");


        } catch (URISyntaxException e) {
        }
    }
    // private MeowBottomNavigation bnv_Main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        user_data = SharedPrefManager.getInstance(MainActivity.this).getLoginDATA();

        context = MainActivity.this;
        mSocket.connect();

        presenter = new MainActivityPresenter(this);
        presenter.OnlineUser(context);

        dialog = AppUtils.hideShowProgress(MainActivity.this);


        // bottom menu
        navController = Navigation.findNavController(this, R.id.main);
        appBarConfiguration = new AppBarConfiguration.Builder(new int[]{R.id.home_fragment, R.id.favorites_fragment, R.id.speedDating_Fragment, R.id.klicksFragment, R.id.chat_Fragment, R.id.klickedList})
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        // NavigationUI.setupWithNavController(binding.bnvMain, navController);


        binding.bnvMain.add(new MeowBottomNavigation.Model(1, R.drawable.ic_noun_home));
        binding.bnvMain.add(new MeowBottomNavigation.Model(2, R.drawable.ic_icon_favorites));
        binding.bnvMain.add(new MeowBottomNavigation.Model(3, R.drawable.ic_noun_speed));
        binding.bnvMain.add(new MeowBottomNavigation.Model(4, R.drawable.ic_icon_klicked));
        binding.bnvMain.add(new MeowBottomNavigation.Model(5, R.drawable.ic_icon_chats));
        binding.bnvMain.show(1, true);
        binding.bnvMain.setOnClickMenuListener(this);


        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(binding.toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .addDragStateListener(new DragStateListener() {
                    @Override
                    public void onDragStart() {

                    }

                    @Override
                    public void onDragEnd(boolean isMenuOpened) {
                        if (isMenuOpened) {
                            binding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));


                        } else {
                            binding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_icon_menu));

                        }

                    }
                })
                .inject();
        binding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_icon_menu));
        binding.back.setOnClickListener(this);
        binding.profileImg.setOnClickListener(this);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.feedbackFragment || destination.getId() == R.id.add_feedback
                        || destination.getId() == R.id.faqFragment || destination.getId() == R.id.add_Faq_Fragment
                        || destination.getId() == R.id.profile_ViewedFragment || destination.getId() == R.id.cardFragment
                        || destination.getId() == R.id.rewardRedeemFragment || destination.getId() == R.id.hintsFragment
                        || destination.getId() == R.id.subHintsFragment || destination.getId() == R.id.fullHintsFragment
                        || destination.getId() == R.id.specificFeaturesFragment || destination.getId() == R.id.profile_Info_Fragment
                        || destination.getId() == R.id.speedDatingChatHistory || destination.getId() == R.id.matches || destination.getId() == R.id.rewardsDetailsFragment
                        || destination.getId() == R.id.redeemGiftFragment || destination.getId() == R.id.redeemHistory || destination.getId() == R.id.redeemHistoryDetails
                        || destination.getId() == R.id.blockedUserListFragment || destination.getId() == R.id.musicDespriction || destination.getId() == R.id.about
                        || destination.getId() == R.id.preview_your_profile || destination.getId() == R.id.hotelFragment || destination.getId() == R.id.hotelInfoFragment||destination.getId()==R.id.privacyPolicyFragment) {
                    binding.bnvMain.setVisibility(View.GONE);
                    binding.linear.setVisibility(View.GONE);
                    binding.toolbar.setVisibility(View.GONE);
                    binding.back.setVisibility(View.VISIBLE);
                    binding.profileImg.setVisibility(View.VISIBLE);
                    binding.appBar.setVisibility(View.VISIBLE);

                } else if (destination.getId() == R.id.Profile_Fragment || destination.getId() == R.id.boostProfile_Fragment
                        || destination.getId() == R.id.BoostFaq) {
                    binding.linear.setVisibility(View.GONE);
                    binding.toolbar.setVisibility(View.GONE);
                    binding.back.setVisibility(View.VISIBLE);
                    binding.profileImg.setVisibility(View.GONE);
                    binding.bnvMain.setVisibility(View.GONE);
                    binding.appBar.setVisibility(View.VISIBLE);

                } else if (destination.getId() == R.id.rewardsFragment) {
                    binding.linear.setVisibility(View.GONE);
                    binding.toolbar.setVisibility(View.GONE);
                    binding.back.setVisibility(View.VISIBLE);
                    binding.profileImg.setVisibility(View.GONE);
                    binding.bnvMain.setVisibility(View.GONE);
                    binding.appBar.setVisibility(View.GONE);

                } else if (destination.getId() == R.id.userChat_Fragment) {
                    binding.linear.setVisibility(View.GONE);
                    binding.toolbar.setVisibility(View.GONE);
                    binding.back.setVisibility(View.VISIBLE);
                    binding.profileImg.setVisibility(View.GONE);
                    binding.bnvMain.setVisibility(View.GONE);
                    binding.appBar.setVisibility(View.GONE);

                } else {
                    binding.bnvMain.setVisibility(View.VISIBLE);
                    binding.linear.setVisibility(View.VISIBLE);
                    binding.toolbar.setVisibility(View.VISIBLE);
                    binding.back.setVisibility(View.GONE);
                    binding.profileImg.setVisibility(View.VISIBLE);
                    binding.appBar.setVisibility(View.VISIBLE);
                    binding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_icon_menu));

                }
            }
        });

     /*   Intent appLinkIntent = getIntent();
        Uri appLinkData = appLinkIntent.getData();

        if (appLinkData != null) {
            String UserId = appLinkData.getLastPathSegment();
            Bundle bundle = new Bundle();
            bundle.putString("UserId", UserId);
            bundle.putString("Type", "adduser");
            bundle.putString("docId", null);
            bundle.putBoolean("fav", false);
            bundle.putBoolean("sendRequest", false);
            navController.navigate(R.id.profile_Info_Fragment, bundle);
        }*/


        handleIntent(getIntent());
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_PROFILE).setChecked(false),
                createItemFor(POS_PLAN),
                createItemFor(POS_REWARDS),
                createItemFor(POS_HOSTELS),
                createItemFor(POS_SPEEDCHAT_HISTORY),
                createItemFor(POS_HINTS),
                createItemFor(POS_FAQ),
                createItemFor(POS_BOOST),
                createItemFor(POS_FEEDBACK),
                createItemFor(POS_DEACTIVATE),
                createItemFor(POS_BLOCK),
                createItemFor(POS_PRIVACY_POLICY),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        profile_img = (ImageView) findViewById(R.id.profile_img1);
        TextView nav_name = (TextView) findViewById(R.id.nav_name1);
        TextView nav_occupation = (TextView) findViewById(R.id.nav_occupation1);
        profile_upload = (ImageView) findViewById(R.id.img_upload);

        nav_name.setText(user_data.getUserName());
        nav_occupation.setText(user_data.getOccupation());
        profile_upload.setOnClickListener(this);

        if (user_data.getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {
            binding.profileImg.setImageDrawable(getResources().getDrawable(R.mipmap.profile1));
            profile_img.setImageDrawable(getResources().getDrawable(R.mipmap.profile1));

        } else {
            Glide.with(this).load(PrefConf.IMAGE_URL + user_data.getProfileImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().circleCrop())
                    .into(binding.profileImg);

            Glide.with(this).load(PrefConf.IMAGE_URL + user_data.getProfileImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().circleCrop())
                    .into(profile_img);
        }


        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        //  adapter.setSelected(POS_PROFILE);


    }


    @Override
    public void onItemSelected(int position) {
        switch (position) {
            case POS_PROFILE:

                navController.navigate(R.id.profile_ViewedFragment);
                break;

            case POS_PLAN:

                planDialog.startLoadingdialog();
                break;

            case POS_REWARDS:
                navController.navigate(R.id.rewardsFragment);
                break;

            case POS_HOSTELS:
                navController.navigate(R.id.hotelFragment);
                break;
            case POS_SPEEDCHAT_HISTORY:
                navController.navigate(R.id.speedDatingChatHistory);
                break;

            case POS_HINTS:
                navController.navigate(R.id.hintsFragment);
                break;

            case POS_FAQ:
                navController.navigate(R.id.faqFragment);
                break;

            case POS_BOOST:
                navController.navigate(R.id.boostProfile_Fragment);
                break;

            case POS_FEEDBACK:
                navController.navigate(R.id.feedbackFragment);
                break;

            case POS_DEACTIVATE:
                DeactivateDialog(MainActivity.this);
                break;

            case POS_BLOCK:
                navController.navigate(R.id.blockedUserListFragment);
                break;

            case POS_LOGOUT:

                String tittle = getBaseContext().getString(R.string.edit_setting__logout);
                String des = getBaseContext().getString(R.string.edit_setting__logout_question);
                String okStr = getBaseContext().getString(R.string.app__ok);
                String cancelStr = getBaseContext().getString(R.string.app__cancel);
                psDialogMsg = new AppUtils(MainActivity.this, false);
                psDialogMsg.showAppLogoutDialog(tittle, des, okStr, cancelStr);
                psDialogMsg.show();

                psDialogMsg.okButton.setOnClickListener(view -> {
                    SharedPrefManager.getInstance(MainActivity.this).logout();
                    MyPreferences.getInstance(MainActivity.this).clearPreferences();
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    prefs.edit().clear().commit();

                    psDialogMsg.cancel();
                    finish();
                    // System.exit(0);
                    Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                });
                psDialogMsg.cancelButton.setOnClickListener(view -> psDialogMsg.cancel());

                break;
            case POS_PRIVACY_POLICY:
                navController.navigate(R.id.privacyPolicyFragment);
                break;
        }

        slidingRootNav.closeMenu();
    }


    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withSelectedIconTint(color(R.color.white))
                .withSelectedTextTint(color(R.color.white));

    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onBackPressed() {

        if (slidingRootNav.isMenuOpened() == true) {

            slidingRootNav.closeMenu();
        } else {
            if (navController.getCurrentDestination().getLabel().equals("Favorites")
                    || navController.getCurrentDestination().getLabel().equals("Speed Dating")
                    || navController.getCurrentDestination().getLabel().equals("Klicks")
                    || navController.getCurrentDestination().getLabel().equals("Chats")) {
                binding.txtHome.setVisibility(View.VISIBLE);
                binding.txtFavorites.setVisibility(View.INVISIBLE);
                binding.txtSpeedDating.setVisibility(View.INVISIBLE);
                binding.txtKlicks.setVisibility(View.INVISIBLE);
                binding.txtChat.setVisibility(View.INVISIBLE);
                binding.bnvMain.show(1, true);
                navController.navigate(R.id.home_fragment);
            } else if (navController.getCurrentDestination().getLabel().equals("Home")) {
                String tittle = getBaseContext().getString(R.string.exit);
                String des = getBaseContext().getString(R.string.app_exit);
                String okStr = getBaseContext().getString(R.string.app__yes);
                String cancelStr = getBaseContext().getString(R.string.app__no);
                psDialogMsg = new AppUtils(MainActivity.this, false);
                psDialogMsg.showAppLogoutDialog(tittle, des, okStr, cancelStr);
                psDialogMsg.show();

                psDialogMsg.okButton.setOnClickListener(view -> {
                    psDialogMsg.cancel();
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    }
                    finish();


                });
                psDialogMsg.cancelButton.setOnClickListener(view -> psDialogMsg.cancel());

            } else {

                super.onBackPressed();

            }
        }


    }

    @Override
    public Unit invoke(MeowBottomNavigation.Model model) {
        switch (model.getId()) {
            case 1:
                navController.navigate(R.id.home_fragment);
                binding.txtHome.setVisibility(View.VISIBLE);
                binding.txtFavorites.setVisibility(View.INVISIBLE);
                binding.txtSpeedDating.setVisibility(View.INVISIBLE);
                binding.txtKlicks.setVisibility(View.INVISIBLE);
                binding.txtChat.setVisibility(View.INVISIBLE);
                break;

            case 2:
                navController.navigate(R.id.favorites_fragment);
                binding.txtHome.setVisibility(View.INVISIBLE);
                binding.txtFavorites.setVisibility(View.VISIBLE);
                binding.txtSpeedDating.setVisibility(View.INVISIBLE);
                binding.txtKlicks.setVisibility(View.INVISIBLE);
                binding.txtChat.setVisibility(View.INVISIBLE);
                break;

            case 3:
                navController.navigate(R.id.speedDating_Fragment);
                binding.txtHome.setVisibility(View.INVISIBLE);
                binding.txtFavorites.setVisibility(View.INVISIBLE);
                binding.txtSpeedDating.setVisibility(View.VISIBLE);
                binding.txtKlicks.setVisibility(View.INVISIBLE);
                binding.txtChat.setVisibility(View.INVISIBLE);
                break;

            case 4:
                navController.navigate(R.id.klicksFragment);
                binding.txtHome.setVisibility(View.INVISIBLE);
                binding.txtFavorites.setVisibility(View.INVISIBLE);
                binding.txtSpeedDating.setVisibility(View.INVISIBLE);
                binding.txtKlicks.setVisibility(View.VISIBLE);
                binding.txtChat.setVisibility(View.INVISIBLE);
                break;

            case 5:
                navController.navigate(R.id.chat_Fragment);
                binding.txtHome.setVisibility(View.INVISIBLE);
                binding.txtFavorites.setVisibility(View.INVISIBLE);
                binding.txtSpeedDating.setVisibility(View.INVISIBLE);
                binding.txtKlicks.setVisibility(View.INVISIBLE);
                binding.txtChat.setVisibility(View.VISIBLE);
                break;

        }
        return null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.back:
                onBackPressed();
                break;

            case R.id.profile_img:
                navController.navigate(R.id.Profile_Fragment);
                break;


            case R.id.img_upload:
                // galleryPicker();
                CropImage.activity()
                        .setMaxCropResultSize(400, 200)
                        .setMinCropResultSize(400, 200)
                        .setAutoZoomEnabled(false)
                        .start(MainActivity.this);

                break;

        }
    }

    @Override
    public void showHideProgress(boolean show) {
        if (show) {
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
        Sneaker.with(MainActivity.this)
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onOnlineUserSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            /*Sneaker.with(MainActivity.this)
                    .setTitle("Come Online")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();*/


            JSONObject message1 = new JSONObject();
            try {
                message1.put("userId", user_data.getId());
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

            Log.d("messageIdddd", message1.toString());
            MainActivity.mSocket.emit("checkOnline", message1);

        }
    }

    @Override
    public void onOfflineSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {

            JSONObject message1 = new JSONObject();
            try {
                message1.put("userId", user_data.getId());
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

            Log.d("messageIdddd", message1.toString());
            MainActivity.mSocket.emit("checkOnline", message1);

            //    Toast.makeText(getApplicationContext(), "Go Offline", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUploadspecialFeatureImageSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok") && responseBody != null) {
            try {
                String response = responseBody.string();
                JSONObject jsonObject = new JSONObject(response);
                String result = jsonObject.getString("result");
                JSONObject jsonObject1 = new JSONObject(result);
                String profileImg = jsonObject1.getString("profileImage");
                Glide.with(this).load(PrefConf.IMAGE_URL + profileImg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().circleCrop())
                        .into(binding.profileImg);

                Glide.with(this).load(PrefConf.IMAGE_URL + profileImg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().circleCrop())
                        .into(profile_img);
                User_Data user_data1 = new User_Data(user_data.getId(),
                        user_data.getEmail(),
                        user_data.getToken(),
                        user_data.getReferral_code(),
                        user_data.getUserName(),
                        user_data.getPhoneNo(),
                        user_data.getDOB(),
                        user_data.getGender(),
                        profileImg,
                        user_data.getOccupation());
                SharedPrefManager.getInstance(MainActivity.this).SetLoginData(user_data1);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onDeactivateAccountSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            SharedPrefManager.getInstance(MainActivity.this).logout();
            MyPreferences.getInstance(MainActivity.this).clearPreferences();
            finish();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(MainActivity.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //   presenter.OfflineUser(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.OnlineUser(context);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("nkldl", "LMKLM");
        handleIntent(getIntent());
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.OfflineUser(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unbound background audio service when activity is destroyed.
        //  unBoundAudioService();
        presenter.OfflineUser(context);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case AppUtils.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionStatus = true;

                } else {
                    permissionStatus = false;
                    String msg = "Please Allow Permission to share.";
                    customAlert(msg);

                }
                return;
        }
    }

    private void customAlert(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialogBox.dismiss();
            }
        }).show();
    }

    private void galleryPicker() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            startActivityForResult(galleryIntent, PICK_PHOTO_FOR_AVATAR);
        } else {
            Intent intent;
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (result == null)
                return;
            Uri uri = result.getUri();
            String path = ImagePath.getPath(MainActivity.this, uri);

            if (path != null && !path.equals("")) {

                doc_file = new File(path);

                uploadImage(doc_file);
            }


        }
    }

    private void uploadImage(File file) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part profileImage =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody phoneNo =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), user_data.getPhoneNo());


        presenter.uploadProfileImage(context, profileImage, phoneNo);

    }

    public void DeactivateDialog(Activity activity) {
        deactivate_dialog = new Dialog(activity);


        deactivate_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deactivate_dialog.setCancelable(false);
        deactivate_dialog.setContentView(R.layout.custom_deactivate_dialog_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();


        lp.copyFrom(deactivate_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        deactivate_dialog.getWindow().setAttributes(lp);
        deactivate_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView okButton = deactivate_dialog.findViewById(R.id.txt_report);
        TextView cancelButton = deactivate_dialog.findViewById(R.id.txt_cancel);
        RadioGroup radioGroup = deactivate_dialog.findViewById(R.id.radio_group);

        cancelButton.setOnClickListener(view1 -> deactivate_dialog.dismiss());

        okButton.setOnClickListener(view1 -> {
            RadioButton rb = (RadioButton) deactivate_dialog.findViewById(radioGroup.getCheckedRadioButtonId());
            String reportText = rb.getText().toString();
            presenter.DeactivateAccount(MainActivity.this, reportText);

            // Toast.makeText(MainActivity.this, "" + reportText, Toast.LENGTH_SHORT).show();


        });

        deactivate_dialog.show();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Intent appLinkIntent = getIntent();
        Uri appLinkData = appLinkIntent.getData();
        // Toast.makeText(getApplicationContext(), ""+appLinkData, Toast.LENGTH_SHORT).show();
        if (appLinkData != null) {
            String UserId = appLinkData.getLastPathSegment();
            Bundle bundle = new Bundle();
            bundle.putString("UserId", UserId);
            bundle.putString("Type", "adduser");
            bundle.putString("docId", null);
            bundle.putBoolean("fav", false);
            bundle.putBoolean("sendRequest", false);
            navController.navigate(R.id.profile_Info_Fragment, bundle);
        }
    }


}

