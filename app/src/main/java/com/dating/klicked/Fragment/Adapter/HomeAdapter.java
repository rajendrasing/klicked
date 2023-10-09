package com.dating.klicked.Fragment.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.dating.klicked.Authentication.Adapter.CardAdapter;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.CardSubCardResponse;
import com.dating.klicked.Model.ResponseRepo.HomeResponse;
import com.dating.klicked.Model.ResponseRepo.MainCardResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.databinding.CustomHomeLayoutBinding;
import com.dating.klicked.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHomeViewHolder> implements PreviewCardAdapter.onPreviewCardClick {

    Context context;
    HomeResponse list;
    onHomeClick onHomeClick;
    List<CardResponse> cardResponseList;
    ArrayList<Boolean> booleanArrayList = new ArrayList<Boolean>();
    ArrayList<Boolean> aDDArrayList = new ArrayList<Boolean>();
    private Dialog subDialog;

    public HomeAdapter(Context context, HomeResponse list, onHomeClick onHomeClick) {
        this.context = context;
        this.list = list;
        this.onHomeClick = onHomeClick;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public MyHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CustomHomeLayoutBinding binding = CustomHomeLayoutBinding.inflate(inflater, parent, false);


        return new MyHomeViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyHomeViewHolder holder, int position) {
        booleanArrayList.add(false);
        aDDArrayList.add(false);
        holder.binding.name.setText(list.getResult().get(position).getFirstName());
        if (list.getResult().get(position).getProfileImage().equalsIgnoreCase("https://stargazeevents.s3.ap-south-1.amazonaws.com/pfiles/profile.png")) {

            holder.binding.imgProfile.setImageDrawable(context.getResources().getDrawable(R.mipmap.profile1));
        } else {
            Glide.with(context).load(PrefConf.IMAGE_URL + list.getResult().get(position).getProfileImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.binding.imgProfile);
        }

        String currentString = list.getResult().get(position).getDob();

        if (currentString != null) {
            String[] separated = currentString.split("/");
            String age = AppUtils.getAge(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));

            if (list.getResult().get(position).getAddress().size() > 0 && list.getResult().get(position).getAddress() != null) {
                holder.binding.location.setVisibility(View.VISIBLE);
                holder.binding.location.setText(list.getResult().get(position).getAddress().get(0).getCity() + "," + list.getResult().get(position).getAddress().get(0).getState() + "," + list.getResult().get(position).getAddress().get(0).getCountry() + "   " + age + " yr");
            } else {
                holder.binding.location.setVisibility(View.INVISIBLE);
            }
        } else {
            if (list.getResult().get(position).getAddress().size() > 0 && list.getResult().get(position).getAddress() != null) {
                holder.binding.location.setVisibility(View.VISIBLE);

                holder.binding.location.setText(list.getResult().get(position).getAddress().get(0).getCity() + "," + list.getResult().get(position).getAddress().get(0).getState() + "," + list.getResult().get(position).getAddress().get(0).getCountry());

            } else {
                holder.binding.location.setVisibility(View.INVISIBLE);
            }
        }

        if (list.getResult().get(position).getBio() != null) {
            holder.binding.txtDescription.setVisibility(View.VISIBLE);
            holder.binding.textSeeMore.setVisibility(View.VISIBLE);
            holder.binding.txtDescription.setText(list.getResult().get(position).getBio());
        } else {
            holder.binding.txtDescription.setVisibility(View.GONE);
            holder.binding.textSeeMore.setVisibility(View.GONE);
        }

        if (list.getResult().get(position).getMaincardsId()!=null && list.getResult().get(position).getMaincardsId().size() > 0) {
            getAllUserCard(context, list.getResult().get(position).getId(), holder.binding.cardRecycler);
            holder.binding.cardRecycler.setVisibility(View.VISIBLE);
        }else {
            holder.binding.cardRecycler.setVisibility(View.GONE);
        }


        if (list.getResult().get(position).getAudioDescription() != null) {
            holder.binding.myAudio.setVisibility(View.VISIBLE);
            holder.binding.relative1.setVisibility(View.VISIBLE);
        } else {
            holder.binding.myAudio.setVisibility(View.GONE);
            holder.binding.relative1.setVisibility(View.GONE);
        }
        holder.binding.imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.imgPause.setVisibility(View.GONE);
                holder.binding.imgPlay.setVisibility(View.VISIBLE);
                onHomeClick.onPauseFavMusicClickListener(holder.binding.waveimage);
            }
        });

        holder.binding.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.binding.imgPause.setVisibility(View.VISIBLE);
                holder.binding.imgPlay.setVisibility(View.GONE);
                onHomeClick.onPlayFavMusicClickListener(list.getResult().get(position).getAudioDescription(), holder.binding.imgPlay, holder.binding.imgPause, view);
            }
        });

        holder.binding.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHomeClick.onPopMenuClickListener(view, list.getResult().get(position).getId(), false);
            }
        });

        holder.binding.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (booleanArrayList.get(position) == false) {
                    booleanArrayList.add(position, true);
              */
                onHomeClick.onFavClickedListener(view, list.getResult().get(position).getId(), true /*booleanArrayList.get(position)*/);
                /*} else {
                    booleanArrayList.add(position, false);

                    onHomeClick.onFavClickedListener(view, list.getResult().get(position).getId(), booleanArrayList.get(position));
                }*/

            }
        });

        holder.binding.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aDDArrayList.get(position) == false) {
                    aDDArrayList.add(position, true);
                    onHomeClick.onSendRequestClickedListener(view, list.getResult().get(position).getId(), aDDArrayList.get(position));
                } else {
                    aDDArrayList.add(position, false);
                    onHomeClick.onSendRequestClickedListener(view, list.getResult().get(position).getId(), aDDArrayList.get(position));
                }

            }
        });

        holder.binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHomeClick.onShareProfileUser(list.getResult().get(position).getId());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHomeClick.onPopMenuClickListener(view, list.getResult().get(position).getId(), true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.getResult().size();
    }

    @Override
    public void onPreviewCardClickListener(List<CardSubCardResponse.Result.Subcard> subcard) {
        subDialog = new Dialog(context);


        subDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        subDialog.setCancelable(true);
        subDialog.setContentView(R.layout.custom_show_subcard_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(subDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        subDialog.getWindow().setAttributes(lp);
        subDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        RecyclerView recyclerView = (RecyclerView) subDialog.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context.getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new PreviewShowSubCardAdapter(context.getApplicationContext(), subcard));

        subDialog.show();
    }

    public class MyHomeViewHolder extends RecyclerView.ViewHolder {
        CustomHomeLayoutBinding binding;


        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyHomeViewHolder(@NonNull CustomHomeLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


          /*  itemView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                    onHomeClick.onGetHomePosition(getAdapterPosition());
                    Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();


                }
            });*/

        }
    }

    public interface onHomeClick {
        void onGetHomePosition(int position);

        void onPlayFavMusicClickListener(String song, ImageView play, ImageView pause, View view);

        void onPauseFavMusicClickListener(ImageView imageView);

        void onPopMenuClickListener(View view, String userid, Boolean aBoolean);

        void onFavClickedListener(View view, String userid, Boolean aBoolean);

        void onSendRequestClickedListener(View view, String receiverId, Boolean aBoolean);

        void onShareProfileUser(String userid);

    }

    public void GetCardByUserId(Context context, String userId, RecyclerView cardRecycler) {
        Call<CardSubCardResponse> userCall = AppUtils.KlickedApi(context).getUserCardsById(userId);
        userCall.enqueue(new Callback<CardSubCardResponse>() {
            @Override
            public void onResponse(Call<CardSubCardResponse> call, Response<CardSubCardResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    if (response.message().equalsIgnoreCase("ok") && response.body().getResult().size() > 0) {
                     /*   RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        cardRecycler.setHasFixedSize(true);
                        cardRecycler.setLayoutManager(layoutManager1);
                        cardRecycler.setItemAnimator(new DefaultItemAnimator());
                        cardRecycler.setAdapter(new PreviewCardAdapter(context, response.body(), HomeAdapter.this));
*/
                    }
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject object = new JSONObject(errorRes);
                        String err_msg = object.getString("error");
                        Log.d("err_msg", err_msg);


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<CardSubCardResponse> call, Throwable t) {
                Log.d("err_msg", t.getLocalizedMessage());

            }
        });
    }

    public void getAllUserCard(Context context, String userId,RecyclerView cardRecycler) {
        Call<MainCardResponse> call = AppUtils.KlickedApi(context).MAIN_CARD_RESPONSE_CALL(userId);

        call.enqueue(new Callback<MainCardResponse>() {
            @Override
            public void onResponse(Call<MainCardResponse> call, Response<MainCardResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    if (response.message().equalsIgnoreCase("ok") && response.body().getCards().size() > 0) {
                        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        cardRecycler.setHasFixedSize(true);
                        cardRecycler.setLayoutManager(layoutManager1);
                        cardRecycler.setItemAnimator(new DefaultItemAnimator());
                        cardRecycler.setAdapter(new PreviewCardAdapter(context, response.body()));

                    }
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorRes);
                        String err_msg = jsonObject.getString("error");
                        Log.d("err_msg", err_msg);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MainCardResponse> call, Throwable t) {
                Log.d("err_msg", t.getLocalizedMessage());

            }
        });

    }


}
