package com.dating.klicked.Fragment.Profile;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dating.klicked.Authentication.Add_Image;
import com.dating.klicked.Fragment.Adapter.RewardsRedeemAdapter;
import com.dating.klicked.Fragment.Adapter.SpecificFeaturesAdapter;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.AddImagePresenter;
import com.dating.klicked.ViewPresenter.CardPresenter;
import com.dating.klicked.ViewPresenter.SpecificFeaturesPresenter;
import com.dating.klicked.databinding.FragmentSpecificFeaturesBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.GridSpacingItemDecoration;
import com.dating.klicked.utils.ImagePath;
import com.irozon.sneaker.Sneaker;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class SpecificFeaturesFragment extends Fragment implements SpecificFeaturesPresenter.SpecificFeaturesView, SpecificFeaturesAdapter.onSpecificFeatureClicked {
    FragmentSpecificFeaturesBinding binding;
    private Dialog dialog, dialogBox;
    private View view;
    SpecificFeaturesPresenter presenter;
    User_Data userData;
    public boolean permissionStatus;
    private int PICK_PHOTO_FOR_AVATAR = 1, position;
    File pro_file;

    public SpecificFeaturesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_specific_features, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        presenter = new SpecificFeaturesPresenter(this);
        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();
        presenter.GetProfile(getContext());


        return binding.getRoot();
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
       {
            Sneaker.with(getActivity())
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
        }
    }

    @Override
    public void onGetProfileSuccess(UserProfileResponse userProfileResponse, String message) {
        List<UserProfileResponse.SpecialFeature> specialFeatures = new ArrayList<UserProfileResponse.SpecialFeature>();
        List<Integer> position = new ArrayList<Integer>();
        specialFeatures.clear();
        if (message.equalsIgnoreCase("ok") && userProfileResponse.getSpecialFeature().size() > 0) {
            for (int i = 0; i < userProfileResponse.getSpecialFeature().size(); i++) {
                UserProfileResponse.SpecialFeature feature = new UserProfileResponse.SpecialFeature();
                feature.setId(userProfileResponse.getSpecialFeature().get(i).getId());
                feature.setImage(userProfileResponse.getSpecialFeature().get(i).getImage());
                feature.setPosition(userProfileResponse.getSpecialFeature().get(i).getPosition());
                position.add(userProfileResponse.getSpecialFeature().get(i).getPosition());
                specialFeatures.add(feature);
            }

            for (int i = 0; i <= 3; i++) {
                if (!position.contains(i)) {
                    UserProfileResponse.SpecialFeature feature = new UserProfileResponse.SpecialFeature();
                    feature.setId(null);
                    feature.setImage(null);
                    feature.setPosition(i);
                    specialFeatures.add(feature);
                }
            }


        } else {
            for (int i = 0; i < 4; i++) {
                UserProfileResponse.SpecialFeature feature = new UserProfileResponse.SpecialFeature();
                feature.setId(null);
                feature.setImage(null);
                feature.setPosition((i));
                specialFeatures.add(feature);
            }

        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        int spanCount = 2; // 3 columns
        int spacing = 40; // 50px
        boolean includeEdge = false;
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(new SpecificFeaturesAdapter(getContext(), specialFeatures, this));


    }

    @Override
    public void onUploadspecialFeatureImageSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully Uploaded Your Profile Image ")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();

            presenter.GetProfile(getContext());

        }
    }

    @Override
    public void onDeleteSpecialFeature(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("SuccessFully Delete Profile image")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();
            presenter.GetProfile(getContext());
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
    public void addSpecificFeatureClickedListener(int positions) {
        position = positions;
        //  Log.d("Dataaaa", " " + position);
        galleryPicker();

    }

    @Override
    public void DeleteSpecificFeatureClickedListener(String id) {

        presenter.DeleteSpecialFeature(getContext(), id, userData.getPhoneNo());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case AppUtils.PERMISSION_REQUEST_CODE:
                Log.d("lengthfffff", String.valueOf(grantResults.length));
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionStatus = true;

                } else if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
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

    private void galleryPicker() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK && data != null) {

            if (data == null)
                return;
            Uri uri = data.getData();


            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            String mediaPath1 = cursor.getString(columnIndex);


            if (mediaPath1 != null && !mediaPath1.equals("")) {
                pro_file = new File(mediaPath1);
                uploadSpecialFeatureImage(pro_file);
            }


            cursor.close();
        }


    }

    private void uploadSpecialFeatureImage(File file) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part profileImage =
                MultipartBody.Part.createFormData("specialFeature", file.getName(), requestFile);
        RequestBody phoneNo =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), userData.getPhoneNo());

        RequestBody CheckImage =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), String.valueOf(position));


        presenter.uploadSpecialFeatureImage(getContext(), profileImage, phoneNo, CheckImage);

    }


}