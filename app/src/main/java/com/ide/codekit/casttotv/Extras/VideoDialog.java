package com.ide.codekit.casttotv.Extras;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.annotation.NonNull;

import com.ide.codekit.casttotv.R;

public class VideoDialog extends Dialog {

    public VideoDialog(@NonNull Context context, String videoUrl) {
        super(context);
        setContentView(R.layout.dialog_video);

        // Make the dialog background transparent
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        VideoView videoView = findViewById(R.id.videoView);
        ImageView backbt = findViewById(R.id.back_bt);

        // Set media controller
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Set video URI and start playing
        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        videoView.start();

        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the dialog when back button is clicked
                dismiss();
            }
        });
    }
}
