package com.dating.klicked.Authentication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dating.klicked.Authentication.View_Flipper;
import com.dating.klicked.Fragment.Profile.CardFragment;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomCardLayoutBinding;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyCardView> {
    List<CardResponse> list;
    Context context;
    public static final ArrayList<Boolean> integers = new ArrayList<Boolean>();
    public static final ArrayList<String> subCardName = new ArrayList<String>();
    public static final ArrayList<String> subCardBackImg = new ArrayList<String>();
    public static final ArrayList<String> subCardIcon = new ArrayList<String>();
    public static final ArrayList<String> CardId = new ArrayList<String>();
    onCardClick click;
    boolean check;


    public CardAdapter(List<CardResponse> list, Context context, onCardClick click,boolean check) {
        this.list = list;
        this.context = context;
        this.click = click;
        this.check = check;
    }

    public CardAdapter() {
    }

    @NonNull
    @Override
    public MyCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomCardLayoutBinding binding = CustomCardLayoutBinding.inflate(inflater, parent, false);

        return new MyCardView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCardView holder, int position) {
        subCardName.clear();
        subCardBackImg.clear();
        subCardIcon.clear();
        Glide.with(context).load(PrefConf.IMAGE_URL + list.get(position).getBackgroundImg()).into(holder.binding.backImg);
        Glide.with(context).load(PrefConf.IMAGE_URL + list.get(position).getIcon()).into(holder.binding.iconImg);
        holder.binding.text.setText(list.get(position).getName());
        if (check==false){
            integers.add(false);
        }else {
            if (integers.size()>0 && integers!=null){
                if (integers.get(position).booleanValue() == true) {
                    holder.binding.backImg.setBackground(context.getResources().getDrawable(R.drawable.card_selected_background));
                    CardId.add(list.get(position).getId());
                } else {
                    holder.binding.backImg.setPadding(0, 0, 0, 0);

                }
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (integers.get(position).booleanValue() == false) {
                    if (check==false){
                        subCardName.add(list.get(position).getName());
                        subCardBackImg.add(list.get(position).getBackgroundImg());
                        subCardIcon.add(list.get(position).getIcon());

                    }else {
                        CardFragment.SelectCardName.add(list.get(position).getName());
                        CardFragment.SelectCardBackImg.add(list.get(position).getBackgroundImg());
                        CardFragment.SelectCardIcon.add(list.get(position).getIcon());

                    }
                    integers.add(position, true);

                     CardId.add(list.get(position).getId());

                    Log.d("cardName", String.valueOf(subCardName));

                    click.onCardClickListener(subCardName);
                    holder.binding.backImg.setBackground(context.getResources().getDrawable(R.drawable.card_selected_background));

                } else {
                    integers.add(position, false);
                    if (check== false){
                        subCardName.remove(list.get(position).getName());
                        subCardBackImg.remove(list.get(position).getBackgroundImg());
                        subCardIcon.remove(list.get(position).getIcon());
                    }else {
                        CardFragment.SelectCardName.remove(list.get(position).getName());
                        CardFragment.SelectCardBackImg.remove(list.get(position).getBackgroundImg());
                        CardFragment.SelectCardIcon.remove(list.get(position).getIcon());

                    }
                    holder.binding.backImg.setBackground(null);
                    holder.binding.backImg.setPadding(0, 0, 0, 0);

                    CardId.remove(list.get(position).getId());


                    Log.d("cardName", String.valueOf(subCardName));
                    click.onCardClickListener(subCardName);

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyCardView extends RecyclerView.ViewHolder {

        CustomCardLayoutBinding binding;

        public MyCardView(@NonNull CustomCardLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }


    }

    public interface onCardClick {
        void onCardClickListener(ArrayList<String> list);
    }
}
