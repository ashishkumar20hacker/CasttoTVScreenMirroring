package com.ide.codekit.casttotv.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ide.codekit.casttotv.Extras.ScreenMirroringService;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {

    ActivityHelpBinding binding;
    private boolean isMirroring = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBt.setOnClickListener(view -> onBackPressed());

        binding.startBt.setOnClickListener(view -> {
            if (isMirroring) {
                stopScreenMirroring();
            } else {
                startScreenMirroring();
            }
            isMirroring = !isMirroring;
        });
    }
    private void startScreenMirroring() {
        Intent intent = new Intent(this, ScreenMirroringService.class);
        intent.setAction("START_SCREEN_MIRRORING");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    private void stopScreenMirroring() {
        Intent intent = new Intent(this, ScreenMirroringService.class);
        intent.setAction("STOP_SCREEN_MIRRORING");
        stopService(intent);
    }
}