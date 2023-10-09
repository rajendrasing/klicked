package com.dating.klicked.Fragment.Adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dating.klicked.Model.ResponseRepo.CardSubCardResponse;
import com.dating.klicked.Model.ResponseRepo.MainCardResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomChatLayoutBinding;
import com.dating.klicked.databinding.CustomPreviewCardLayoutBinding;
import com.dating.klicked.databinding.CustomSelectedCardLayoutBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PreviewCardAdapter extends RecyclerView.Adapter<PreviewCardAdapter.PreviewCardView> {
    Context context;
    MainCardResponse cardSubCardResponse;
    Integer[] cardBack = {R.drawable.pre_back_card1, R.drawable.pre_back_card2, R.drawable.pre_back_card3};
    onPreviewCardClick cardClick;

    public PreviewCardAdapter(Context context, MainCardResponse cardSubCardResponse, onPreviewCardClick cardClick) {
        this.context = context;
        this.cardSubCardResponse = cardSubCardResponse;
        this.cardClick = cardClick;
    }

    public PreviewCardAdapter(Context context, MainCardResponse cardSubCardResponse) {
        this.context = context;
        this.cardSubCardResponse = cardSubCardResponse;
    }

    @NonNull
    @Override
    public PreviewCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomSelectedCardLayoutBinding binding = CustomSelectedCardLayoutBinding.inflate(inflater, parent, false);

        return new PreviewCardView(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PreviewCardView holder, int position) {
        Glide.with(context).load(PrefConf.IMAGE_URL + cardSubCardResponse.getCards().get(position).getBackgroundImg()).into(holder.binding.backImg);
        Glide.with(context).load(PrefConf.IMAGE_URL + cardSubCardResponse.getCards().get(position).getIcon()).into(holder.binding.iconImg);
        holder.binding.text.setText(cardSubCardResponse.getCards().get(position).getName());


      /*   int randomAndroidColor = cardBack[new Random().nextInt(cardBack.length)];
        holder.binding.linear.setBackgroundResource(randomAndroidColor);
        holder.binding.cardName.setText(cardSubCardResponse.getResult().get(position).getCard().getName());

       holder.binding.subcardlength.setText(cardSubCardResponse.getResult().get(position).getSubcard().size() + " Sub Cards");


        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.binding.recyclerView.setHasFixedSize(true);
        holder.binding.recyclerView.setLayoutManager(layoutManager1);
        holder.binding.recyclerView.addItemDecoration(new OverlapDecoration(-20));
        holder.binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.binding.recyclerView.setAdapter(new PreviewSubCardAdapter(context, cardSubCardResponse, position));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardClick.onPreviewCardClickListener(cardSubCardResponse.getResult().get(position).getSubcard());
            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return cardSubCardResponse.getCards().size();
    }


    public class PreviewCardView extends RecyclerView.ViewHolder {
        CustomSelectedCardLayoutBinding binding;

        public PreviewCardView(@NonNull CustomSelectedCardLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public class OverlapDecoration extends RecyclerView.ItemDecoration {
        private final int mSpace;

        public OverlapDecoration(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);

            if (position == 0) {
                return;
            } else {
                outRect.set(mSpace, 0, 0, 0);
            }
        }
    }

    public interface onPreviewCardClick {
        void onPreviewCardClickListener(List<CardSubCardResponse.Result.Subcard> subcard);
    }

}
