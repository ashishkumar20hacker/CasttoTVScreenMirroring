package com.ide.codekit.casttotv.Extras;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ide.codekit.casttotv.Activity.HelpActivity;
import com.ide.codekit.casttotv.R;

public class ScreenMirroringService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "ScreenMirroringChannel";

    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;

    @Override
    public void onCreate() {
        super.onCreate();
        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("START_SCREEN_MIRRORING")) {
                handleStartScreenMirroring(intent);
            } else if (intent.getAction().equals("STOP_SCREEN_MIRRORING")) {
                handleStopScreenMirroring();
            }
        }
        return START_NOT_STICKY;
    }

    private void handleStartScreenMirroring(Intent intent) {
        // Start the service in the foreground
        startForeground(NOTIFICATION_ID, createNotification());

        // Obtain the media projection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent data = intent.getParcelableExtra("DATA");
            mediaProjection = projectionManager.getMediaProjection(Activity.RESULT_OK, data);
            if (mediaProjection != null) {
                // Your screen mirroring logic goes here
                Toast.makeText(this, "Screen mirroring started", Toast.LENGTH_SHORT).show();
            } else {
                stopSelf(); // Stop the service if media projection cannot be obtained
            }
        } else {
            Toast.makeText(this, "Screen mirroring not supported on this device", Toast.LENGTH_SHORT).show();
            stopSelf(); // Stop the service if screen mirroring is not supported
        }
    }

    private void handleStopScreenMirroring() {
        // Stop the service
        stopForeground(true);
        stopSelf();
    }
    private Notification createNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Screen Mirroring", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(this, HelpActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Screen Mirroring")
                .setContentText("Screen mirroring is active")
                .setSmallIcon(R.drawable.cast)
                .setContentIntent(pendingIntent);

        return builder.build();
    }

    /*private Notification createNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Screen Mirroring", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Screen Mirroring")
                .setContentText("Screen mirroring is active")
                .setSmallIcon(R.drawable.cast)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));

        return builder.build();
    }*/

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
