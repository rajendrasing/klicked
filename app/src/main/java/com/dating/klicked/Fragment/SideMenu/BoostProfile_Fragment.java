package com.dating.klicked.Fragment.SideMenu;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dating.klicked.Authentication.Add_Image;
import com.dating.klicked.Authentication.Audio_Des;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.ViewPresenter.BoostProfilePresenter;
import com.dating.klicked.databinding.FragmentBoostProfieBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.ImagePath;
import com.dating.klicked.utils.Validation;
import com.irozon.sneaker.Sneaker;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class BoostProfile_Fragment extends Fragment implements View.OnClickListener, BoostProfilePresenter.BoostProfileView {
    private FragmentBoostProfieBinding binding;
    String[] documentType = {"Select Document", "Aadhaar Card", "Driving License", "Voter ID card", "Passport"};
    private Dialog dialog;
    private View view;
    String documentName;
    private int PICK_PHOTO_FOR_AVATAR = 1;
    File doc_file;
    public boolean permissionStatus;
    private Dialog dialogBox;
    long fileSizeInKB;
    BoostProfilePresenter presenter;
    NavController navController;


    public BoostProfile_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_boost_profie, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        setData();

        presenter = new BoostProfilePresenter(this);
        presenter.GetProfile(getContext());

        binding.txtUploadDoc.setOnClickListener(this);
        binding.txtNext.setOnClickListener(this);
        binding.linear.setOnClickListener(this);
        binding.txtAgain.setOnClickListener(this);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }


    public void setData() {
        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.country_layout, documentType);
        // aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        binding.spinnerDoc.setAdapter(aa);

        binding.spinnerDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    TextView view1 = view.findViewById(R.id.name);
                    view1.setTextColor(getResources().getColor(R.color.text_tittle));
                    binding.txtNext.setAlpha(0.9f);
                } else {
                    binding.txtNext.setAlpha(0.3f);
                }
                documentName = parent.getItemAtPosition(position).toString();
                if (documentName.equalsIgnoreCase("Aadhaar Card")) {
                    binding.edDocNo.setText("");
                    setEditTextMaxLength(binding.edDocNo, 12);
                    binding.edDocNo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                } else {
                    binding.edDocNo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

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

    private void galleryPicker() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
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

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK && data != null) {
            //the image URI

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
                doc_file = new File(mediaPath1);
                binding.txtUploadDoc.setText(mediaPath1);
                // Get length of file in bytes
                long fileSizeInBytes = doc_file.length();
                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                fileSizeInKB = fileSizeInBytes / 1024;
                //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                // long fileSizeInMB = fileSizeInKB / 1024;
/*
                if (fileSizeInKB < 500) {
                    //  uploadAudio(file);
                } else {
                    Sneaker.with(getActivity())
                            .setTitle("File size is to large ")
                            .setMessage("maximum File size is 500KB")
                            .setCornerRadius(4)
                            .setDuration(5000)
                            .sneakError();
                }*/

                // uploadSpecialFeatureImage(sp_file);
            }

            cursor.close();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txt_upload_doc:
                galleryPicker();
                break;
            case R.id.txt_next:
                String docNo = binding.edDocNo.getText().toString().trim();
                if (documentName.equalsIgnoreCase("Select Document")) {
                    Sneaker.with(getActivity())
                            .setTitle("Please select Document")
                            .setCornerRadius(4)
                            .setDuration(5000)
                            .sneakWarning();


                } else if (docNo.isEmpty()) {
                    Sneaker.with(getActivity())
                            .setTitle("Please Enter Document Number")
                            .setCornerRadius(4)
                            .setDuration(5000)
                            .sneakWarning();
                } else if (documentName.equalsIgnoreCase("Aadhaar Card") && docNo.length()<=11) {
                    Sneaker.with(getActivity())
                            .setTitle("Please Enter Valid Aadhaar Card Number")
                            .setCornerRadius(4)
                            .setDuration(5000)
                            .sneakWarning();
                } else if (documentName.equalsIgnoreCase("Driving License") && !Validation.isValidLicenseNo(docNo)) {
                    Sneaker.with(getActivity())
                            .setTitle("Please Enter Valid Driving License Number")
                            .setCornerRadius(4)
                            .setDuration(5000)
                            .sneakWarning();
                } else if (documentName.equalsIgnoreCase("Voter ID card") && !Validation.isValidVoterIdCardNo(docNo)) {
                    Sneaker.with(getActivity())
                            .setTitle("Please Enter Valid Voter ID Card Number")
                            .setCornerRadius(4)
                            .setDuration(5000)
                            .sneakWarning();
                } else if (documentName.equalsIgnoreCase("Passport") && !Validation.isValidPassportNo(docNo)) {
                    Sneaker.with(getActivity())
                            .setTitle("Please Enter Valid Passport Number")
                            .setCornerRadius(4)
                            .setDuration(5000)
                            .sneakWarning();
                } else if (doc_file == null) {

                    Sneaker.with(getActivity())
                            .setTitle("Please Upload document Image")
                            .setCornerRadius(4)
                            .setDuration(5000)
                            .sneakWarning();

                } else if (fileSizeInKB > 2048) {
                    Sneaker.with(getActivity())
                            .setTitle("File size is to large ")
                            .setMessage("maximum File size is 500KB")
                            .setCornerRadius(4)
                            .setDuration(5000)
                            .sneakError();

                } else {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), doc_file);

                    MultipartBody.Part docImage =
                            MultipartBody.Part.createFormData("kyc", doc_file.getName(), requestFile);


                    RequestBody docno =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), docNo);
                    RequestBody docType =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), documentName);

                    presenter.BoostProfile(getContext(), docImage, docno, docType);
                }


                break;

            case R.id.linear:

                navController.navigate(R.id.BoostFaq);
                break;

            case R.id.txt_again:
                binding.txtAgain.setVisibility(View.GONE);
                binding.linearNonApply.setVisibility(View.VISIBLE);
                binding.txtNext.setVisibility(View.VISIBLE);
                binding.cardDetail.setVisibility(View.GONE);
                binding.linearKycStatus.setVisibility(View.GONE);
                break;
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
        Sneaker.with(getActivity())
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onUploadKYCSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully Upload Your document")
                    .setCornerRadius(4)
                    .setDuration(5000)
                    .sneakSuccess();
            presenter.GetProfile(getContext());
            binding.txtUploadDoc.setText("");
            binding.edDocNo.setText("");
            binding.spinnerDoc.setSelection(0);
        }

    }

    @Override
    public void onGetProfileSuccess(UserProfileResponse userProfileResponse, String message) {
        if (message.equalsIgnoreCase("ok")) {
            if (userProfileResponse.getKycVerified() != null) {
                if (userProfileResponse.getKycVerified().equalsIgnoreCase("Pending")) {
                    binding.txtAgain.setVisibility(View.GONE);
                    binding.linearNonApply.setVisibility(View.GONE);
                    binding.txtNext.setVisibility(View.GONE);
                    binding.cardDetail.setVisibility(View.VISIBLE);
                    binding.relativeDetails.setVisibility(View.VISIBLE);
                    binding.linearKycStatus.setVisibility(View.VISIBLE);
                    binding.relativeRejected.setVisibility(View.GONE);
                    binding.kycImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_panding_kyc));
                    binding.textStatus.setText(userProfileResponse.getKycVerified());
                    binding.textMessage.setText("We're working on your verification, please allow us sometime.");
                    Glide.with(getContext()).load(PrefConf.IMAGE_URL + userProfileResponse.getKyc().get(0).getImages().get(0)).into(binding.docImage);
                    binding.textDocType.setText(userProfileResponse.getKyc().get(0).getType());
                    binding.textDocno.setText(userProfileResponse.getKyc().get(0).getNumber());


                } else if (userProfileResponse.getKycVerified().equalsIgnoreCase("Rejacted") || userProfileResponse.getKycVerified().equalsIgnoreCase("Rejected")) {
                    binding.txtAgain.setVisibility(View.VISIBLE);
                    binding.linearNonApply.setVisibility(View.GONE);
                    binding.txtNext.setVisibility(View.GONE);
                    binding.cardDetail.setVisibility(View.VISIBLE);
                    binding.relativeDetails.setVisibility(View.GONE);
                    binding.linearKycStatus.setVisibility(View.VISIBLE);
                    binding.relativeRejected.setVisibility(View.VISIBLE);
                    binding.kycImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_reject_kyc));
                    binding.textStatus.setText("something went wrong");
                    binding.textMessage.setText("Sorry, We're unable to verify your account,please try again.");

                } else if (userProfileResponse.getKycVerified().equalsIgnoreCase("Done") || userProfileResponse.getKycVerified().equalsIgnoreCase("verified")) {
                    binding.txtAgain.setVisibility(View.GONE);
                    binding.linearNonApply.setVisibility(View.GONE);
                    binding.txtNext.setVisibility(View.GONE);
                    binding.cardDetail.setVisibility(View.VISIBLE);
                    binding.relativeDetails.setVisibility(View.VISIBLE);
                    binding.linearKycStatus.setVisibility(View.VISIBLE);
                    binding.relativeRejected.setVisibility(View.GONE);
                    binding.kycImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_verified_kyc));
                    binding.textStatus.setText("Congratulations");
                    binding.textMessage.setText("You are a verified user now.");
                    Glide.with(getContext()).load(PrefConf.IMAGE_URL + userProfileResponse.getKyc().get(0).getImages().get(0)).into(binding.docImage);
                    binding.textDocType.setText(userProfileResponse.getKyc().get(0).getType());
                    binding.textDocno.setText(userProfileResponse.getKyc().get(0).getNumber());

                } else if (userProfileResponse.getKycVerified().equalsIgnoreCase("Not Applied")) {
                    binding.txtAgain.setVisibility(View.GONE);
                    binding.linearNonApply.setVisibility(View.VISIBLE);
                    binding.txtNext.setVisibility(View.VISIBLE);
                    binding.cardDetail.setVisibility(View.GONE);
                    binding.linearKycStatus.setVisibility(View.GONE);
                }
            } else {
                binding.txtAgain.setVisibility(View.GONE);
                binding.linearNonApply.setVisibility(View.VISIBLE);
                binding.txtNext.setVisibility(View.VISIBLE);
                binding.cardDetail.setVisibility(View.GONE);
                binding.linearKycStatus.setVisibility(View.GONE);
            }
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

    public static void setEditTextMaxLength(EditText editText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(FilterArray);
    }
}