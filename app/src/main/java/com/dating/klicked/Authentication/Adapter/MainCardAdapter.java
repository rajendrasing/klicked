package com.dating.klicked.Authentication.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Authentication.SubCardAdapter;
import com.dating.klicked.Model.GetCardModel;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.ViewPresenter.PendingUserPresent;
import com.dating.klicked.databinding.CustomCardLayoutBinding;
import com.dating.klicked.databinding.CustomMainCardLayoutBinding;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCardAdapter extends RecyclerView.Adapter<MainCardAdapter.MyMainCardView> implements SubCardAdapter.onSubCardClick {
    ArrayList<GetCardModel> list;
    onMainCardClick click;
    Activity activity;

    public MainCardAdapter(ArrayList<GetCardModel> list, Activity activity, onMainCardClick click) {
        this.list = list;
        this.activity = activity;
        this.click = click;
    }

    @NonNull
    @Override
    public MyMainCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomMainCardLayoutBinding binding = CustomMainCardLayoutBinding.inflate(inflater, parent, false);

        return new MyMainCardView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMainCardView holder, int position) {
        holder.binding.cardtext.setText(list.get(position).getName());

        SubCardAdapter cardAdapter = new SubCardAdapter(activity,list.get(position).getList(),this,  list.get(position).getName());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 4, LinearLayoutManager.VERTICAL, false);
        MyMainCardView.binding.subRecycler.setLayoutManager(layoutManager);
        MyMainCardView.binding.subRecycler.setItemAnimator(new DefaultItemAnimator());
        MyMainCardView.binding.subRecycler.setHasFixedSize(true);
        MyMainCardView.binding.subRecycler.setAdapter(cardAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onSubCardClickListener(ShowCardModal cardResponses) {
        click.onCardClickListener(cardResponses);
    }

    public static class MyMainCardView extends RecyclerView.ViewHolder {

        public static CustomMainCardLayoutBinding binding;

        public MyMainCardView(@NonNull CustomMainCardLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }


    }


    public interface onMainCardClick {
        void onCardClickListener(ShowCardModal cardResponses);
    }





}
