package com.ide.codekit.casttotv.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding binding;
    String youtube_url = "https://www.youtube.com/";
    String drive_url = "https://drive.google.com/drive/home";
    String main_url = youtube_url;
    String intent_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.BLACK);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent_name = getIntent().getStringExtra("intent_name");
        assert intent_name != null;
        if (intent_name.equalsIgnoreCase("youtube")){
            main_url = youtube_url;
        } else {
            main_url = drive_url;
        }

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // Set a WebViewClient to handle navigation
        binding.webView.setWebViewClient(new WebViewClient());

        // Set a WebChromeClient to handle progress changes and titles
        binding.webView.setWebChromeClient(new WebChromeClient());

        // Load the URL
        binding.webView.loadUrl(main_url);
    }

    @Override
    public void onBackPressed() {
        // Check if the WebView can go back and navigate back if possible
        if (binding.webView.canGoBack()) {
            binding.webView.goBack();
        } else {
            // If the WebView cannot go back, proceed with the default back button behavior
            super.onBackPressed();
        }
    }
}