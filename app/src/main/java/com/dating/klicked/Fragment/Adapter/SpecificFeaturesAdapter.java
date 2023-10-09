package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomHintsLayoutBinding;
import com.dating.klicked.databinding.CustomSpecificFeaturesLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class SpecificFeaturesAdapter extends RecyclerView.Adapter<SpecificFeaturesAdapter.SpecificFeatureView> {
    Context context;
    List<UserProfileResponse.SpecialFeature> list;
    onSpecificFeatureClicked clicked;
    ArrayList<Boolean> arrayList = new ArrayList<>();


    public SpecificFeaturesAdapter(Context context, List<UserProfileResponse.SpecialFeature> list, onSpecificFeatureClicked clicked) {
        this.context = context;
        this.list = list;
        this.clicked = clicked;
    }


    @NonNull
    @Override
    public SpecificFeatureView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomSpecificFeaturesLayoutBinding binding = CustomSpecificFeaturesLayoutBinding.inflate(inflater, parent, false);

        return new SpecificFeatureView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificFeatureView holder, int position) {

        if (list.get(position).getImage() != null) {
            Glide.with(context).load(PrefConf.IMAGE_URL + list.get(position).getImage()).into(holder.binding.img);
            holder.binding.addImg.setVisibility(View.GONE);
            holder.binding.cardDelete.setVisibility(View.VISIBLE);
            holder.binding.img.setBackground(null);

        } else {
            holder.binding.addImg.setVisibility(View.VISIBLE);
            holder.binding.cardDelete.setVisibility(View.GONE);
            holder.binding.img.setBackground(context.getResources().getDrawable(R.drawable.ic_specific_back));
        }

        holder.binding.cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked.DeleteSpecificFeatureClickedListener(list.get(position).getId());
            }
        });


        holder.binding.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked.addSpecificFeatureClickedListener(list.get(position).getPosition());
            }
        });

        //  Log.d("Position", String.valueOf(list.get(position).getPosition()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class SpecificFeatureView extends RecyclerView.ViewHolder {
        CustomSpecificFeaturesLayoutBinding binding;

        public SpecificFeatureView(@NonNull CustomSpecificFeaturesLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onSpecificFeatureClicked {
        void addSpecificFeatureClickedListener(int position);

        void DeleteSpecificFeatureClickedListener(String id);

    }
}
