package com.dating.klicked.FullScreenImage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.dating.klicked.R;
import com.dating.klicked.databinding.ActivityFullscreenViewBinding;

import java.util.ArrayList;

public class Fullscreen_view extends AppCompatActivity {
    ActivityFullscreenViewBinding binding;
    public static final String URI_LIST_DATA = "URI_LIST_DATA";
    public static final String BLURSIZE = "BLURSIZE";
    public static final String CURRENTPOSITION = "CURRENTPOSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fullscreen_view);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fullscreen_view);

        ArrayList<String> imagePaths = getIntent().getStringArrayListExtra(URI_LIST_DATA);
        int blur = getIntent().getIntExtra(BLURSIZE,100);
        int position = getIntent().getIntExtra(CURRENTPOSITION,0);

        binding.viewPager.setAdapter(new FullScreenImageAdapter(this,imagePaths,blur ));
        binding.viewPager.setCurrentItem(position);
    }
}