package com.dating.klicked.Fragment.SideMenu;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Fragment.Adapter.ChatAdapter;
import com.dating.klicked.Fragment.Adapter.HotelAdapter;
import com.dating.klicked.Model.ResponseRepo.HotelResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.ViewPresenter.ChatPresenter;
import com.dating.klicked.ViewPresenter.HotelPresenter;
import com.dating.klicked.databinding.FragmentHotelBinding;
import com.dating.klicked.utils.AppUtils;
import com.irozon.sneaker.Sneaker;

import java.util.List;

public class HotelFragment extends Fragment implements HotelPresenter.HotelView, SwipeRefreshLayout.OnRefreshListener, HotelAdapter.HotelViewClicked {
    FragmentHotelBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    private HotelPresenter presenter;
    CharSequence search = "";

    public HotelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        presenter = new HotelPresenter(this);

        presenter.getAllHotel(getContext());

     
        binding.SwipeRefresh.setOnRefreshListener(this);
        binding.SwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.global__primary));
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }


    @Override
    public void onRefresh() {
        presenter.getAllHotel(getContext());


        binding.SwipeRefresh.setRefreshing(false);

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
        Sneaker.with(getActivity())
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onGetAllHotelSuccess(List<HotelResponse> hotelResponseList, String message) {
        if (message.equalsIgnoreCase("ok") && hotelResponseList.size()>0 && hotelResponseList!=null){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.hotelRecycler.setHasFixedSize(true);
            binding.hotelRecycler.setLayoutManager(layoutManager);
            binding.hotelRecycler.setItemAnimator(new DefaultItemAnimator());
            HotelAdapter chatAdapter = new HotelAdapter(getContext(), hotelResponseList, this);
            binding.hotelRecycler.setAdapter(chatAdapter);
            binding.hotelRecycler.setVisibility(View.VISIBLE);
            binding.imdNoDta.setVisibility(View.GONE);
            binding.unshowText.setVisibility(View.GONE);

            binding.edSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    chatAdapter.getFilter().filter(s);
                    search = s;
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else {
            binding.hotelRecycler.setVisibility(View.GONE);
            binding.imdNoDta.setVisibility(View.VISIBLE);
            binding.unshowText.setVisibility(View.VISIBLE);
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
    public void onHotelViewClickedListener(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        navController.navigate(R.id.action_hotelFragment_to_hotelInfoFragment,bundle);

    }
}