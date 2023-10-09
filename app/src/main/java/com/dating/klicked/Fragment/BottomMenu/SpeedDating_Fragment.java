package com.dating.klicked.Fragment.BottomMenu;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dating.klicked.Activity.SpeedDatingChating;
import com.dating.klicked.Model.ResponseRepo.UserResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.SeedDatingUserPresenter;
import com.dating.klicked.databinding.FragmentSpeedDatingBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;


public class SpeedDating_Fragment extends Fragment implements View.OnClickListener, SeedDatingUserPresenter.SeedDatingUserView {
    FragmentSpeedDatingBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    SeedDatingUserPresenter presenter;
    String userId, currentDate, beforeDate;
    boolean check = false;
    User_Data userData;
    long time = MyPreferences.getInstance(getContext()).getLong(PrefConf.ReamingTimmer, Long.valueOf("5"));


    private final long START_TIME_IN_MILLIS = time * 60000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;


    public SpeedDating_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_speed_dating, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();
        presenter = new SeedDatingUserPresenter(this);

        presenter.GetSeedDatingUser(getContext());
        // presenter.OnlineUser(getContext());
        binding.textNextPerson.setOnClickListener(this);
        binding.txtChat.setOnClickListener(this);


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.text_nextPerson:
                presenter.GetSeedDatingUser(getContext());
                break;

            case R.id.txt_chat:
                presenter.CreateSpeedChatDocumentUser(getContext(), userId, binding.textName.getText().toString());
                break;
        }
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
    public void onSeedDatingUserSuccess(UserResponse response, String message) {
        if (message.equalsIgnoreCase("ok") && response.getResult() != null) {
            if (response.getResult().getFirstName() != null) {
                binding.textName.setText(response.getResult().getFirstName());

            } else {
                binding.textName.setText("");

            }
            userId = response.getResult().getId();
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

                startActivity(new Intent(getActivity(), SpeedDatingChating.class)
                        .putExtra("userId", userId)
                        .putExtra("chatId", ChatId)
                        .putExtra("name", binding.textName.getText().toString()));


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onOnlineUserSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Toast.makeText(getContext(), "Speed Online", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpdateSpeedTimeSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Log.d("kndkl", "nscns,nm,v");
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
    public void onPause() {
        super.onPause();
        check = true;
        pauseTimer();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (check == true) {
            presenter.GetSeedDatingUser(getContext());
        }

    }


    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        currentDate = AppUtils.getCurrentDate();
        //  updateButtons();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();


            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                //updateButtons();
            } else {
                startTimer();
            }
        } else {
            startTimer();
        }
    }


    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
        startTimer();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        binding.txtTimmer.setText(timeLeftFormatted);
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (millisUntilFinished < 1000)
                            MyPreferences.getInstance(getActivity()).putString(PrefConf.CheckTimmer, AppUtils.getCurrentDate());

                    }
                });
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
                beforeDate = MyPreferences.getInstance(getActivity()).getString(PrefConf.CheckTimmer, "");
                if (!currentDate.equalsIgnoreCase(beforeDate)) {
                    resetTimer();
                }


            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        presenter.UpdateSpeedtime(getContext(), String.valueOf(mTimeLeftInMillis / 60000), userData.getPhoneNo());
        //    Log.d("mTimeLeftInMillis", String.valueOf(mTimeLeftInMillis));


        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    private void updateButtons() {
        if (mTimerRunning) {

            binding.txt.setVisibility(View.VISIBLE);
            binding.txtTimmer.setVisibility(View.VISIBLE);
            binding.card.setVisibility(View.VISIBLE);
            binding.txtChat.setVisibility(View.VISIBLE);
            binding.textNextPerson.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);
        } else {
            binding.txt.setVisibility(View.GONE);
            binding.txtTimmer.setVisibility(View.GONE);
            binding.card.setVisibility(View.GONE);
            binding.txtChat.setVisibility(View.GONE);
            binding.textNextPerson.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.VISIBLE);
        }
    }

}