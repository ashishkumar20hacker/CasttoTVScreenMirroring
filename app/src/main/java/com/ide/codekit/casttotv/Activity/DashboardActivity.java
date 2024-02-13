package com.ide.codekit.casttotv.Activity;

import static com.ide.codekit.casttotv.Extras.Utils.rateApp;
import static com.ide.codekit.casttotv.Extras.Utils.shareApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.ide.codekit.casttotv.Extras.Constants;
import com.ide.codekit.casttotv.Extras.SharePreferences;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    SharePreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = new SharePreferences(this);
        preferences.putBoolean(Constants.isFirstRun,false);
        Utils.applyGradientOnTv(binding.appNameTv, "#43E97B", "#38F9D7");
        Utils.applyGradientOnTv(binding.castToTvBt, "#43E97B", "#38F9D7");
        Utils.applyGradientOnTv(binding.videoPlayerBt, "#43E97B", "#38F9D7");
        Utils.applyGradientOnTv(binding.screenMirroringBt, "#43E97B", "#38F9D7");
        Utils.applyGradientOnTv(binding.shareAppBt, "#43E97B", "#38F9D7");

        binding.menubt.setOnClickListener(view -> binding.drawLay.openDrawer(GravityCompat.START));
        binding.closeDrawer.setOnClickListener(view -> binding.drawLay.closeDrawer(GravityCompat.START));
        binding.shareAppBt.setOnClickListener(view -> shareApp(this));

        binding.castToTvBt.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("selectedID",0)));
        binding.castToTvNav.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("selectedID",0)));

        binding.screenMirroringBt.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("selectedID",0)));
        binding.screenMirroringNav.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("selectedID",0)));

        binding.videoPlayerBt.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("selectedID",0)));
        binding.videoPlayerNav.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("selectedID",0)));

    }

    @Override
    public void onBackPressed() {
        if (binding.drawLay.isOpen()){
            binding.drawLay.closeDrawer(GravityCompat.START);
        } else {
            exitDialog();
        }
    }

    private void exitDialog() {
        Dialog dialog = new Dialog(DashboardActivity.this, R.style.SheetDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(this);

        View lay = inflater.inflate(R.layout.exit_dialog, null);
        MaterialButton exit, close;
        ImageView rateUs;
        exit = lay.findViewById(R.id.exit);
        close = lay.findViewById(R.id.close);
        rateUs = lay.findViewById(R.id.rate_us);

        dialog.setContentView(lay);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finishAffinity();
            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                rateApp(DashboardActivity.this);
            }
        });

        dialog.show();

    }
}