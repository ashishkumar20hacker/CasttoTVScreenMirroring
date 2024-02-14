package com.ide.codekit.casttotv.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ide.codekit.casttotv.Extras.CustomBottomNav;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.Model.CustomBottomNavModel;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<CustomBottomNavModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        modelList.add(new CustomBottomNavModel(0,R.drawable.cast_btm_nav, getString(R.string.cast_to_tv)));
        modelList.add(new CustomBottomNavModel(1,R.drawable.screen_mirror_btm_nav, getString(R.string.screen_mirroring)));
        modelList.add(new CustomBottomNavModel(2,R.drawable.video_btm_nav, getString(R.string.video_player)));

        binding.bottomNav.populate(modelList);

    }
}