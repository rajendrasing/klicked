package com.dating.klicked.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dating.klicked.MainActivity;
import com.dating.klicked.Model.RequestRepo.VerifyOTP_Body;
import com.dating.klicked.Model.ResponseRepo.UserResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.AddImagePresenter;
import com.dating.klicked.databinding.ActivityAddImageBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.ImagePath;
import com.irozon.sneaker.Sneaker;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class Add_Image extends AppCompatActivity implements View.OnClickListener, AddImagePresenter.AddImageView {
    ActivityAddImageBinding binding;
    private View view;
    private Context context;
    private Dialog dialog;
    public boolean permissionStatus;
    private int PICK_PHOTO_FOR_AVATAR = 1, checkImage;
    private Dialog dialogBox;
    String PhoneNo, otp;
    AddImagePresenter presenter;
    File pro_file, sp_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_image);
        view = binding.getRoot();
        context = Add_Image.this;

        presenter = new AddImagePresenter(this);

        dialog = AppUtils.hideShowProgress(context);
        PhoneNo = MyPreferences.getInstance(context).getString(PrefConf.USER_NUMBER, null);
        otp = MyPreferences.getInstance(context).getString(PrefConf.USER_OTP, null);
        binding.imgProfile.setOnClickListener(this);
        binding.txtBack.setOnClickListener(this);
        binding.img1.setOnClickListener(this);
        binding.img2.setOnClickListener(this);
        binding.img3.setOnClickListener(this);
        binding.img4.setOnClickListener(this);
        binding.txtNext.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.txt_back:
                onBackPressed();
                break;

            case R.id.img_profile:
                // galleryPicker();
                CropImage.activity()
                        .setMaxCropResultSize(400, 200)
                        .setMinCropResultSize(400, 200)
                        .setAutoZoomEnabled(false)
                        .start(Add_Image.this);
                break;

            case R.id.img1:
                checkImage = 0;
                //   CropImage.activity().start(Add_Image.this);
                galleryPicker();
                break;

            case R.id.img2:
                checkImage = 1;
                // CropImage.activity().start(Add_Image.this);
                galleryPicker();
                break;

            case R.id.img3:
                checkImage = 2;
                galleryPicker();
                //CropImage.activity().start(Add_Image.this);
                break;

            case R.id.img4:
                checkImage = 3;
                galleryPicker();
                //CropImage.activity().start(Add_Image.this);
                break;

            case R.id.txt_next:
                if (pro_file == null) {
                    Sneaker.with(Add_Image.this)
                            .setTitle("please upload Special Feature  image")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakWarning();
                } else if (sp_file == null) {
                    Sneaker.with(Add_Image.this)
                            .setTitle("please Upload at least one profile Image")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakWarning();
                } else {
                    VerifyOTP_Body body = new VerifyOTP_Body(PhoneNo, otp);
                    presenter.UserLogin(context, body);
                }

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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK && data != null) {
            //the image URI

            if (data == null)
                return;
            Uri uri = data.getData();


            String[] filePathColumn = {MediaStore.Audio.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            String mediaPath1 = cursor.getString(columnIndex);


            if (mediaPath1 != null && !mediaPath1.equals("")) {
                sp_file = new File(mediaPath1);
                uploadSpecialFeatureImage(sp_file);
            }

            if (checkImage == 0) {
                Glide.with(Add_Image.this).load(uri).into(binding.img1);
            } else if (checkImage == 1) {
                Glide.with(Add_Image.this).load(uri).into(binding.img2);
            } else if (checkImage == 2) {
                Glide.with(Add_Image.this).load(uri).into(binding.img3);
            } else if (checkImage == 3) {
                Glide.with(Add_Image.this).load(uri).into(binding.img4);
            }


            cursor.close();
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (result == null)
                return;
            Uri uri = result.getUri();
            String path = ImagePath.getPath(Add_Image.this, uri);

            if (path != null && !path.equals("")) {

                pro_file = new File(path);

                uploadImage(pro_file);
            }


        }


    }

    private void uploadImage(File file) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part profileImage =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody phoneNo =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), PhoneNo);


        presenter.uploadProfileImage(context, profileImage, phoneNo);

    }

    private void uploadSpecialFeatureImage(File file) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part profileImage =
                MultipartBody.Part.createFormData("specialFeature", file.getName(), requestFile);
        RequestBody phoneNo =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), PhoneNo);

        RequestBody CheckImage =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), String.valueOf(checkImage));


        presenter.uploadSpecialFeatureImage(context, profileImage, phoneNo, CheckImage);

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
        Sneaker.with(Add_Image.this)
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onUploadProfileImageSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            try {
                String s = responseBody.string();
                JSONObject jsonObject = new JSONObject(s);
                String result = jsonObject.getString("result");
                JSONObject jsonObject1 = new JSONObject(result);
                String profile = jsonObject1.getString("profileImage");

                Glide.with(context).load(PrefConf.IMAGE_URL + profile).into(binding.imgProfile);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onUploadspecialFeatureImageSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(Add_Image.this)
                    .setTitle("Successfully Uploaded Your Profile Image ")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();

        }
    }

    @Override
    public void onUserLoginSuccess(UserResponse responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {

            User_Data user_data = new User_Data(responseBody.getResult().getId(),
                    responseBody.getResult().getEmail(),
                    responseBody.getToken(),
                    responseBody.getResult().getMyReferalcode(),
                    responseBody.getResult().getFirstName(),
                    responseBody.getResult().getPhone(),
                    responseBody.getResult().getDob(),
                    responseBody.getResult().getGender(),
                    responseBody.getResult().getProfileImage(),
                    responseBody.getResult().getOccuptation());

            SharedPrefManager.getInstance(Add_Image.this).SetLoginData(user_data);
            startActivity(new Intent(Add_Image.this, MainActivity.class));
            finish();
            MyPreferences.getInstance(Add_Image.this).clearPreferences();
            MyPreferences.getInstance(Add_Image.this).putString(PrefConf.Musicsong, PrefConf.IMAGE_URL + responseBody.getResult().getAudioDescription());

            Toast.makeText(context, "" + responseBody.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(Add_Image.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }
}