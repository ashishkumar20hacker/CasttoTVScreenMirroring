package com.ide.codekit.casttotv.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.ide.codekit.casttotv.Extras.CustomBottomNav;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.Fragments.CastToTvFragment;
import com.ide.codekit.casttotv.Fragments.ScreenMirroringFragment;
import com.ide.codekit.casttotv.Fragments.VideoPlayerFragment;
import com.ide.codekit.casttotv.Model.CustomBottomNavModel;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    int selectedID;
    List<CustomBottomNavModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedID = getIntent().getIntExtra("selectedID",0);

        modelList.add(new CustomBottomNavModel(0,R.drawable.cast_btm_nav, getString(R.string.cast_to_tv)));
        modelList.add(new CustomBottomNavModel(1,R.drawable.screen_mirror_btm_nav, getString(R.string.screen_mirroring)));
        modelList.add(new CustomBottomNavModel(2,R.drawable.video_btm_nav, getString(R.string.video_player)));

        binding.bottomNav.setOnMenuItemSelectedListener(new CustomBottomNav.OnMenuItemSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case 0:
                        changeFragment(new CastToTvFragment());
                        break;
                    case 1:
                        changeFragment(new ScreenMirroringFragment());
                        break;
                    case 2:
                        changeFragment(new VideoPlayerFragment());
                        break;
                }
            }
        });

        binding.bottomNav.populate(modelList);
        binding.bottomNav.onSelected(selectedID);
        switch (selectedID) {
            case 0:
                changeFragment(new CastToTvFragment());
                break;
            case 1:
                changeFragment(new ScreenMirroringFragment());
                break;
            case 2:
                changeFragment(new VideoPlayerFragment());
                break;
        }
    }

    private void changeFragment(Fragment explorefragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, explorefragment);
        fragmentTransaction.commit();

//        String fragmentName = explorefragment.getClass().getSimpleName();
//        Log.d("Current Fragment", "Fragment Name: " + fragmentName);
    }

}