package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.databinding.CustomProfileItemBinding;

import java.util.ArrayList;

public class ProfileAdapter  extends RecyclerView.Adapter<ProfileAdapter.ProfileView>{
    Context context;
    ArrayList<String>list;
    OnProfileClicked onProfileClicked;

    public ProfileAdapter(Context context, ArrayList<String> list, OnProfileClicked onProfileClicked) {
        this.context = context;
        this.list = list;
        this.onProfileClicked = onProfileClicked;
    }

    @NonNull
    @Override
    public ProfileView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomProfileItemBinding binding = CustomProfileItemBinding.inflate(inflater,parent,false);

        return new ProfileView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileView holder, int position) {

        holder.binding.txt.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProfileClicked.onProfileClickedListener(position,list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProfileView extends RecyclerView.ViewHolder {

        CustomProfileItemBinding binding;

        public ProfileView(@NonNull CustomProfileItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnProfileClicked {
        void onProfileClickedListener(int position, ArrayList<String> list);
    }
}
