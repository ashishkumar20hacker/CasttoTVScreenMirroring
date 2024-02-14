package com.ide.codekit.casttotv.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.Model.DataModel;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivityShowMediaBinding;

import java.io.File;
import java.util.List;

public class ShowMediaActivity extends AppCompatActivity {

    ActivityShowMediaBinding binding;

    List<String> dataModelList;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivityShowMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        title = getIntent().getStringExtra("title");
        binding.titleTv.setText(title);

        if (title.equals("Images")) {
            dataModelList = Utils.getImageFolders(ShowMediaActivity.this);
        } else if (title.equals("Videos")) {
            dataModelList = Utils.getVideoFolders(ShowMediaActivity.this);
        } else {
            dataModelList = Utils.getAudioFolders(ShowMediaActivity.this);
        }

        populateTabLayout(dataModelList);

    }

    private void populateTabLayout(List<String> dataModelList) {
        for (String dataModel : dataModelList) {
            String folderName = new File(dataModel).getName();
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(folderName));
        }
        binding.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String selectedCategory = tab.getText().toString();
                for (String myCategory : dataModelList) {
                    if (myCategory.equalsIgnoreCase(selectedCategory)) {
                        if (!myCategory.isEmpty()) {
                            List<DataModel> details;
                            if (title.equals("Images")) {
                                details = Utils.getImagesFromFolder(ShowMediaActivity.this, myCategory);
                            } else if (title.equals("Videos")) {
                                details = Utils.getVideosFromFolder(ShowMediaActivity.this, myCategory);
                            } else {
                                details = Utils.getAudiosFromFolder(ShowMediaActivity.this, myCategory);
                            }
                        } else {
                            Log.d("Error", "null");
                        }
                        break; // Stop iterating once the matching category is found
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}