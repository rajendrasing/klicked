package com.dating.klicked.Authentication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dating.klicked.Authentication.Adapter.CardSelectedModel;
import com.dating.klicked.Authentication.Adapter.MainCardAdapter;
import com.dating.klicked.Authentication.Adapter.ShowCardModal;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomMainCardLayoutBinding;
import com.dating.klicked.databinding.CustomSubcardLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class SubCardAdapter extends RecyclerView.Adapter<SubCardAdapter.MySubCardView> {
    List<CardResponse> list;
    Context context;
    onSubCardClick click;
    String cardName;
    ArrayList<CardSelectedModel> selectedModelArrayList = new ArrayList<CardSelectedModel>();
    public static final ArrayList<String> subCardName = new ArrayList<String>();
    public static final ArrayList<String> subCardBackImg = new ArrayList<String>();
    public static final ArrayList<String> subCardIcon = new ArrayList<String>();

    ArrayList<ShowCardModal> showCardModals = new ArrayList<>();


    public SubCardAdapter(Context context, List<CardResponse> list, onSubCardClick click, String cardName) {
        this.list = list;
        this.context = context;
        this.click = click;
        this.cardName = cardName;
    }

    @NonNull
    @Override
    public MySubCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomSubcardLayoutBinding binding = CustomSubcardLayoutBinding.inflate(inflater, parent, false);

        return new MySubCardView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MySubCardView holder, int position) {
        subCardName.clear();
        subCardBackImg.clear();
        subCardIcon.clear();
        selectedModelArrayList.add(new CardSelectedModel(list.get(position).getCard(), false, list.get(position).getName(), list.get(position).getBackgroundImg(), list.get(position).getIcon(), position));
        Glide.with(context).load(PrefConf.IMAGE_URL + list.get(position).getBackgroundImg()).into(holder.binding.backImg);
        Glide.with(context).load(PrefConf.IMAGE_URL + list.get(position).getIcon()).into(holder.binding.iconImg);
        holder.binding.text.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedModelArrayList.get(position).getSubCard().equalsIgnoreCase(list.get(position).getName()) && selectedModelArrayList.get(position).isChecked() == false) {
                    selectedModelArrayList.set(position, new CardSelectedModel(list.get(position).getCard(), true, list.get(position).getName(), list.get(position).getBackgroundImg(), list.get(position).getIcon(), position));
                    holder.binding.relative.setBackground(context.getResources().getDrawable(R.drawable.card_selected_background));
                    subCardName.add(selectedModelArrayList.get(position).getSubCard());
                    subCardBackImg.add(selectedModelArrayList.get(position).getBackgroundImage());
                    subCardIcon.add(selectedModelArrayList.get(position).getIcon());
                    click.onSubCardClickListener(new ShowCardModal(list.get(position).getName(), list.get(position).getBackgroundImg(), list.get(position).getIcon(), list.get(position).getName(), true));
                    //   click.onSubCardClickListener(cardResponses);
                } else if (selectedModelArrayList.get(position).getSubCard().equalsIgnoreCase(list.get(position).getName()) && selectedModelArrayList.get(position).isChecked() == true) {
                    selectedModelArrayList.set(position, new CardSelectedModel(list.get(position).getCard(), false, list.get(position).getName(), list.get(position).getBackgroundImg(), list.get(position).getIcon(), position));
                    holder.binding.relative.setBackground(null);
                    subCardName.remove(selectedModelArrayList.get(position).getSubCard());
                    subCardBackImg.remove(selectedModelArrayList.get(position).getBackgroundImage());
                    subCardIcon.remove(selectedModelArrayList.get(position).getIcon());


                    click.onSubCardClickListener(new ShowCardModal(list.get(position).getName(), list.get(position).getBackgroundImg(), list.get(position).getIcon(), list.get(position).getName(), false));


                }


                Log.d("subCardName", subCardName.toString());

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MySubCardView extends RecyclerView.ViewHolder {

        CustomSubcardLayoutBinding binding;

        public MySubCardView(@NonNull CustomSubcardLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }


    }


    public interface onSubCardClick {
        void onSubCardClickListener(ShowCardModal cardResponses);
    }


}
