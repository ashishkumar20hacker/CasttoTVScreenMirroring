package com.ide.codekit.casttotv.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivityMirroringWebBrowserBinding;

public class MirroringWebBrowserActivity extends AppCompatActivity {

    ActivityMirroringWebBrowserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivityMirroringWebBrowserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBt.setOnClickListener(view -> onBackPressed());

        binding.startBt.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WebViewActivity.class).putExtra("intent_name","web")));

    }
}