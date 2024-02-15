package com.ide.codekit.casttotv.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.ide.codekit.casttotv.Adapter.OnlineImagesAdapter;
import com.ide.codekit.casttotv.Extras.KeyboardVisibilityUtils;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.Retrofit.PexelsApiService;
import com.ide.codekit.casttotv.Retrofit.PexelsPhoto;
import com.ide.codekit.casttotv.Retrofit.PexelsResponse;
import com.ide.codekit.casttotv.Retrofit.RetrofitClient;
import com.ide.codekit.casttotv.databinding.ActivityOnlineImagesBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineImagesActivity extends AppCompatActivity {

    ActivityOnlineImagesBinding binding;

    ArrayList<String> imageUrls;

    private static final String BASE_URL = "https://api.pexels.com/v1/";
    private static final String API_KEY = "PtX8K4aVrRZP1lHwFQWmcICIYQsrVbccbEwaI2IVrPokFS9vrivxjw8F";
    private static final String TAG = "PexelsApiExample";
    private static final int IMAGES_PER_PAGE = 25;
    private static final int TOTAL_IMAGES_TO_FETCH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivityOnlineImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PexelsApiService apiService = RetrofitClient.getClient(BASE_URL).create(PexelsApiService.class);

        binding.backBt.setOnClickListener(view -> {
            onBackPressed();
        });

        KeyboardVisibilityUtils.setKeyboardVisibilityListener(this, new KeyboardVisibilityUtils.KeyboardVisibilityListener() {
            @Override
            public void onKeyboardVisibilityChanged(boolean keyboardVisible) {
                // Perform actions based on keyboard visibility
                if (keyboardVisible) {
                    // Keyboard is visible
                    // Perform actions when keyboard is shown
                    binding.noMediaTv.setText("Search something!");
                    binding.resultRv.setVisibility(View.GONE);
                    binding.loader.setVisibility(View.VISIBLE);
                } else {
                    // Keyboard is hidden
                    // Perform actions when keyboard is hidden
                    if (imageUrls != null && !imageUrls.isEmpty()) {
                        // If images have been fetched, hide the loader and show the result RecyclerView
                        binding.loader.setVisibility(View.GONE);
                        binding.resultRv.setVisibility(View.VISIBLE);
                    } else {
                        // If no images have been fetched, hide the result RecyclerView and show the loader
                        binding.resultRv.setVisibility(View.GONE);
                        binding.loader.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        binding.searchEd.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.noMediaTv.setText("Fetching...");
                binding.resultRv.setVisibility(View.GONE);
                binding.loader.setVisibility(View.VISIBLE);
                searchImage(apiService, binding.searchEd.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void searchImage(PexelsApiService apiService, String query) {
        hideKeyboard();
        if (query.isEmpty()) {
            binding.searchEd.setError("Please write something to search!!");
        } else {
            imageUrls = new ArrayList<>();
            fetchImages(apiService, query, 1);
        }
    }

    private void fetchImages(PexelsApiService apiService, String query, final int page) {
        Call<PexelsResponse> call = apiService.searchImages(API_KEY, query, IMAGES_PER_PAGE, page);
        call.enqueue(new Callback<PexelsResponse>() {
            @Override
            public void onResponse(Call<PexelsResponse> call, Response<PexelsResponse> response) {
                if (response.isSuccessful()) {
                    PexelsResponse pexelsResponse = response.body();
                    if (pexelsResponse != null && pexelsResponse.getPhotos() != null && !pexelsResponse.getPhotos().isEmpty()) {
                        for (PexelsPhoto photo : pexelsResponse.getPhotos()) {
                            String imageUrl = photo.getSrc().getOriginal();
                            if (imageUrl != null) {
                                imageUrls.add(imageUrl);
                            } else {
                                Log.e(TAG, "Image URL is null");
                            }
                        }
                        // Check if there are more pages
                        if (imageUrls.size() < TOTAL_IMAGES_TO_FETCH) {
                            // If there are more photos than the fetched ones, fetch the next page
                            fetchImages(apiService, query, page + 1);
                        } else {
                            // Otherwise, set the adapter
                            setAdapter();
                        }
                    }
                } else {
                    Log.e(TAG, "API error: " + response.code());
                    binding.noMediaTv.setText("Something went wrong!");
                    binding.resultRv.setVisibility(View.GONE);
                    binding.loader.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PexelsResponse> call, Throwable t) {
                binding.noMediaTv.setText("Something went wrong!");
                binding.resultRv.setVisibility(View.GONE);
                binding.loader.setVisibility(View.VISIBLE);
                Log.e(TAG, "API call failed", t);
            }
        });
    }

    private void setAdapter() {
        if (imageUrls.isEmpty()) {
            binding.resultRv.setVisibility(View.GONE);
            binding.loader.setVisibility(View.VISIBLE);
        } else {
            OnlineImagesAdapter adapter = new OnlineImagesAdapter();
            binding.resultRv.setAdapter(adapter);
            adapter.submitList(imageUrls);
            binding.loader.setVisibility(View.GONE);
            binding.resultRv.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        binding.searchEd.clearFocus();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
