package com.dating.klicked.Fragment.Profile;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Fragment.Adapter.ProfileAdapter;
import com.dating.klicked.Fragment.SideMenu.PlanDialog;
import com.dating.klicked.Model.ResponseRepo.KlickedResponse;
import com.dating.klicked.Model.ResponseRepo.ViewedProfiledResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.ProfilePresenter;
import com.dating.klicked.databinding.FragmentProfileBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.ImagePath;
import com.irozon.sneaker.Sneaker;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ProfileFragment extends Fragment implements ProfileAdapter.OnProfileClicked, ProfilePresenter.ProfileView, View.OnClickListener {
    FragmentProfileBinding binding;
    private Context context;
    private View view;
    NavController navController;
    Activity mActivity;
    User_Data userData;
    ProfilePresenter presenter;
    File doc_file;
    public boolean permissionStatus;
    private Dialog dialogBox, dialog;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //   return inflater.inflate(R.layout.fragment_profile, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();
        Glide.with(this).load(PrefConf.IMAGE_URL + userData.getProfileImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().circleCrop())
                .into(binding.imgProfile);
        binding.name.setText(userData.getUserName());
        binding.txtOccuption.setText(userData.getOccupation());
        presenter = new ProfilePresenter(this);
        binding.imgUpload.setOnClickListener(this);
        binding.imgShare.setOnClickListener(this);
        presenter.GetKlickedUserSuccess(getContext());
        presenter.ViewProfile(getContext());

        setData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }


    public void setData() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Preview your profile");
        list.add("About");
        list.add("Card");
        list.add("Profiles Pictures");
        list.add("Audio Description");
        list.add("Rewards");
        list.add("Matches");
        list.add("Profile viewed");
        list.add("Select Plan");
        list.add("Boost your profile");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(new ProfileAdapter(getContext(), list, this));

    }

    @Override
    public void onProfileClickedListener(int position, ArrayList<String> list) {
        switch (list.get(position)) {

            case "Preview your profile":
                navController.navigate(R.id.action_Profile_Fragment_to_preview_your_profile);
                break;

            case "About":
                navController.navigate(R.id.action_Profile_Fragment_to_about);
                break;

            case "Card":
                navController.navigate(R.id.action_Profile_Fragment_to_cardFragment);
                break;

            case "Profiles Pictures":
                navController.navigate(R.id.action_Profile_Fragment_to_specificFeaturesFragment);
                break;

            case "Audio Description":
                navController.navigate(R.id.action_Profile_Fragment_to_musicDespriction);
                break;

            case "Rewards":
                navController.navigate(R.id.action_Profile_Fragment_to_rewardsFragment);
                break;

            case "Matches":
                navController.navigate(R.id.action_Profile_Fragment_to_matches);
                break;

            case "Profile viewed":
                navController.navigate(R.id.action_Profile_Fragment_to_profile_ViewedFragment);
                break;

            case "Select Plan":
                final PlanDialog planDialog = new PlanDialog(mActivity);

                planDialog.startLoadingdialog();

                break;

            case "Boost your profile":
                navController.navigate(R.id.action_Profile_Fragment_to_boostProfile_Fragment);
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
        Sneaker.with(getActivity())
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
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
                        .into(binding.imgProfile);

                User_Data user_data1 = new User_Data(userData.getId(),
                        userData.getEmail(),
                        userData.getToken(),
                        userData.getReferral_code(),
                        userData.getUserName(),
                        userData.getPhoneNo(),
                        userData.getDOB(),
                        userData.getGender(),
                        profileImg,
                        userData.getOccupation());
                SharedPrefManager.getInstance(getContext()).SetLoginData(user_data1);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onGetKlickedUserSuccess(KlickedResponse klickedResponse, String message) {
        if (message.equalsIgnoreCase("ok") && klickedResponse != null
                && klickedResponse.getUsers() != null && klickedResponse.getUsers().size() > 0) {

            binding.txtMatch.setText("" + klickedResponse.getUsers().size());
        } else {
            binding.txtMatch.setText("00");

        }

    }

    @Override
    public void onGetViewedProfileSuccess(List<ViewedProfiledResponse> list, String message) {
        if (message.equalsIgnoreCase("ok") && list.size() > 0) {
            if (list.get(0).getUsers() != null && list.get(0).getUsers().size() > 0) {

                binding.txtViwed.setText("" + list.get(0).getUsers().size());
            } else {
                binding.txtViwed.setText("00");
            }
        } else {
            binding.txtViwed.setText("00");

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

            case R.id.img_upload:
                //CropImage.activity().start(getActivity());
                CropImage.activity()
                        .setMaxCropResultSize(400, 200)
                        .setMinCropResultSize(400, 200)
                        .setAutoZoomEnabled(false)
                        .start(getActivity());
                break;

            case R.id.img_share:
                /*Sneaker.with(getActivity())
                        .setTitle("Coming soon")
                        .setMessage("")
                        .setCornerRadius(4)
                        .setDuration(1500)
                        .sneakSuccess();*/
                AppUtils.shareApp(getContext(),userData.getReferral_code());
                break;

        }
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialogBox.dismiss();
            }
        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (result == null)
                return;
            Uri uri = result.getUri();
            String path = ImagePath.getPath(getContext(), uri);

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
                        MediaType.parse("multipart/form-data"), userData.getPhoneNo());


        presenter.uploadProfileImage(context, profileImage, phoneNo);

    }
}