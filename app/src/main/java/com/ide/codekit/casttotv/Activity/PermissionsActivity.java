package com.ide.codekit.casttotv.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivityPermissionsBinding;

public class PermissionsActivity extends AppCompatActivity {

    ActivityPermissionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivityPermissionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Utils.applyGradientOnTv(binding.titleTv, "#43E97B", "#38F9D7");
        Utils.applyGradientOnTv(binding.grantPermissionTv, "#43E97B", "#38F9D7");

        if (Utils.isStoragePermissionGranted(this)) {
            binding.tickIv.setVisibility(View.VISIBLE);
            binding.grantPermissionTv.setText(getString(R.string.next));
        } else {
            binding.tickIv.setVisibility(View.GONE);
            binding.grantPermissionTv.setText(getString(R.string.grant_permission));
        }

        binding.linearLayout.setOnClickListener(view -> {
            if (!Utils.isStoragePermissionGranted(this)) {
                Utils.requestStoragePermission(this);
            }
        });

        binding.checkbox.setOnClickListener(view -> {
            if (!Utils.isStoragePermissionGranted(this)) {
                Utils.requestStoragePermission(this);
            }
        });

        binding.grantPermissionTv.setOnClickListener(view -> {
            if (Utils.isStoragePermissionGranted(this)) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            } else {
                Utils.requestStoragePermission(this);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Utils.STORAGE_PERMISSION_REQ_CODE) {
            if (Utils.isStoragePermissionGranted(this)) {
                binding.tickIv.setVisibility(View.VISIBLE);
                binding.grantPermissionTv.setText(getString(R.string.next));
            } else {
                binding.tickIv.setVisibility(View.GONE);
                binding.grantPermissionTv.setText(getString(R.string.grant_permission));
            }
        }
    }
}