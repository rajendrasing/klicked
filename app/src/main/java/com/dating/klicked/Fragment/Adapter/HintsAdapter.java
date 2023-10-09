package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.databinding.CustomHintsLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class HintsAdapter extends RecyclerView.Adapter<HintsAdapter.HintsView> implements Filterable {
    Context context;
    List<FAQResponse> list;
    onClickHints onClickHints;
    private List<FAQResponse> exampleListFull;

    public HintsAdapter(Context context, List<FAQResponse> list, onClickHints onClickHints) {
        this.context = context;
        this.list = list;
        this.onClickHints = onClickHints;
        exampleListFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public HintsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomHintsLayoutBinding binding = CustomHintsLayoutBinding.inflate(inflater, parent, false);

        return new HintsView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HintsView holder, int position) {
        holder.binding.textTitle.setText(list.get(position).getQuestion());
        holder.binding.textDes.setText(list.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHints.onClickHintsListener(list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FAQResponse> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (FAQResponse item : exampleListFull) {
                    if (item.getQuestion().toLowerCase().trim().contains(filterPattern) || item.getDescription().toLowerCase().trim().contains(filterPattern) ) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class HintsView extends RecyclerView.ViewHolder {
        CustomHintsLayoutBinding binding;

        public HintsView(@NonNull CustomHintsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public interface onClickHints {
        void onClickHintsListener(String quesId);
    }
}
