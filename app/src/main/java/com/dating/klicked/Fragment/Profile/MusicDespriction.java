package com.dating.klicked.Fragment.Profile;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dating.klicked.Authentication.Adapter.FavMusicAdapter;
import com.dating.klicked.Authentication.Audio_Des;
import com.dating.klicked.Authentication.EnterOtp;
import com.dating.klicked.Model.ResponseRepo.FavMusicResponse;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.CardPresenter;
import com.dating.klicked.ViewPresenter.MusicDesprictionPresenter;
import com.dating.klicked.databinding.FragmentMusicDesprictionBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class MusicDespriction extends Fragment implements MusicDesprictionPresenter.MusicDesprictionsView, View.OnClickListener, FavMusicAdapter.onFavMusicClick {
    FragmentMusicDesprictionBinding binding;
    private Dialog dialog, dialogBox;
    private View view;
    MusicDesprictionPresenter presenter;
    public MediaPlayer mediaPlayer;
    String song;
    int favMusicSize;
    User_Data userData;
    private boolean mWasGetContentIntent;
    public boolean permissionStatus;
    private int PICK_PHOTO_FOR_AVATAR = 1;
    Boolean checkFile = false;


    public MusicDespriction() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_music_despriction, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_music_despriction, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();

        mediaPlayer = new MediaPlayer();


        presenter = new MusicDesprictionPresenter(this);
        presenter.GetProfile(getContext());
        //  presenter.GetFavMusic(getContext(), userData.getPhoneNo());
        //     presenter.getAllCard(getContext());

        binding.imgPlay.setOnClickListener(this);
        binding.imgPause.setOnClickListener(this);
        binding.audioupdate.setOnClickListener(this);
        binding.musicUpdate.setOnClickListener(this);
        binding.recordUpdate.setOnClickListener(this);

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
        Sneaker.with(getActivity())
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();

    }

    @Override
    public void onGetProfileSuccess(UserProfileResponse userProfileResponse, String message) {
        if (message.equalsIgnoreCase("ok") && userProfileResponse != null && userProfileResponse.getAudioDescription() != null) {
            song = PrefConf.IMAGE_URL + userProfileResponse.getAudioDescription();

            MyPreferences.getInstance(getContext()).putString(PrefConf.Musicsong, song);

            binding.relative1.setVisibility(View.VISIBLE);
            binding.text.setText(getActivity().getResources().getString(R.string.update));
        } else {
            binding.relative1.setVisibility(View.GONE);
            binding.text.setText("Add Audio Description");
        }

    }

    @Override
    public void onAudioDesUploadSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully Uploaded Your Audio Description ")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            presenter.GetProfile(getContext());
        }
    }

    @Override
    public void onFavMusicUploadSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {

            Sneaker.with(getActivity())
                    .setTitle("Successfully Uploaded Your Music ")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            presenter.GetFavMusic(getContext(), userData.getPhoneNo());
        }
    }

    @Override
    public void onGetFavMusicSuccess(FavMusicResponse favMusicResponse, String message) {
        if (message.equalsIgnoreCase("ok") && favMusicResponse.getFavMusic() != null) {
            favMusicSize = favMusicResponse.getFavMusic().size();
            FavMusicAdapter favMusicAdapter = new FavMusicAdapter(getContext(), favMusicResponse, this, false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.favRecycler.setLayoutManager(layoutManager);
            binding.favRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.favRecycler.setHasFixedSize(true);
            binding.favRecycler.setAdapter(favMusicAdapter);
            binding.favRecycler.setVisibility(View.VISIBLE);
        } else {

            binding.favRecycler.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDeleteFavMusicSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("Successfully Deleted")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            presenter.GetFavMusic(getContext(), userData.getPhoneNo());
            if (mediaPlayer != null && mediaPlayer.isPlaying())
                mediaPlayer.pause();
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

    private void stopPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_play:
                try {
                    stopPlaying();
                    mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(song));
                    mediaPlayer.start();
                    binding.imgPause.setVisibility(View.VISIBLE);
                    binding.imgPlay.setVisibility(View.GONE);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlaying();
                            binding.imgPlay.setVisibility(View.VISIBLE);
                            binding.imgPause.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception e) {

                    Toast.makeText(view.getContext(), "Not Supported this audio", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.img_pause:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                binding.imgPlay.setVisibility(View.VISIBLE);
                binding.imgPause.setVisibility(View.GONE);
                break;

            case R.id.audioupdate:
                checkFile = false;
                galleryPicker();

                break;


            case R.id.musicUpdate:
                if (favMusicSize <= 2) {
                    checkFile = true;
                    galleryPicker();
                } else {
                    Sneaker.with(getActivity())
                            .setTitle("You can upload up to three music")
                            .setMessage("Please delete a music if you want to upload other music")
                            .setCornerRadius(4)
                            .setDuration(2500)
                            .sneakError();
                }
                break;


            case R.id.recordUpdate:
                checkFile = false;
                onRecord();
                break;

        }

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
        presenter.DeleteFavMusic(getContext(), favMusicResponse.getFavMusic().get(position), userData.getPhoneNo());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case AppUtils.PERMISSION_REQUEST_CODE:
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
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
            mWasGetContentIntent = galleryIntent.getAction().equals(
                    Intent.ACTION_PICK);
            startActivityForResult(galleryIntent, PICK_PHOTO_FOR_AVATAR);
        } else {
            Intent intent;
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/mpeg");
            startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
            mWasGetContentIntent = galleryIntent.getAction().equals(
                    Intent.ACTION_GET_CONTENT);
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


            String[] filePathColumn = {MediaStore.Audio.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            String mediaPath1 = cursor.getString(columnIndex);


            if (mediaPath1 != null && !mediaPath1.equals("")) {
                File file = new File(mediaPath1);

                // Get length of file in bytes
                long fileSizeInBytes = file.length();
                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                long fileSizeInKB = fileSizeInBytes / 1024;
                //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                // long fileSizeInMB = fileSizeInKB / 1024;

                if (fileSizeInKB < 500) {
                    uploadAudio(file);
                } else {
                    startRingdroidEditor(mediaPath1);
                }

            }
            cursor.close();
        }


    }

    private void uploadAudio(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        RequestBody phoneNo =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), userData.getPhoneNo());


        if (checkFile == false) {
            MultipartBody.Part AudioDes =
                    MultipartBody.Part.createFormData("audioDescription", file.getName(), requestFile);

            presenter.uploadAudioDes(getContext(), AudioDes, phoneNo);
        } else {
            MultipartBody.Part favMusic =
                    MultipartBody.Part.createFormData("favMusic", file.getName(), requestFile);

            presenter.uploadFavMusic(getContext(), favMusic, phoneNo);
        }


    }

    private void onRecord() {
        try {
            Intent intent = new Intent(Intent.ACTION_EDIT, Uri.parse("record"));
            intent.putExtra("was_get_content_intent", mWasGetContentIntent);
            intent.putExtra("checkType", checkFile);
            intent.setClassName("com.dating.klicked", "com.dating.klicked.AuidoTrimmer.AudioTrimmerActivity");
            startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
        } catch (Exception e) {
            Log.e("Ringdroid", "Couldn't start editor");
        }
    }

    private void startRingdroidEditor(String filename) {

        try {
            Intent intent = new Intent(Intent.ACTION_EDIT, Uri.parse(filename));
            intent.putExtra("was_get_content_intent", mWasGetContentIntent);
            intent.putExtra("checkType", checkFile);
            intent.setClassName("com.dating.klicked", "com.dating.klicked.AuidoTrimmer.AudioTrimmerActivity");
            startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
        } catch (Exception e) {
            Log.e("Ringdroid", "Couldn't start editor");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.GetProfile(getContext());
        //   presenter.GetFavMusic(getContext(), userData.getPhoneNo());


    }
}