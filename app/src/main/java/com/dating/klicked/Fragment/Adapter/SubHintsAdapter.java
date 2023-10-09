package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.Model.ResponseRepo.SubHintsResponse;
import com.dating.klicked.databinding.CustomHintsLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class SubHintsAdapter extends RecyclerView.Adapter<SubHintsAdapter.SubHintsView> {
    Context context;
    List<SubHintsResponse> list;
    onClickSubHints onClickSubHints;

    public SubHintsAdapter(Context context, List<SubHintsResponse> list, SubHintsAdapter.onClickSubHints onClickSubHints) {
        this.context = context;
        this.list = list;
        this.onClickSubHints = onClickSubHints;
    }

    @NonNull
    @Override
    public SubHintsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomHintsLayoutBinding binding = CustomHintsLayoutBinding.inflate(inflater, parent, false);

        return new SubHintsView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubHintsView holder, int position) {
        holder.binding.textTitle.setText(list.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubHints.onClickSubHintsListener(position,list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubHintsView extends RecyclerView.ViewHolder {
        CustomHintsLayoutBinding binding;

        public SubHintsView(@NonNull CustomHintsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.textDes.setVisibility(View.GONE);
        }
    }


    public interface onClickSubHints {
        void onClickSubHintsListener(int positions, List<SubHintsResponse> list);
    }
}
