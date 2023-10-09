package com.dating.klicked.Fragment.SideMenu;

import android.app.Dialog;
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

import com.dating.klicked.Model.ResponseRepo.SubHintsResponse;
import com.dating.klicked.R;
import com.dating.klicked.databinding.FragmentFullHintsBinding;
import com.dating.klicked.databinding.FragmentSubHintsBinding;
import com.dating.klicked.utils.AppUtils;


public class FullHintsFragment extends Fragment {
    FragmentFullHintsBinding binding;
    private Dialog dialog;
    private View view;
    NavController navController;


    public FullHintsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_full_hints, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());

        SubHintsResponse subHintsResponse = (SubHintsResponse) getArguments().getSerializable("SubHints");

        binding.textTitle.setText(subHintsResponse.getTitle());
        binding.textDes.setText(subHintsResponse.getContent());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }

}