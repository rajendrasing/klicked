package com.dating.klicked.Activity.PlanAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Activity.PlanPurchaseActivity;
import com.dating.klicked.Fragment.Adapter.FeedbackAdapter;
import com.dating.klicked.Fragment.Adapter.PlanAdapter;
import com.dating.klicked.Model.ResponseRepo.PlanResponse;
import com.dating.klicked.databinding.CustomParantPlanLayoutBinding;

import java.util.List;

public class PlanParentAdapter extends RecyclerView.Adapter<PlanParentAdapter.PlanParentView> {

    List<PlanResponse> planResponseList;
    Context context;

    public PlanParentAdapter(List<PlanResponse> planResponseList, Context context) {
        this.planResponseList = planResponseList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlanParentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomParantPlanLayoutBinding binding = CustomParantPlanLayoutBinding.inflate(inflater, parent, false);

        return new PlanParentView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanParentView holder, int position) {
        holder.binding.txtMonth.setText(planResponseList.get(position).getDuration()+" Months");
        holder.binding.textPrice.setText(planResponseList.get(position).getPrice());
        holder.binding.txtNext.setText("Get "+planResponseList.get(position).getDuration()+" Months");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        holder.binding.recyclerView.setLayoutManager(layoutManager);
        holder.binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.binding.recyclerView.setHasFixedSize(true);
        holder.binding.recyclerView.setAdapter(new PlanChildAdapter(planResponseList.get(position).getDetails(),context));

        holder.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), PlanPurchaseActivity.class);
                intent.putExtra("price",planResponseList.get(position).getPrice());
                intent.putExtra("planName",planResponseList.get(position).getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return planResponseList.size();
    }

    public class PlanParentView extends RecyclerView.ViewHolder {
        CustomParantPlanLayoutBinding binding;

        public PlanParentView(@NonNull CustomParantPlanLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
