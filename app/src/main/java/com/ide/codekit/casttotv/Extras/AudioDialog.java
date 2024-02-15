package com.ide.codekit.casttotv.Extras;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ide.codekit.casttotv.R;

import java.io.IOException;

public class AudioDialog extends Dialog {

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private boolean isPlaying = false;
    TextView current_progress;
    TextView max_progress;

    public AudioDialog(@NonNull Context context, String audioUrl, String name) {
        super(context);
        setContentView(R.layout.dialog_audio);

        // Make the dialog background transparent
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView playPauseButton = findViewById(R.id.playPauseButton);
        ImageView backbt = findViewById(R.id.back_bt);
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView title = findViewById(R.id.title_tv);
        current_progress = findViewById(R.id.current_progress);
        max_progress = findViewById(R.id.max_progress);

        title.setText(name);

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);

        // Set data source and prepare the MediaPlayer
        try {
            mediaPlayer.setDataSource(context, Uri.parse(audioUrl));
            mediaPlayer.prepare();
            max_progress.setText(getMaxDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set prepared listener
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Update the maximum duration of the seek bar
                seekBar.setMax(mp.getDuration());

                // Start playback
                mp.start();

                // Update the play/pause button icon
                playPauseButton.setImageResource(R.drawable.ic_pause);

                // Update the seek bar progress
                updateSeekBar(seekBar);
            }
        });

        // Set max value for seekBar
        seekBar.setMax(mediaPlayer.getDuration());

        // Handle play/pause button clicks
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    playPauseButton.setImageResource(R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    playPauseButton.setImageResource(R.drawable.ic_pause);
                    updateSeekBar(seekBar);
                }
                isPlaying = !isPlaying;
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Handle media playback completion
                // For example, reset the play/pause button icon
                playPauseButton.setImageResource(R.drawable.ic_play);
                seekBar.setProgress(0);
                current_progress.setText("00:00");
            }
        });

        // Handle seekBar changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
                current_progress.setText(getCurrentProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Pause the media player when seeking
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    isPlaying = false;
                    playPauseButton.setImageResource(R.drawable.ic_play);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Resume playback after seeking
                mediaPlayer.start();
                isPlaying = true;
                playPauseButton.setImageResource(R.drawable.ic_pause);
                updateSeekBar(seekBar);
            }
        });

        // Handle back button click
        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the dialog when back button is clicked
//                dismiss();
                if (mediaPlayer != null) {
                    dismiss();
                }
            }
        });
    }

    private void updateSeekBar(SeekBar seekBar) {
        // Update seekBar progress
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            current_progress.setText(getCurrentProgress());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateSeekBar(seekBar);
                }
            }, 1000);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        releaseMediaPlayer();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
        handler.removeCallbacksAndMessages(null);
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getCurrentProgress() {
        if (mediaPlayer != null) {
            int currentMillis = mediaPlayer.getCurrentPosition();
            return formatTime(currentMillis);
        }
        return "00:00";
    }

    public String getMaxDuration() {
        if (mediaPlayer != null) {
            int maxMillis = mediaPlayer.getDuration();
            return formatTime(maxMillis);
        }
        return "00:00";
    }

}
