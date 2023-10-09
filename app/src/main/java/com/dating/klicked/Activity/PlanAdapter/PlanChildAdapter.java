package com.dating.klicked.Activity.PlanAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.ResponseRepo.PlanResponse;
import com.dating.klicked.databinding.CustomChaildPlanLayoutBinding;
import com.dating.klicked.databinding.CustomParantPlanLayoutBinding;

import java.util.List;

public class PlanChildAdapter extends RecyclerView.Adapter<PlanChildAdapter.PlanChildView> {
    List<PlanResponse.Detail> planResponseList;
    Context context;

    public PlanChildAdapter(List<PlanResponse.Detail> planResponseList, Context context) {
        this.planResponseList = planResponseList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlanChildView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomChaildPlanLayoutBinding binding = CustomChaildPlanLayoutBinding.inflate(inflater, parent, false);

        return new PlanChildView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanChildView holder, int position) {
        holder.binding.txtDetails.setText(planResponseList.get(position).getDetail());

    }

    @Override
    public int getItemCount() {
        return planResponseList.size();
    }

    public class PlanChildView extends RecyclerView.ViewHolder {
        CustomChaildPlanLayoutBinding binding;

        public PlanChildView(@NonNull CustomChaildPlanLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
