package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.ChatResponse;
import com.dating.klicked.Model.ResponseRepo.HotelResponse;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomChatLayoutBinding;
import com.dating.klicked.databinding.CustomHotelLayoutBinding;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelView> implements Filterable {
    Context context;
    List<HotelResponse> hotelResponseList;
    private HotelViewClicked hotelViewClicked;
    private List<HotelResponse> exampleListFull;


    public HotelAdapter(Context context, List<HotelResponse> hotelResponseList, HotelViewClicked hotelViewClicked) {
        this.context = context;
        this.hotelResponseList = hotelResponseList;
        this.hotelViewClicked = hotelViewClicked;

        exampleListFull = new ArrayList<>(hotelResponseList);
    }

    @NonNull
    @Override
    public HotelView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomHotelLayoutBinding binding = CustomHotelLayoutBinding.inflate(inflater, parent, false);

        return new HotelView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelView holder, int position) {
        if (hotelResponseList.get(position).getImage().size()>0){
            Glide.with(context).load(PrefConf.IMAGE_URL + hotelResponseList.get(position).getImage().get(0))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.img);
        }

        if (!hotelResponseList.get(position).getRating().equalsIgnoreCase("")){
            SimpleRatingBar.AnimationBuilder builder = holder.binding.textRating.getAnimationBuilder()
                    .setRatingTarget(Float.parseFloat(hotelResponseList.get(position).getRating()))
                    .setDuration(2000)
                    .setRepeatMode(1)
                    .setInterpolator(new BounceInterpolator());
            builder.start();
        }else {
            SimpleRatingBar.AnimationBuilder builder = holder.binding.textRating.getAnimationBuilder()
                    .setRatingTarget(Float.parseFloat("0"))
                    .setDuration(2000)
                    .setRepeatMode(1)
                    .setInterpolator(new BounceInterpolator());
            builder.start();
        }

        holder.binding.txtName.setText(hotelResponseList.get(position).getName());
        holder.binding.textLoc.setText(hotelResponseList.get(position).getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotelViewClicked.onHotelViewClickedListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hotelResponseList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<HotelResponse> filteredList = new ArrayList<HotelResponse>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (HotelResponse item : exampleListFull) {
                    if (item.getLocation().toLowerCase().trim().contains(filterPattern) ) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            hotelResponseList.clear();
            hotelResponseList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class HotelView extends RecyclerView.ViewHolder {
        CustomHotelLayoutBinding binding;

        public HotelView(@NonNull CustomHotelLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public interface HotelViewClicked {
        void onHotelViewClickedListener(int position);

    }
}
