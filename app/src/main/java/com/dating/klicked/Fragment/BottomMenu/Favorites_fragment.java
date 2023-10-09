package com.dating.klicked.Fragment.BottomMenu;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Dialog;
import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dating.klicked.Fragment.Adapter.FavPersonAdapter;
import com.dating.klicked.Fragment.Adapter.HomeAdapter;
import com.dating.klicked.Fragment.Adapter.RecentFavAdapter;
import com.dating.klicked.Model.ResponseRepo.FavouriteResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.FavouritePresenter;
import com.dating.klicked.databinding.FragmentFavoritesBinding;
import com.dating.klicked.databinding.FragmentHomeBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;


public class Favorites_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FavouritePresenter.FavouriteView, FavPersonAdapter.FavPersonClicked {
    private FragmentFavoritesBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;

    User_Data userData;
    FavouritePresenter presenter;
    public MediaPlayer mediaPlayer;
    ImageView imageView;

    public Favorites_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();
        presenter = new FavouritePresenter(this);

        presenter.GetRecentFavouriteSuccess(getContext());
        presenter.GetFavouriteSuccess(getContext());


        binding.SwipeRefresh.setOnRefreshListener(this);
        binding.SwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.global__primary));


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }


    @Override
    public void onRefresh() {
        presenter.GetFavouriteSuccess(getContext());

        stopPlaying();
        binding.SwipeRefresh.setRefreshing(false);
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
            dialog.dismiss();
        }

    }

    @Override
    public void onError(String message) {
        if (message.equalsIgnoreCase("Do not exist") || message.equalsIgnoreCase("Cannot read property 'users' of undefined")) {
            binding.imdNoDta.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.VISIBLE);
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
    public void onGetRecentFavouriteSuccess(List<FavouriteResponse> RecentFavouriteResponse, String message) {
        if (message.equalsIgnoreCase("ok") && RecentFavouriteResponse != null && RecentFavouriteResponse.size() > 0) {
            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.recentRecyclerView.setHasFixedSize(true);
            binding.recentRecyclerView.setLayoutManager(layoutManager1);
            binding.recentRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recentRecyclerView.setAdapter(new RecentFavAdapter(getContext(), RecentFavouriteResponse, this));

            binding.recentRecyclerView.setVisibility(View.VISIBLE);
            binding.text.setVisibility(View.VISIBLE);
        } else {
            binding.recentRecyclerView.setVisibility(View.GONE);
            binding.text.setVisibility(View.GONE);
        }

    }

    @Override
    public void onGetFavouriteSuccess(List<FavouriteResponse> favouriteResponses, String message) {
        if (message.equalsIgnoreCase("ok") && favouriteResponses != null && favouriteResponses.size() > 0) {
            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.otherRecyclerView.setHasFixedSize(true);
            binding.otherRecyclerView.setLayoutManager(layoutManager1);
            binding.otherRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.otherRecyclerView.setAdapter(new FavPersonAdapter(getContext(), favouriteResponses, this));

            binding.otherRecyclerView.setVisibility(View.VISIBLE);
            binding.text1.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);

        } else {

            binding.otherRecyclerView.setVisibility(View.GONE);
            binding.text1.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.VISIBLE);
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

            presenter.GetRecentFavouriteSuccess(getContext());
            presenter.GetFavouriteSuccess(getContext());
        }

    }

    @Override
    public void onSendRequestSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_circle_check));

            Sneaker.with(getActivity())
                    .setTitle("Successfully Send Request in User")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();

        }
    }

    @Override
    public void onShareUserSuccess(String shareUserId, String message) {
        if (message.equalsIgnoreCase("ok")){
            Toast.makeText(getApplicationContext(), "Link Successful Generated", Toast.LENGTH_SHORT).show();
            AppUtils.shareUserProfile(getContext(),shareUserId);
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
    public void onDeleteFavPersonClicked(String userId) {

        presenter.deleteFavouriteUser(getContext(), userId);
    }

    @Override
    public void onSendRequestPersonClicked(ImageView imageViews, String ReciveredId) {
        imageView = imageViews;
        presenter.SendRequest(getContext(), userData.getId(), ReciveredId);
    }

    @Override
    public void onAudioPlayClicked(String Song, View view, Boolean aBoolean) {
        ImageView imageView = view.findViewById(R.id.audio);

        if (aBoolean == true) {
            stopPlaying();
            mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(Song));
            mediaPlayer.start();
            imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_icon_pause_circle));
            Toast.makeText(getContext(), "Playing", Toast.LENGTH_SHORT).show();

        } else {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            Toast.makeText(getContext(), "Pause", Toast.LENGTH_SHORT).show();

            imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_circle_headphone));

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

        presenter.ShareUserProfile(getContext(),userId,userData.getId());
    }

    @Override
    public void onPause() {
        super.onPause();

        stopPlaying();

    }

    private void stopPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}