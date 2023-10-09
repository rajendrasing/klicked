package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.ChatResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomChatLayoutBinding;
import com.dating.klicked.databinding.CustomSpeedchatHistoryBinding;
import com.dating.klicked.utils.AppUtils;

import java.util.List;

public class SpeedChatHistoryAdapter extends RecyclerView.Adapter<SpeedChatHistoryAdapter.SpeedChatHistoryView> {
    Context context;
    ChatResponse chatResponse;
    private SpeedChatHistoryViewClicked chatViewClicked;
    String UserId;

    public SpeedChatHistoryAdapter(Context context, ChatResponse chatResponse, SpeedChatHistoryViewClicked chatViewClicked, String userId) {
        this.context = context;
        this.chatResponse = chatResponse;
        this.chatViewClicked = chatViewClicked;
        this.UserId = userId;
    }

    @NonNull
    @Override
    public SpeedChatHistoryView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomSpeedchatHistoryBinding binding = CustomSpeedchatHistoryBinding.inflate(inflater, parent, false);

        return new SpeedChatHistoryView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeedChatHistoryView holder, int position) {
        if (chatResponse.getResult().get(position).getUserId().getId().equalsIgnoreCase(UserId)) {
            if (chatResponse.getResult().get(position).getIdReceiver().getFirstName() != null) {
                holder.binding.name.setText(chatResponse.getResult().get(position).getIdReceiver().getFirstName());
            }


        } else {
            if (chatResponse.getResult().get(position).getUserId().getFirstName() != null) {
                holder.binding.name.setText(chatResponse.getResult().get(position).getUserId().getFirstName());
            }


        }


        if (chatResponse.getResult().get(position).getOutgoingMessages().size() > 0) {
            holder.binding.txtLastChat.setText(chatResponse.getResult().get(position).getOutgoingMessages().get((chatResponse.getResult().get(position).getOutgoingMessages().size() - 1)).getOutgoingMessage());
            holder.binding.time.setText(AppUtils.getDateTime(chatResponse.getResult().get(position).getOutgoingMessages().get((chatResponse.getResult().get(position).getOutgoingMessages().size() - 1)).getCreatedAt()));
        } else {
            holder.binding.txtLastChat.setText("Send Your First Message");
            holder.binding.time.setText("12:00 PM");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatResponse.getResult().get(position).getUserId().getId().equalsIgnoreCase(UserId)) {
                    chatViewClicked.onSpeedChatHistoryViewClickedListener(chatResponse.getResult().get(position).getId(), chatResponse.getResult().get(position).getIdReceiver().getFirstName()
                            , chatResponse.getResult().get(position).getIdReceiver().getId());
                } else {
                    chatViewClicked.onSpeedChatHistoryViewClickedListener(chatResponse.getResult().get(position).getId(), chatResponse.getResult().get(position).getUserId().getFirstName()
                            , chatResponse.getResult().get(position).getUserId().getId());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return chatResponse.getResult().size();
    }


    public class SpeedChatHistoryView extends RecyclerView.ViewHolder {
        CustomSpeedchatHistoryBinding binding;

        public SpeedChatHistoryView(@NonNull CustomSpeedchatHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface SpeedChatHistoryViewClicked {
        void onSpeedChatHistoryViewClickedListener(String ChatId, String Name, String userId);

    }
}
