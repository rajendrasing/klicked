package com.dating.klicked.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dating.klicked.Authentication.Adapter.FavMusicAdapter;
import com.dating.klicked.Model.ResponseRepo.FavMusicResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.ViewPresenter.AudioDesPresenter;
import com.dating.klicked.databinding.ActivityAudioDesBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.FileUtils;
import com.dating.klicked.utils.ImagePath;
import com.irozon.sneaker.Sneaker;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class Audio_Des extends AppCompatActivity implements View.OnClickListener, AudioDesPresenter.AudioDesView, FavMusicAdapter.onFavMusicClick {
    ActivityAudioDesBinding binding;
    private View view;
    private Context context;
    private Dialog dialog;
    public boolean permissionStatus;
    private boolean mWasGetContentIntent;
    private int PICK_PHOTO_FOR_AVATAR = 1;
    private Dialog dialogBox;
    public MediaPlayer mediaPlayer;

    AudioDesPresenter presenter;
    Boolean checkFile;
    String PhoneNo, AudioDes = "";
    int favMusicSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_audio_des);
        view = binding.getRoot();
        context = Audio_Des.this;
        presenter = new AudioDesPresenter(this);
        dialog = AppUtils.hideShowProgress(context);

        PhoneNo = MyPreferences.getInstance(context).getString(PrefConf.USER_NUMBER, null);

        mediaPlayer = new MediaPlayer();

        binding.txtUpload.setOnClickListener(this);
        binding.addFavMusic.setOnClickListener(this);
        binding.txtBack.setOnClickListener(this);
        binding.txtNext.setOnClickListener(this);
        binding.audioPath.setOnClickListener(this);


        //  presenter.GetFavMusic(context, "+918766372989");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.audio_path:
                checkFile = false;
                onRecord();
                break;

            case R.id.txt_back:
                onBackPressed();
                break;


            case R.id.txt_upload:
                checkFile = false;
                galleryPicker();
                break;

            case R.id.add_fav_music:
                if (favMusicSize <= 2) {
                    checkFile = true;
                    galleryPicker();
                } else {
                    Sneaker.with(Audio_Des.this)
                            .setTitle("Please Upload maximum 3 Your current jam  ")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(2500)
                            .sneakError();
                }

                break;

            case R.id.txt_next:
                if (AudioDes.isEmpty()) {
                    Sneaker.with(Audio_Des.this)
                            .setTitle("Please Upload Your Audio Description ")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakWarning();
                } /*else if (favMusicSize == 0) {
                    Sneaker.with(Audio_Des.this)
                            .setTitle("Please Upload minimum 1 Your current jam  ")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakError();
                }*/ else {
                    startActivity(new Intent(Audio_Des.this, Add_Image.class));
                }

                break;

        }

    }

    @Override
    protected void onPause() {
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
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            startActivityForResult(galleryIntent, PICK_PHOTO_FOR_AVATAR);
            mWasGetContentIntent = galleryIntent.getAction().equals(
                    Intent.ACTION_PICK);
            Log.d("come", "1");
        } else {
            Intent intent;
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/mpeg");
            startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
            mWasGetContentIntent = galleryIntent.getAction().equals(
                    Intent.ACTION_GET_CONTENT);
            Log.d("come", "2");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK && data != null) {
            //the image URI

            if (data == null)
                return;


            Log.d("Datatatatata", String.valueOf(data.getData()));
            Uri uri = data.getData();
            String mediaPath1 = FileUtils.getPath(context, uri);

            if (checkFile == false) {
                binding.audioPath.setText(mediaPath1);
            }

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

        }


    }

    private void uploadAudio(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        RequestBody phoneNo =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), PhoneNo);


        if (checkFile == false) {
            MultipartBody.Part AudioDes =
                    MultipartBody.Part.createFormData("audioDescription", file.getName(), requestFile);

            presenter.uploadAudioDes(context, AudioDes, phoneNo);
        } else {
            MultipartBody.Part favMusic =
                    MultipartBody.Part.createFormData("favMusic", file.getName(), requestFile);

            presenter.uploadFavMusic(context, favMusic, phoneNo);
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
        Sneaker.with(Audio_Des.this)
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onAudioDesUploadSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            AudioDes = "Successfully Uploaded";
            binding.audioPath.setText("");
            Sneaker.with(Audio_Des.this)
                    .setTitle("Successfully Uploaded Your Audio Description ")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
        }
    }

    @Override
    public void onFavMusicUploadSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {

            Sneaker.with(Audio_Des.this)
                    .setTitle("Successfully Uploaded Your current jam ")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            presenter.GetFavMusic(context, PhoneNo);
        }
    }

    @Override
    public void onGetFavMusicSuccess(FavMusicResponse favMusicResponse, String message) {
        if (message.equalsIgnoreCase("ok") && favMusicResponse.getFavMusic() != null) {
            favMusicSize = favMusicResponse.getFavMusic().size();
            FavMusicAdapter favMusicAdapter = new FavMusicAdapter(Audio_Des.this, favMusicResponse, this, false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Audio_Des.this, RecyclerView.VERTICAL, false);
            binding.favRecycler.setLayoutManager(layoutManager);
            binding.favRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.favRecycler.setHasFixedSize(true);
            binding.favRecycler.setAdapter(favMusicAdapter);
            binding.favRecycler.setVisibility(View.GONE);
        } else {

            binding.favRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDeleteFavMusicSuccess(ResponseBody responseBody, String message) {

        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(Audio_Des.this)
                    .setTitle("Successfully Deleted")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            presenter.GetFavMusic(context, PhoneNo);
        }

    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(Audio_Des.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onPlayFavMusicClickListener(String song, ImageView play, ImageView pause) {
        stopPlaying();
        Uri uri = Uri.parse(PrefConf.IMAGE_URL + song);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(Audio_Des.this, uri);
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

        presenter.DeleteFavMusic(context, favMusicResponse.getFavMusic().get(position), PhoneNo);
    }

    private void stopPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
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
    protected void onResume() {
        super.onResume();
        String check = MyPreferences.getInstance(this).getString(PrefConf.CheckMusicsong, null);
        if (check != null && check.equalsIgnoreCase("false")) {
            binding.audioPath.setText("Successfully Uploaded");
            AudioDes = "Successfully Uploaded";
        }/* else if (check != null && check.equalsIgnoreCase("ture")) {
            presenter.GetFavMusic(this, PhoneNo);
        }
*/
    }
}