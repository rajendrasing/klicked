package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.klicked.Model.ResponseRepo.ChatResponse;
import com.dating.klicked.Model.ResponseRepo.UserChatResponse;
import com.dating.klicked.R;
import com.dating.klicked.databinding.CustomChatItemLayoutBinding;
import com.dating.klicked.databinding.CustomChatLayoutBinding;
import com.dating.klicked.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class Chat_messageAdapter extends RecyclerView.Adapter<Chat_messageAdapter.ChatMessageView> {
    Context context;
    List<UserChatResponse.Array> response;
    String userId;

    public Chat_messageAdapter(Context context, List<UserChatResponse.Array> response, String userId) {
        this.context = context;
        this.response = response;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ChatMessageView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomChatItemLayoutBinding binding = CustomChatItemLayoutBinding.inflate(inflater, parent, false);

        return new ChatMessageView(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageView holder, int position) {
        /*if (list.get(position).equalsIgnoreCase("date")){

            holder.binding.receiverLayout.setVisibility(View.GONE);
            holder.binding.senderLayout.setVisibility(View.GONE);
            holder.binding.txtDate.setVisibility(View.VISIBLE);
        }else*/
        if (response.get(position).getUserId().equalsIgnoreCase(userId)) {
            holder.binding.receiverLayout.setVisibility(View.GONE);
            holder.binding.senderLayout.setVisibility(View.VISIBLE);
            holder.binding.txtDate.setVisibility(View.GONE);
            holder.binding.senderMessage.setText(response.get(position).getOutgoingMessage());
            if (response.get(position).getRead()==false){
                holder.binding.sendTime.setText(AppUtils.getDateTime(response.get(position).getCreatedAt()));
                holder.binding.sendTime.setCompoundDrawablesWithIntrinsicBounds(null,null,context.getResources().getDrawable(R.drawable.ic_double_unfill_check),null);
            }else {
                holder.binding.sendTime.setText(AppUtils.getDateTime(response.get(position).getCreatedAt()));
                holder.binding.sendTime.setCompoundDrawablesWithIntrinsicBounds(null,null,context.getResources().getDrawable(R.drawable.ic_double_fill_check),null);

            }
        } else if (!response.get(position).getUserId().equalsIgnoreCase(userId)) {
            holder.binding.receiverLayout.setVisibility(View.VISIBLE);
            holder.binding.senderLayout.setVisibility(View.GONE);
            holder.binding.txtDate.setVisibility(View.GONE);
            holder.binding.receiverMessage.setText(response.get(position).getOutgoingMessage());

            if (response.get(position).getRead()==false){
                holder.binding.receiverTime.setText(AppUtils.getDateTime(response.get(position).getCreatedAt()));
             //   holder.binding.sendTime.setCompoundDrawables(null,null,context.getResources().getDrawable(R.drawable.ic_double_unfill_check),null);
            }else {
                holder.binding.sendTime.setText(AppUtils.getDateTime(response.get(position).getCreatedAt()));
//                holder.binding.sendTime.setCompoundDrawables(null,null,context.getResources().getDrawable(R.drawable.ic_double_fill_check),null);

            }


        }

    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public class ChatMessageView extends RecyclerView.ViewHolder {
        CustomChatItemLayoutBinding binding;

        public ChatMessageView(@NonNull CustomChatItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void update(List<UserChatResponse.Array> response){

        notifyDataSetChanged();
    }
}
