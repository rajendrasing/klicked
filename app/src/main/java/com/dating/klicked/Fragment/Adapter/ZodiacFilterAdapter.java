package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.Zodiac_filter_model;
import com.dating.klicked.R;
import com.dating.klicked.databinding.CustomViewProfileLayoutBinding;
import com.dating.klicked.databinding.CustomZodiacFilterLayoutBinding;

import java.util.ArrayList;

public class ZodiacFilterAdapter extends RecyclerView.Adapter<ZodiacFilterAdapter.ZodiacFilterView> {
    Context context;
    ArrayList<Zodiac_filter_model> list;
    OnZodiacFilterViewClicked clicked;
    private int selectedPosition = -1;

    public ZodiacFilterAdapter(Context context, ArrayList<Zodiac_filter_model> list, OnZodiacFilterViewClicked clicked) {
        this.context = context;
        this.list = list;
        this.clicked = clicked;
    }

    @NonNull
    @Override
    public ZodiacFilterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomZodiacFilterLayoutBinding binding = CustomZodiacFilterLayoutBinding.inflate(inflater, parent, false);

        return new ZodiacFilterView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ZodiacFilterView holder, int position) {
        holder.binding.img.setImageResource(list.get(position).getIcon());
        holder.binding.txtName.setText(list.get(position).getName());
        if (selectedPosition == position) {
            holder.itemView.setSelected(true); //using selector drawable
            holder.binding.card.setStrokeColor(context.getResources().getColor(R.color.status_bar));
            holder.binding.img.setColorFilter(context.getResources().getColor(R.color.status_bar));
            holder.binding.txtName.setTextColor(context.getResources().getColor(R.color.status_bar));
        } else {
            holder.itemView.setSelected(false);
            holder.binding.card.setStrokeColor(context.getResources().getColor(R.color.un_select));
            holder.binding.img.setColorFilter(context.getResources().getColor(R.color.un_select));
            holder.binding.txtName.setTextColor(context.getResources().getColor(R.color.un_select));
        }
        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            holder.binding.card.setStrokeColor(context.getResources().getColor(R.color.status_bar));
            holder.binding.img.setColorFilter(context.getResources().getColor(R.color.status_bar));
            holder.binding.txtName.setTextColor(context.getResources().getColor(R.color.status_bar));
            clicked.onZodiacFilterClickListener(list.get(position).getName());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ZodiacFilterView extends RecyclerView.ViewHolder {

        CustomZodiacFilterLayoutBinding binding;

        public ZodiacFilterView(@NonNull CustomZodiacFilterLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnZodiacFilterViewClicked {
        void onZodiacFilterClickListener(String name);
    }

    public void refreshView(int position) {
        notifyItemChanged(position);
    }
}
