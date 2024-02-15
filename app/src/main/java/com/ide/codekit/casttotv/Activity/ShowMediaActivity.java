package com.ide.codekit.casttotv.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.tabs.TabLayout;
import com.ide.codekit.casttotv.Adapter.VideoAdapter;
import com.ide.codekit.casttotv.Extras.AudioDialog;
import com.ide.codekit.casttotv.Extras.ImageDialog;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.Extras.VideoDialog;
import com.ide.codekit.casttotv.Model.DataModel;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ActivityShowMediaBinding;

import java.io.File;
import java.util.List;

public class ShowMediaActivity extends AppCompatActivity {

    private ActivityShowMediaBinding binding;
    private List<String> folderPaths;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.makeStatusBarTransparent2(this);
        binding = ActivityShowMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title = getIntent().getStringExtra("title");
        binding.titleTv.setText(title);

        folderPaths = getFolderPaths(title);
        populateTabLayout();

        binding.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private List<String> getFolderPaths(String title) {
        if (title.equals("Images")) {
            return Utils.getImageFolders(this);
        } else if (title.equals("Videos")) {
            return Utils.getVideoFolders(this);
        } else {
            return Utils.getAudioFolders(this);
        }
    }

    private void populateTabLayout() {
        for (String folderPath : folderPaths) {
            String folderName = new File(folderPath).getName();
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(folderName));
        }

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                performActionForTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        performActionForTab(0);
    }

    private void performActionForTab(int tabIndex) {
        String selectedCategory = binding.tabLayout.getTabAt(tabIndex).getText().toString();
        for (String folderPath : folderPaths) {
            String folderName = new File(folderPath).getName();
            if (folderName.equalsIgnoreCase(selectedCategory)) {
                List<DataModel> details = getDataModels(folderPath);
                setAdapter(details);
                return; // Stop iterating once the matching category is found
            }
        }
    }

    private List<DataModel> getDataModels(String folderPath) {
        if (title.equals("Images")) {
            binding.noMediaTv.setText(getString(R.string.no_images_found));
            return Utils.getImagesFromFolder(this, folderPath);
        } else if (title.equals("Videos")) {
            binding.noMediaTv.setText(getString(R.string.no_videos_found));
            return Utils.getVideosFromFolder(this, folderPath);
        } else {
            binding.noMediaTv.setText(getString(R.string.no_audios_found));
            return Utils.getAudiosFromFolder(this, folderPath);
        }
    }

    private void setAdapter(List<DataModel> details) {
        if (details.size() == 0) {
            binding.mediaRv.setVisibility(View.GONE);
            binding.loader.setVisibility(View.VISIBLE);
        } else {
            VideoAdapter adapter = new VideoAdapter(new VideoAdapter.DataClickListener() {
                @Override
                public void onDataClick(DataModel dataModel) {
                    if (title.equals("Images")) {
                        ImageDialog dialog = new ImageDialog(ShowMediaActivity.this, dataModel.getPath());
                        dialog.show();
                    } else if (title.equals("Videos")) {
                        VideoDialog dialog = new VideoDialog(ShowMediaActivity.this, dataModel.getPath());
                        dialog.show();
                    } else {
                        AudioDialog dialog = new AudioDialog(ShowMediaActivity.this, dataModel.getPath(), dataModel.getName());
                        dialog.show();
                    }
                }

                @Override
                public void onShare(DataModel model) {
                    shareFile(model.getPath());
                }
            });
            if (title.equals("Audios")) {
                adapter.setViewType(VideoAdapter.VIEW_AUDIO);
            } else {
                adapter.setViewType(VideoAdapter.VIEW_LINEAR);
            }
            binding.mediaRv.setAdapter(adapter);
            adapter.submitList(details);
            binding.loader.setVisibility(View.GONE);
            binding.mediaRv.setVisibility(View.VISIBLE);
        }
    }

    private void shareFile(String path) {
        File file = new File(path); // filePath is the path of your file
        Uri uri = FileProvider.getUriForFile(ShowMediaActivity.this, getPackageName() + ".fileprovider", file);

        if (title.equals("Images")) {
            Utils.shareImage(ShowMediaActivity.this, uri, "Share Image");
        } else if (title.equals("Videos")) {
            Utils.shareVideo(ShowMediaActivity.this, uri, "Share Video");
        } else {
            Utils.shareAudio(ShowMediaActivity.this, uri, "Share Audio");
        }
    }
}
