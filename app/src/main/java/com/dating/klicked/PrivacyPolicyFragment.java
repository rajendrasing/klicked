package com.dating.klicked;

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
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dating.klicked.databinding.FragmentPrivacyPolicyBinding;
import com.dating.klicked.utils.AppUtils;

public class PrivacyPolicyFragment extends Fragment {

    FragmentPrivacyPolicyBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    NavController navController;
    String url = "www.klicked.co/privacy#ques14";


    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_attendance, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_privacy_policy, container, false);
        view = binding.getRoot();


//        binding.webData.loadDataWithBaseURL(
//                "",
//                url,
//                "text/html",
//                "UTF-8",
//                null
//        );

        binding.webData.getSettings().setJavaScriptEnabled(true);
        binding.webData.getSettings().setDomStorageEnabled(true);
        //binding.webData.addJavascriptInterface(new MyJavaScriptInterface(this), "ShoppingCartAnalyser");


        binding.webData.setWebViewClient(new WebViewClient() {

            private void loadEvent(String javascript) {
                binding.webData.loadUrl("javascript:" + javascript);
            }
        });

        binding.webData.loadUrl(url);

        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }
}