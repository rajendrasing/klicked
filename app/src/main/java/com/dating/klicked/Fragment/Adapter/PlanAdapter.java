package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Activity.PlanDetailsActivity;
import com.dating.klicked.Model.ResponseRepo.PlanResponse;
import com.dating.klicked.R;
import com.dating.klicked.databinding.CustomPlanLayoutBinding;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyPlanView> {

    List<PlanResponse> planResponseList;
    Context context;
    onPlanClick planClick;
    private int selectedPosition = 1;

    public PlanAdapter(List<PlanResponse> planResponseList, Context context, onPlanClick planClick) {
        this.planResponseList = planResponseList;
        this.context = context;
        this.planClick = planClick;
    }

    @NonNull
    @Override
    public MyPlanView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomPlanLayoutBinding binding = CustomPlanLayoutBinding.inflate(inflater, parent, false);

        return new MyPlanView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlanView holder, int position) {
        holder.binding.txtDis.setText("Save " + planResponseList.get(position).getDiscount() + "%");
        holder.binding.txtDuration.setText(planResponseList.get(position).getDuration());
        holder.binding.textPrice.setText(context.getResources().getString(R.string.rupee_sign) + planResponseList.get(position).getPrice());

        if (selectedPosition == position) {
            holder.binding.linear.setBackground(context.getResources().getDrawable(R.drawable.plan_selected_background)); //using selector drawable
            holder.binding.txtMore.setVisibility(View.VISIBLE);
            planClick.onPlanClickedListener(position, planResponseList);
        } else {
            holder.binding.linear.setBackground(null); //using selector drawable
            holder.binding.txtMore.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
            holder.binding.linear.setBackground(context.getResources().getDrawable(R.drawable.plan_selected_background)); //using selector drawable
            planClick.onPlanClickedListener(position, planResponseList);
        });


        holder.binding.txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PlanDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return planResponseList.size();
    }


    public class MyPlanView extends RecyclerView.ViewHolder {
        CustomPlanLayoutBinding binding;

        public MyPlanView(@NonNull CustomPlanLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    public interface onPlanClick {
        void onPlanClickedListener(int position, List<PlanResponse> planResponseList);

    }
}
