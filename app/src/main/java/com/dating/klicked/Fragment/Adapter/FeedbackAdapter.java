package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.Model.ResponseRepo.FeedbackResponse;
import com.dating.klicked.databinding.CustomFeedbackLayoutBinding;
import com.dating.klicked.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackView> {
    Context context;
    FeedbackResponse list;
    List<FAQResponse> faqResponseList;
    boolean checkAdapter;
    String partten ="hh:mm a | EEE dd-MM-yyyy  ";


    public FeedbackAdapter(Context context, FeedbackResponse list, boolean checkAdapter) {
        this.context = context;
        this.list = list;
        this.checkAdapter = checkAdapter;
    }

    public FeedbackAdapter(Context context, List<FAQResponse> faqResponseList, boolean checkAdapter) {
        this.context = context;
        this.faqResponseList = faqResponseList;
        this.checkAdapter = checkAdapter;
    }

    @NonNull
    @Override
    public FeedbackView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomFeedbackLayoutBinding binding = CustomFeedbackLayoutBinding.inflate(inflater, parent, false);

        return new FeedbackView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackView holder, int position) {
        if (checkAdapter == true){
            holder.binding.txtDes.setText(list.getResult().get(position).getDescription());
            holder.binding.txtTittle.setText(list.getResult().get(position).getTitle());
            holder.binding.txtDate.setText(AppUtils.getDateTime1(list.getResult().get(position).getCreatedAt(),partten));

        }else {
            holder.binding.txtDes.setText(faqResponseList.get(position).getAnswer());
            holder.binding.txtTittle.setText(faqResponseList.get(position).getQuestion());
            holder.binding.txtDate.setText(AppUtils.getDateTime1(faqResponseList.get(position).getUpdatedAt(),partten));

        }

    }

    @Override
    public int getItemCount() {
        if (checkAdapter==true){
            return list.getResult().size();
        }else {
            return faqResponseList.size();
        }

    }


    public class FeedbackView extends RecyclerView.ViewHolder {

        CustomFeedbackLayoutBinding binding;

        public FeedbackView(@NonNull CustomFeedbackLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
