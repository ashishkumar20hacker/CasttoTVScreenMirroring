package com.ide.codekit.casttotv.Extras;

import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ide.codekit.casttotv.BuildConfig;
import com.ide.codekit.casttotv.Model.DataModel;
import com.ide.codekit.casttotv.Model.MediaDataModel;
import com.ide.codekit.casttotv.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {
    public static final int STORAGE_PERMISSION_REQ_CODE = 100;

    // Method to check if storage permission is granted
    public static boolean isStoragePermissionGranted(Activity activity) {
        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    // Method to request storage permission
    public static void requestStoragePermission(Activity activity) {
        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_AUDIO}, STORAGE_PERMISSION_REQ_CODE);
        } else {
            activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQ_CODE);
        }
    }

    public static void rateApp(Activity contexts) {
        try {
            Intent rateIntent = rateIntentForUrl(contexts, "market://details");
            contexts.startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl(contexts, "https://play.google.com/store/apps/details");
            contexts.startActivity(rateIntent);
        }
    }

    private static Intent rateIntentForUrl(Activity contexts, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, contexts.getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    public static void shareApp(Activity activity) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage = "Check out this amazing app!!";
            shareMessage = shareMessage + "\n\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(shareIntent, "Share Via"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public static void makeStatusBarTransparent(Activity context) {
        if (SDK_INT >= 19 && SDK_INT < 21) {
            setWindowFlag(context, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (SDK_INT >= 19) {
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //make fully Android Transparent Status bar
        if (SDK_INT >= 21) {
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void makeStatusBarTransparent2(Activity context) {
        if (SDK_INT >= 19 && SDK_INT < 21) {
            setWindowFlag(context, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (SDK_INT >= 19) {
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (SDK_INT >= 21) {
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        /*WindowInsetsControllerCompat.setAppearanceLightStatusBars(true)*/
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static void applyGradientOnTv(TextView tv, String color1, String color2) {
        Shader textShader = new LinearGradient(0, 0, tv.getPaint().measureText(tv.getText().toString()), tv.getTextSize(),
                new int[]{Color.parseColor(color1), Color.parseColor(color2)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        tv.getPaint().setShader(textShader);
    }

    public interface onPushEffect {
        void onClick();
    }

    public static void pushEffectCardView(View btn, onPushEffect onPushEffect, boolean before) {
        btn.animate()
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (before) {
                            onPushEffect.onClick();
                        }
                        btn.animate()
                                .scaleY(1f)
                                .scaleX(1f)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        if (!before) {
                                            onPushEffect.onClick();
                                        }
                                    }
                                })
                                .setDuration(50)
                                .setInterpolator(new AccelerateDecelerateInterpolator());
                    }
                })
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(50)
                .setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public static List<MediaDataModel> getAllVideoList(Context context) {
        List<MediaDataModel> dataModelList = new ArrayList<>();

        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        try (Cursor cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder)) {

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    long videoId = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                    String videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    String videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                    long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                    String readableFileSize = getFileSize(fileSize);


                    MediaDataModel dataModel = new MediaDataModel(videoId, videoPath, videoName, readableFileSize);
                    dataModelList.add(dataModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataModelList;
    }

    public static List<String> getVideoFolders(Context context) {
        List<String> videoFolders = new ArrayList<>();
        Set<String> folderPaths = new HashSet<>(); // Use Set to avoid duplicates

        String[] projection = {MediaStore.Video.Media.DATA};
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(videoUri, projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    File videoFile = new File(videoPath);
                    String folderPath = videoFile.getParent(); // Extract folder path using File's getParent method

                    if (folderPath != null) {
                        folderPaths.add(folderPath);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        videoFolders.addAll(folderPaths); // Add unique folder paths to the list
        return videoFolders;
    }

    public static List<MediaDataModel> getVideosFromFolder(Context context, String folderPath) {
        List<MediaDataModel> dataModelList = new ArrayList<>();

        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATA
        };
        String selection = MediaStore.Video.Media.DATA + " like ?";
        String[] selectionArgs = new String[]{"%" + folderPath + "%"};
        String sortOrder = null;

        try (Cursor cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder)) {

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                    long videoId = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                    String videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                    String readableFileSize = getFileSize(fileSize);

                    MediaDataModel dataModel = new MediaDataModel(videoId, videoPath, videoName, readableFileSize);
                    dataModelList.add(dataModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataModelList;
    }

    public static List<MediaDataModel> getAllAudioFiles(Context context) {
        List<MediaDataModel> audioFiles = new ArrayList<>();

        // Define the columns you want to retrieve
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE
        };

        // Query the external audio content URI
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long audioId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String audioPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String audioName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                    String readableFileSize = getFileSize(fileSize);
                    MediaDataModel dataModel = new MediaDataModel(audioId, audioPath, audioName, readableFileSize);
                    audioFiles.add(dataModel);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return audioFiles;
    }

    public static List<String> getAudioFolders(Context context) {
        List<String> audioFolders = new ArrayList<>();
        Set<String> folderPaths = new HashSet<>(); // Use Set to avoid duplicates

        String[] projection = {MediaStore.Audio.Media.DATA};
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(audioUri, projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String audioPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    File audioFile = new File(audioPath);
                    String folderPath = audioFile.getParent(); // Extract folder path using File's getParent method

                    if (folderPath != null) {
                        folderPaths.add(folderPath);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        audioFolders.addAll(folderPaths); // Add unique folder paths to the list
        return audioFolders;
    }

    public static List<MediaDataModel> getAudiosFromFolder(Context context, String folderPath) {
        List<MediaDataModel> dataModelList = new ArrayList<>();

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA
        };
        String selection = MediaStore.Audio.Media.DATA + " like ?";
        String[] selectionArgs = new String[]{"%" + folderPath + "%"};
        String sortOrder = null;

        try (Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder)) {

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String audioName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    long audioId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String audioPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                    String readableFileSize = getFileSize(fileSize);

                    MediaDataModel dataModel = new MediaDataModel(audioId, audioPath, audioName, readableFileSize);
                    dataModelList.add(dataModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataModelList;
    }

    public static List<MediaDataModel> getAllImageFiles(Context context) {
        List<MediaDataModel> imageFiles = new ArrayList<>();

        // Define the columns you want to retrieve
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
        };

        // Query the external audio content URI
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long imageId = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                    String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String imageName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                    String readableFileSize = getFileSize(fileSize);

                    MediaDataModel dataModel = new MediaDataModel(imageId, imagePath, imageName, readableFileSize);
                    imageFiles.add(dataModel);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return imageFiles;
    }

    public static List<String> getImageFolders(Context context) {
        List<String> imageFolders = new ArrayList<>();
        Set<String> folderPaths = new HashSet<>(); // Use Set to avoid duplicates

        String[] projection = {MediaStore.Images.Media.DATA};
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(imageUri, projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    File imageFile = new File(imagePath);
                    String folderPath = imageFile.getParent(); // Extract folder path using File's getParent method

                    if (folderPath != null) {
                        folderPaths.add(folderPath);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        imageFolders.addAll(folderPaths); // Add unique folder paths to the list
        return imageFolders;
    }

    public static List<MediaDataModel> getImagesFromFolder(Context context, String folderPath) {
        List<MediaDataModel> dataModelList = new ArrayList<>();

        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA
        };
        String selection = MediaStore.Images.Media.DATA + " like ?";
        String[] selectionArgs = new String[]{"%" + folderPath + "%"};
        String sortOrder = null;

        try (Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder)) {

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    long imageId = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                    String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String imageName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                    String readableFileSize = getFileSize(fileSize);

                    MediaDataModel dataModel = new MediaDataModel(imageId, imagePath, imageName, readableFileSize);
                    dataModelList.add(dataModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataModelList;
    }

    public static String getFileSize(long size) {
        if (size <= 0) return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    // Method to share images
    public static void shareImage(Context context, Uri imageUri, String title) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }

    // Method to share videos
    public static void shareVideo(Context context, Uri videoUri, String title) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("video/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }

    // Method to share audio
    public static void shareAudio(Context context, Uri audioUri, String title) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("audio/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, audioUri);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }
    public static LiveData<List<DataModel>> getCasts(Activity activity) {
        MutableLiveData<List<DataModel>> mutableLiveData = new MutableLiveData<>();
        final ArrayList<DataModel> arrayList = new ArrayList<>();
        new NearbyDeviceScanner(activity).startScan(new NearbyDeviceScanner.DeviceScanListener() {
            @Override
            public void onScanFailed(String str) {
                // Handle scan failure if needed
            }

            @Override
            public void onScanStarted() {
                // Handle scan start if needed
            }

            @Override
            public void onScanStopped() {
                // Handle scan stop if needed
            }

            @Override
            public void onDeviceFound(NsdServiceInfo nsdServiceInfo) {
                String serviceName = nsdServiceInfo.getServiceName();
                String hostAddress = nsdServiceInfo.getHost().getHostAddress();
                String arrays = Arrays.toString(nsdServiceInfo.getHost().getAddress());
                Log.e("onDeviceFound: ", serviceName + "   " + hostAddress);
                synchronized (arrayList) {
                    arrayList.add(new DataModel(serviceName, arrays, hostAddress));
                }
                mutableLiveData.postValue(arrayList);
            }

            @Override
            public void onDeviceLost(NsdServiceInfo nsdServiceInfo) {
                String serviceName = nsdServiceInfo.getServiceName();
                String hostAddress = nsdServiceInfo.getHost().getHostAddress();
                String arrays = Arrays.toString(nsdServiceInfo.getHost().getAddress());
                Log.e("onDeviceLost: ", serviceName + "   " + hostAddress);
                synchronized (arrayList) {
                    arrayList.remove(new DataModel(serviceName, arrays, hostAddress));
                }
                mutableLiveData.postValue(arrayList);
            }
        });
        return mutableLiveData;
    }

}
