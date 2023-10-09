package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.databinding.CustomAboutSexualLayoutBinding;
import com.dating.klicked.databinding.CustomCheckOrientationLayoutBinding;

import java.util.ArrayList;

public class filterOrientationAdapter extends RecyclerView.Adapter<filterOrientationAdapter.filterOrientationView> {
    Context context;
    ArrayList<String> list;
    public static  ArrayList<String> selectedOrientation = new ArrayList<String>();

    public filterOrientationAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public filterOrientationView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomCheckOrientationLayoutBinding binding = CustomCheckOrientationLayoutBinding.inflate(inflater, parent, false);

        return new filterOrientationView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull filterOrientationView holder, int position) {
        selectedOrientation.clear();
        holder.binding.rdWomen.setText(list.get(position));
        holder.binding.rdWomen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    selectedOrientation.add(list.get(position));
                } else {
                    selectedOrientation.remove(list.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class filterOrientationView extends RecyclerView.ViewHolder {
        CustomCheckOrientationLayoutBinding binding;

        public filterOrientationView(@NonNull CustomCheckOrientationLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
