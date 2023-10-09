package com.dating.klicked.Fragment.Profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dating.klicked.Authentication.Adapter.CardAdapter;
import com.dating.klicked.Authentication.Adapter.ShowCardAdapter;
import com.dating.klicked.Authentication.View_Flipper;
import com.dating.klicked.Fragment.Adapter.AboutOrientationAdapter;
import com.dating.klicked.Model.RequestRepo.UpdateProfile;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.MainCardResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.CardPresenter;
import com.dating.klicked.databinding.FragmentCardBinding;
import com.dating.klicked.databinding.FragmentProfileBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.ItemMoveCallback;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;


public class CardFragment extends Fragment implements CardPresenter.CardView, CardAdapter.onCardClick, View.OnClickListener {

    FragmentCardBinding binding;
    private Dialog dialog;
    private View view;
    NavController navController;
    CardPresenter presenter;
    User_Data userData;
    public static final ArrayList<String> SelectCardName = new ArrayList<String>();
    public static final ArrayList<String> SelectCardIcon = new ArrayList<String>();
    public static final ArrayList<String> SelectCardBackImg = new ArrayList<String>();

    ArrayList<String> card = new ArrayList<String>();


    public CardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_card, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();

        presenter = new CardPresenter(this);
        presenter.getAllUserCard(getContext(), userData.getId());
        binding.txtCardNext.setOnClickListener(this);
        //  presenter.getAllCard(getContext());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    dialog.dismiss();
                }


            }, 1000);

        }
    }

    @Override
    public void onError(String message) {
        if (message.equalsIgnoreCase("Card Not Exist")) {
            presenter.getAllCard(getContext());
        } else {
            Sneaker.with(getActivity())
                    .setTitle(message)
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakError();

        }

    }

    @Override
    public void onGetCardSuccess(List<CardResponse> cardResponses, String message) {
        ArrayList<String> cardName = new ArrayList<String>();
        cardName.clear();
        if (message.equalsIgnoreCase("ok")) {

            CardAdapter.integers.clear();
            for (int i = 0; i < cardResponses.size(); i++) {
                cardName.add(i, cardResponses.get(i).getName());
            }

            for (String tempList : cardName)    //tempList is  a variable
                CardAdapter.integers.add(SelectCardName.contains(tempList) ? true : false);


            Log.d("njvsksj", CardAdapter.integers.toString());
            CardAdapter cardAdapter = new CardAdapter(cardResponses, getContext(), this, true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
            binding.cardRecycler.setLayoutManager(layoutManager);
            binding.cardRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.cardRecycler.setHasFixedSize(true);
            binding.cardRecycler.setAdapter(cardAdapter);


        }
    }

    @Override
    public void onGetUSerCardSuccess(MainCardResponse cardResponses, String message) {
        SelectCardName.clear();
        SelectCardIcon.clear();
        SelectCardBackImg.clear();
        CardAdapter.CardId.clear();
        if (message.equalsIgnoreCase("ok")) {
            for (int i = 0; i < cardResponses.getCards().size(); i++) {
                SelectCardName.add(cardResponses.getCards().get(i).getName());
                SelectCardIcon.add(cardResponses.getCards().get(i).getIcon());
                SelectCardBackImg.add(cardResponses.getCards().get(i).getBackgroundImg());
            }
            ShowCardAdapter cardAdapter = new ShowCardAdapter(SelectCardName, SelectCardBackImg, SelectCardIcon, getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            linearLayoutManager.setStackFromEnd(true);
            cardAdapter.notifyDataSetChanged();
            ItemTouchHelper.Callback callback =
                    new ItemMoveCallback(cardAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(binding.selectedCardRecycler);

            cardAdapter.update(CardAdapter.subCardName, CardAdapter.subCardBackImg, CardAdapter.subCardIcon);
            binding.selectedCardRecycler.setLayoutManager(linearLayoutManager);
            binding.selectedCardRecycler.setHasFixedSize(true);
            binding.selectedCardRecycler.setAdapter(cardAdapter);

            presenter.getAllCard(getContext());
        }

        Log.d("SelectCardName", SelectCardName.toString());
    }

    @Override
    public void onUpdateCardUser(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("successfully update card ")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            presenter.getAllUserCard(getContext(), userData.getId());


        }
    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(getActivity())
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onCardClickListener(ArrayList<String> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ShowCardAdapter cardAdapter = new ShowCardAdapter(SelectCardName, SelectCardBackImg, SelectCardIcon, getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                linearLayoutManager.setStackFromEnd(true);
                cardAdapter.notifyDataSetChanged();
                cardAdapter.update(CardAdapter.subCardName, CardAdapter.subCardBackImg, CardAdapter.subCardIcon);
                ItemTouchHelper.Callback callback =
                        new ItemMoveCallback(cardAdapter);
                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                touchHelper.attachToRecyclerView(binding.selectedCardRecycler);
                binding.selectedCardRecycler.setLayoutManager(linearLayoutManager);
                binding.selectedCardRecycler.setHasFixedSize(true);
                binding.selectedCardRecycler.setAdapter(cardAdapter);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_card_next:
                Log.d("jvjkkscvk", CardAdapter.CardId.toString());
                removeDuplicates(CardAdapter.CardId);
                UpdateProfile profile = new UpdateProfile(null, null, null, userData.getPhoneNo(), null, null, null, null, null, null, null, null, CardAdapter.CardId);
                presenter.UpdateUserCard(getContext(), profile);
                break;
        }
    }

    private void removeDuplicates(List<?> list)
    {
        int count = list.size();

        for (int i = 0; i < count; i++)
        {
            for (int j = i + 1; j < count; j++)
            {
                if (list.get(i).equals(list.get(j)))
                {
                    list.remove(j--);
                    count--;
                }
            }
        }
    }
}