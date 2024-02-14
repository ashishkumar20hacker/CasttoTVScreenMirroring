package com.ide.codekit.casttotv.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ide.codekit.casttotv.Extras.Constants;
import com.ide.codekit.casttotv.Extras.SharePreferences;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    SharePreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = new SharePreferences(this);
        Utils.applyGradientOnTv(binding.appNameTv, "#43E97B", "#38F9D7");

        nextActivity();

    }

    private void nextActivity() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {

            if (preferences.getBoolean(Constants.isFirstRun)) {
                Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                startActivity(intent);
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }

        }, 2000);
    }
}