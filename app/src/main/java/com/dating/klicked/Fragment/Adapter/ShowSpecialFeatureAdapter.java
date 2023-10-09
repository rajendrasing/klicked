package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomShowSpecialFeatureLayoutBinding;
import com.dating.klicked.databinding.CustomSpecificFeaturesLayoutBinding;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ShowSpecialFeatureAdapter extends RecyclerView.Adapter<ShowSpecialFeatureAdapter.ShowSpecialFeatureView> {
    List<UserProfileResponse.SpecialFeature> list;
    Context context;
    onShowSpecialFeatureClicked clicked;
    int blurSize;

    public ShowSpecialFeatureAdapter(List<UserProfileResponse.SpecialFeature> list, Context context, onShowSpecialFeatureClicked clicked, int blurSize) {
        this.list = list;
        this.context = context;
        this.clicked = clicked;
        this.blurSize = blurSize;
    }

    @NonNull
    @Override
    public ShowSpecialFeatureView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomShowSpecialFeatureLayoutBinding binding = CustomShowSpecialFeatureLayoutBinding.inflate(inflater, parent, false);

        return new ShowSpecialFeatureView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowSpecialFeatureView holder, int position) {
        Log.d("blurSize", String.valueOf(blurSize));
        Glide.with(context).load(PrefConf.IMAGE_URL +list.get(position).getImage())
               .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(blurSize, 1)))
                .dontAnimate()
                .into(holder.binding.img1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked.onShowSpecialFeatureClickedListener(list,position,blurSize);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ShowSpecialFeatureView extends RecyclerView.ViewHolder {

        CustomShowSpecialFeatureLayoutBinding binding;

        public ShowSpecialFeatureView(@NonNull CustomShowSpecialFeatureLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onShowSpecialFeatureClicked {
        void onShowSpecialFeatureClickedListener(List<UserProfileResponse.SpecialFeature> list,int position,int blurSize);

    }

}
