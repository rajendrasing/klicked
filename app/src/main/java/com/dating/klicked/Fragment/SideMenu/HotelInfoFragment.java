package com.dating.klicked.Fragment.SideMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dating.klicked.Fragment.Adapter.HotelAdapter;
import com.dating.klicked.Model.ResponseRepo.HotelResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.ViewPresenter.HotelPresenter;
import com.dating.klicked.databinding.FragmentHotelInfoBinding;
import com.dating.klicked.utils.AppUtils;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

public class HotelInfoFragment extends Fragment implements HotelPresenter.HotelView, View.OnClickListener {
    FragmentHotelInfoBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    private HotelPresenter presenter;
    int position;
    String phone;

    public HotelInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_info, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        presenter = new HotelPresenter(this);

        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
        presenter.getAllHotel(getContext());

        binding.txtCall.setOnClickListener(this);
        binding.map.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


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
    public void onGetAllHotelSuccess(List<HotelResponse> hotelResponseList, String message) {
        List<SlideModel> BannerManufacturer = new ArrayList<>();
        if (message.equalsIgnoreCase("ok") && hotelResponseList.size() > 0 && hotelResponseList != null) {

            if (hotelResponseList.get(position).getImage().size() > 0) {
                for (int i = 0; i < hotelResponseList.get(position).getImage().size(); i++) {
                    BannerManufacturer.add(new SlideModel(PrefConf.IMAGE_URL + hotelResponseList.get(position).getImage().get(i), ScaleTypes.FIT));
                }
                binding.slider.setImageList(BannerManufacturer);
                Glide.with(getActivity()).load(PrefConf.IMAGE_URL + hotelResponseList.get(position).getImage().get(0))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.img);
            }


            binding.txtName.setText(hotelResponseList.get(position).getName());
            binding.txtHname.setText(hotelResponseList.get(position).getName());
            SimpleRatingBar.AnimationBuilder builder = binding.textRating.getAnimationBuilder()
                    .setRatingTarget(Float.parseFloat(hotelResponseList.get(position).getRating()))
                    .setDuration(2000)
                    .setRepeatMode(1)
                    .setInterpolator(new BounceInterpolator());
            builder.start();

            binding.textDes.setText(hotelResponseList.get(position).getDescription());
            binding.txtAddress.setText(hotelResponseList.get(position).getAddress());
            binding.txtOpen.setText(hotelResponseList.get(position).getOpenTiming());
            binding.txtClose.setText(hotelResponseList.get(position).getCloseTiming());
            phone = hotelResponseList.get(position).getPhone();

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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txt_call:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);
                break;

            case R.id.map:
                String map = "http://maps.google.co.in/maps?q=" + binding.txtAddress.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(i);
                break;
        }
    }
}