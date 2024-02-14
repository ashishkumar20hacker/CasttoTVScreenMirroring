package com.ide.codekit.casttotv.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

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

        binding.bottomNav.setOnMenuItemSelectedListener(new CustomBottomNav.OnMenuItemSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                binding.bottomNav.onSelected(itemId);
                switch (itemId) {
                    case 0:
                        Toast.makeText(MainActivity.this, "0", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        binding.bottomNav.populate(modelList);

    }
}