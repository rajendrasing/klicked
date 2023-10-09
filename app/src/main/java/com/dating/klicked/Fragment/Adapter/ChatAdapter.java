package com.dating.klicked.Fragment.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Model.ResponseRepo.ChatResponse;
import com.dating.klicked.Model.ResponseRepo.KlickedResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomChatLayoutBinding;
import com.dating.klicked.databinding.CustomChatLayoutBinding;
import com.dating.klicked.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatView> implements Filterable {
    Context context;
    ChatResponse chatResponse;
    private ChatViewClicked chatViewClicked;
    private List<ChatResponse.Result> exampleListFull;
    String UserId;


    public ChatAdapter(Context context, ChatResponse chatResponse, ChatViewClicked chatViewClicked, String UserId) {
        this.context = context;
        this.chatResponse = chatResponse;
        this.chatViewClicked = chatViewClicked;
        this.UserId = UserId;
        exampleListFull = new ArrayList<>(chatResponse.getResult());
    }

    @NonNull
    @Override
    public ChatView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomChatLayoutBinding binding = CustomChatLayoutBinding.inflate(inflater, parent, false);

        return new ChatView(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ChatView holder, int position) {

        if (chatResponse.getResult().get(position).getUserId().getId().equalsIgnoreCase(UserId)) {
            if (chatResponse.getResult().get(position).getIdReceiver().getFirstName() != null) {
                holder.binding.name.setText(chatResponse.getResult().get(position).getIdReceiver().getFirstName());
            }



            if (chatResponse.getResult().get(position).getIdReceiver().getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

                holder.binding.imgProfile.setImageDrawable(context.getResources().getDrawable(R.mipmap.profile1));
            } else {
                Glide.with(context).load(PrefConf.IMAGE_URL + chatResponse.getResult().get(position).getIdReceiver().getProfileImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.binding.imgProfile);
            }
        } else {
            if (chatResponse.getResult().get(position).getUserId().getFirstName() != null) {
                holder.binding.name.setText(chatResponse.getResult().get(position).getUserId().getFirstName());
            }



            if (chatResponse.getResult().get(position).getUserId().getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

                holder.binding.imgProfile.setImageDrawable(context.getResources().getDrawable(R.mipmap.profile1));
            } else {
                Glide.with(context).load(PrefConf.IMAGE_URL + chatResponse.getResult().get(position).getUserId().getProfileImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.binding.imgProfile);
            }
        }


        if (chatResponse.getResult().get(position).getOutgoingMessages().size() > 0) {
            int count = 0;
            for (int i = 0; i < chatResponse.getResult().get(position).getOutgoingMessages().size(); i++) {
                if (chatResponse.getResult().get(position).getOutgoingMessages().get(i).getRead() == false && !chatResponse.getResult().get(position).getOutgoingMessages().get(i).getUserId().equalsIgnoreCase(UserId)) {
                    count++;
                }
            }
            if (count == 0) {
                holder.binding.textCount.setVisibility(View.GONE);
            } else {
                holder.binding.textCount.setText(String.valueOf(count));
                holder.binding.textCount.setVisibility(View.VISIBLE);
            }

            holder.binding.textCount.setText(String.valueOf(count));
            holder.binding.txtLastChat.setText(chatResponse.getResult().get(position).getOutgoingMessages().get((chatResponse.getResult().get(position).getOutgoingMessages().size() - 1)).getOutgoingMessage());
            holder.binding.time.setText(AppUtils.getDateTime(chatResponse.getResult().get(position).getOutgoingMessages().get((chatResponse.getResult().get(position).getOutgoingMessages().size() - 1)).getCreatedAt()));
        } else {
            holder.binding.txtLastChat.setText("Send Your First Message");
            holder.binding.time.setText("12:00 PM");
        }


        if (chatResponse.getResult().get(position).getKlickedMeter() != null) {
            holder.binding.textProgress.setText(Math.toIntExact(Math.round(chatResponse.getResult().get(position).getKlickedMeter())) + "%");
            holder.binding.progressBar.setMax(100);
            holder.binding.progressBar.setProgress(Math.toIntExact(Math.round(chatResponse.getResult().get(position).getKlickedMeter())));
        } else {
            holder.binding.textProgress.setText("0%");
            holder.binding.progressBar.setMax(100);
            holder.binding.progressBar.setProgress(0);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatResponse.getResult().get(position).getUserId().getId().equalsIgnoreCase(UserId)) {
                    chatViewClicked.onChatViewClickedListener(chatResponse.getResult().get(position).getId(), chatResponse.getResult().get(position).getIdReceiver().getFirstName()
                            , chatResponse.getResult().get(position).getIdReceiver().getProfileImage(), chatResponse.getResult().get(position).getIdReceiver().getId());
                } else {
                    chatViewClicked.onChatViewClickedListener(chatResponse.getResult().get(position).getId(), chatResponse.getResult().get(position).getUserId().getFirstName()
                            , chatResponse.getResult().get(position).getUserId().getProfileImage(), chatResponse.getResult().get(position).getUserId().getId());
                }

            }
        });

        // holder.binding.name.setText(chatResponse);
    }

    @Override
    public int getItemCount() {
        return chatResponse.getResult().size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ChatResponse.Result> filteredList = new ArrayList<ChatResponse.Result>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ChatResponse.Result item : exampleListFull) {
                    if (item.getIdReceiver().getFirstName().toLowerCase().trim().contains(filterPattern) || item.getUserId().getFirstName().toLowerCase().trim().contains(filterPattern)) {
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
            chatResponse.getResult().clear();
            chatResponse.getResult().addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ChatView extends RecyclerView.ViewHolder {
        CustomChatLayoutBinding binding;

        public ChatView(@NonNull CustomChatLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ChatViewClicked {
        void onChatViewClickedListener(String ChatId, String Name, String profile, String userId);

    }
}
