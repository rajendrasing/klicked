package com.dating.klicked.Authentication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dating.klicked.Model.ResponseRepo.UserChatResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomCardLayoutBinding;
import com.dating.klicked.databinding.CustomSelectedCardLayoutBinding;
import com.dating.klicked.utils.ItemMoveCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowCardAdapter extends RecyclerView.Adapter<ShowCardAdapter.ShowCardView> implements ItemMoveCallback.ItemTouchHelperContract{
    ArrayList<String> CardName, CardBackImg, cardIcon;
    Context context;

    public ShowCardAdapter(ArrayList<String> cardName, ArrayList<String> cardBackImg, ArrayList<String> cardIcon, Context context) {
        this.CardName = cardName;
        this.CardBackImg = cardBackImg;
        this.cardIcon = cardIcon;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomSelectedCardLayoutBinding binding = CustomSelectedCardLayoutBinding.inflate(inflater, parent, false);

        return new ShowCardView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowCardView holder, int position) {
        Glide.with(context).load(PrefConf.IMAGE_URL + CardBackImg.get(position)).into(holder.binding.backImg);
        Glide.with(context).load(PrefConf.IMAGE_URL + cardIcon.get(position)).into(holder.binding.iconImg);
        holder.binding.text.setText(CardName.get(position));


    }

    @Override
    public int getItemCount() {
        return CardName.size();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(CardAdapter.CardId, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(CardAdapter.CardId, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(ShowCardView myViewHolder) {
        myViewHolder.itemView.setBackgroundColor(Color.GRAY);


    }

    @Override
    public void onRowClear(ShowCardView myViewHolder) {
        myViewHolder.binding.relative.setBackground(context.getResources().getDrawable(R.drawable.btn_color_border));
        myViewHolder.binding.relative.setPadding(5,5,5,5);
    }

    public class ShowCardView extends RecyclerView.ViewHolder {

        CustomSelectedCardLayoutBinding binding;

        public ShowCardView(@NonNull CustomSelectedCardLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }


    }

    public void update(ArrayList<String> cardName, ArrayList<String> cardBackImg, ArrayList<String> cardIcon){

        notifyDataSetChanged();
    }
}
