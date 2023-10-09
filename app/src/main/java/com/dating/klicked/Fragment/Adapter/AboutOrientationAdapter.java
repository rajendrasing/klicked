package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.ResponseRepo.SubHintsResponse;
import com.dating.klicked.Model.SelectedSexualOrientationModel;
import com.dating.klicked.databinding.CustomAboutSexualLayoutBinding;
import com.dating.klicked.databinding.CustomHintsLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class AboutOrientationAdapter extends RecyclerView.Adapter<AboutOrientationAdapter.AboutOrientationView> {
    Context context;
    ArrayList<SelectedSexualOrientationModel> list;
    public static final List<String> selectedList = new ArrayList<String>();
    onAboutOrientationClick orientationClick;


    public AboutOrientationAdapter(Context context, ArrayList<SelectedSexualOrientationModel> list, onAboutOrientationClick orientationClick) {
        this.context = context;
        this.list = list;
        this.orientationClick = orientationClick;
    }

    @NonNull
    @Override
    public AboutOrientationView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomAboutSexualLayoutBinding binding = CustomAboutSexualLayoutBinding.inflate(inflater, parent, false);

        return new AboutOrientationView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutOrientationView holder, int position) {
        selectedList.clear();
        if (list.get(position).isChecked() == true) {
            holder.binding.textName.setText(list.get(position).getName());
            holder.binding.imgDelete.setVisibility(View.VISIBLE);
            selectedList.add(list.get(position).getName());
            holder.binding.textName.getBackground().setTint(Color.parseColor("#db6e8f"));

        } else {
            holder.binding.textName.setText(list.get(position).getName());
            holder.binding.imgDelete.setVisibility(View.GONE);
            holder.binding.textName.getBackground().setTint(Color.parseColor("#bbbbbb"));

        }

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).isChecked() == true) {
                    list.set(position, new SelectedSexualOrientationModel(list.get(position).getName(), false));
                    holder.binding.textName.setText(list.get(position).getName());
                    holder.binding.imgDelete.setVisibility(View.GONE);
                    holder.binding.textName.getBackground().setTint(Color.parseColor("#bbbbbb"));
                }
            }
        });

        holder.binding.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).isChecked() == false) {
                    list.set(position, new SelectedSexualOrientationModel(list.get(position).getName(), true));
                    holder.binding.textName.setText(list.get(position).getName());
                    holder.binding.imgDelete.setVisibility(View.VISIBLE);
                    holder.binding.textName.getBackground().setTint(Color.parseColor("#db6e8f"));

                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orientationClick.onGetAboutOrientationData(list, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class AboutOrientationView extends RecyclerView.ViewHolder {
        CustomAboutSexualLayoutBinding binding;

        public AboutOrientationView(@NonNull CustomAboutSexualLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public interface onAboutOrientationClick {
        void onGetAboutOrientationData(ArrayList<SelectedSexualOrientationModel> list, int position);

    }
}
