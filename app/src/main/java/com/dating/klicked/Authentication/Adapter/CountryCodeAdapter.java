package com.dating.klicked.Authentication.Adapter;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.CountryCode;
import com.dating.klicked.databinding.CustomCountryCodeLayoutBinding;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.ArrayList;
import java.util.List;

public class CountryCodeAdapter extends BaseAdapter {
    Context context;
    List<CountryCode> arrayListt;
    LayoutInflater inflter;

    public CountryCodeAdapter(Context context, List<CountryCode> arrayList) {
        this.context = context;
        this.arrayListt = arrayList;
        inflter = (LayoutInflater.from(context));

    }


    @Override
    public int getCount() {
        return arrayListt.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CustomCountryCodeLayoutBinding binding = CustomCountryCodeLayoutBinding.inflate(inflter, viewGroup, false);

        if (arrayListt.get(i).getCallingCodes().size()>0){
            binding.text.setText(arrayListt.get(i).getCallingCodes().get(0));
        }else {
            binding.text.setText("NIL");

        }


        RequestBuilder<PictureDrawable> requestBuilder = GlideToVectorYou
                .init()
                .with(context)
                .getRequestBuilder();


        requestBuilder
                .load(arrayListt.get(i).getFlag())
                .apply(new RequestOptions().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.flageImg);


        return binding.getRoot();
    }



}
