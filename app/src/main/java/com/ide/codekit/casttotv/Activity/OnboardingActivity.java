package com.ide.codekit.casttotv.Activity;

import static com.ide.codekit.casttotv.Extras.Utils.makeStatusBarTransparent2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.ide.codekit.casttotv.Adapter.OnBoardingAdapter;
import com.ide.codekit.casttotv.Model.ObDataModel;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivityOnboardingBinding;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    ActivityOnboardingBinding binding;
    OnBoardingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeStatusBarTransparent2(OnboardingActivity.this);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new OnBoardingAdapter(new OnBoardingAdapter.OnNextBtnClickListener() {
            @Override
            public void onClick() {
                if (binding.viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                    binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(OnboardingActivity.this, PermissionsActivity.class));
                }
            }
        });
        adapter.submitList(getBoardList());
        binding.viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

    }

    private List<ObDataModel> getBoardList() {
        List<ObDataModel> list = new ArrayList<>();


        list.add(new ObDataModel(getString(R.string.title_1), getString(R.string.desc_1), R.drawable.ob_one_dots, 1));
        list.add(new ObDataModel(getString(R.string.title_2), getString(R.string.desc_2), R.drawable.ob_two_dots, 2));
        list.add(new ObDataModel(getString(R.string.title_3), getString(R.string.desc_3), R.drawable.ob_three_dots, 3));

        return list;
    }

    @Override
    public void onBackPressed() {
        if (binding.viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() - 1);
        }
    }
}