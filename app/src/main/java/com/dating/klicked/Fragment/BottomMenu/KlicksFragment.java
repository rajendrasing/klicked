package com.dating.klicked.Fragment.BottomMenu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.klicked.Fragment.Klicked.PendingFragment;
import com.dating.klicked.Fragment.Klicked.RequestFragment;
import com.dating.klicked.Fragment.Klicked.klicked_Fragment;
import com.dating.klicked.R;
import com.dating.klicked.databinding.FragmentKlicksBinding;
import com.dating.klicked.utils.AppUtils;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


public class KlicksFragment extends Fragment {

    FragmentKlicksBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;

    public KlicksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_klicks, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add("klicks", klicked_Fragment.class)
                .add("Pending", PendingFragment.class)
                .add("Request", RequestFragment.class)
                .create());

        binding.viewpager.setAdapter(adapter);
        binding.viewpagertab.setupWithViewPager(binding.viewpager);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }
}