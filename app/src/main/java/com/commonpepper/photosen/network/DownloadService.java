package com.commonpepper.photosen.network;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.R;
import com.commonpepper.photosen.ui.activities.CropActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

public class DownloadService extends IntentService {
    private static final String TAG = DownloadService.class.getSimpleName();

    public static boolean isRunning = false;

    private NotificationManager mgr;

    public enum Aciton {
        DOWNLOAD_ONLY,
        WALLPAPER
    }

    public static final String TAG_URL = "url";
    public static final String TAG_FILENAME = "filename";
    public static final String TAG_ACTION = "action";

    private static final String CHANNEL_ID = "download_channel_id";
    public static final int FOREGROUND_ID = 98732517;
    public static final int NOTIFY_ID = 98732518;

    private String filename;
    private Aciton aciton;

    public DownloadService() {
        super("PhotosenDownloadService");
    }

    @Override
    public void onCreate() {
        isRunning = true;
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Photosen.getFirebaseAnalytics().logEvent("Download_start", null);
        String urlToDownload = intent.getStringExtra(TAG_URL);

        filename = intent.getStringExtra(TAG_FILENAME);
        aciton = (Aciton) intent.getSerializableExtra(TAG_ACTION);

        mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mgr.getNotificationChannel(CHANNEL_ID) == null) {
            NotificationChannel c = new NotificationChannel(CHANNEL_ID, "Photosen", NotificationManager.IMPORTANCE_DEFAULT);

            c.setSound(null, null);
            c.enableVibration(false);

            mgr.createNotificationChannel(c);
        }

        startForeground(FOREGROUND_ID, buildForegroundNotification());

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getPath() + File.separator + filename;

        try {
            File output = new File(path);

            if (output.exists()) {
                output.delete();
            }

            URL url = new URL(urlToDownload);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            int fileLength = c.getContentLength();

            FileOutputStream fos = new FileOutputStream(output.getPath());
            BufferedOutputStream out = new BufferedOutputStream(fos);

            try {
                InputStream in = c.getInputStream();
                byte[] buffer = new byte[8192];
                int len = 0;
                int sum = 0;

                while ((len = in.read(buffer)) >= 0) {
                    out.write(buffer, 0, len);
                    sum += len;
                    progressNotification(fileLength, sum);
                }

                out.flush();
            } finally {
                fos.getFD().sync();
                out.close();
                c.disconnect();
            }

            Uri uri = FileProvider.getUriForFile(this, Photosen.PACKAGE_NAME + ".fileprovider", new File(path));
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (aciton == Aciton.WALLPAPER) {
                try {
                    Intent wallpaperIntent = WallpaperManager.getInstance(this).getCropAndSetWallpaperIntent(uri);
                    wallpaperIntent.setDataAndType(uri, "image/*");
                    wallpaperIntent.putExtra("mimeType", "image/*");
                    wallpaperIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    wallpaperIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    wallpaperIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivity(wallpaperIntent);
                    Photosen.getFirebaseAnalytics().logEvent("Crop_and_set_wallpaper_intent", null);
                } catch (IllegalArgumentException e) {
                    //can't crop and set with default methods
                    Intent myCrop = new Intent(this, CropActivity.class);
                    myCrop.putExtra(CropActivity.TAG_URISTR, uri.toString());
                    myCrop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(myCrop);
                    Photosen.getFirebaseAnalytics().logEvent("My_crop", null);
                }
            }

            completeNotification(uri, null);
        } catch (IOException e) {
            completeNotification(null, e);
        } finally {
            mgr.cancel(FOREGROUND_ID); //on some devices stopForeground don't remove notification
            stopForeground(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void progressNotification(int total, int current) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setOnlyAlertOnce(true);
        builder.setContentTitle(getActionString())
                .setContentText(filename)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setProgress(total, current, false);

        mgr.notify(FOREGROUND_ID, builder.build());
    }

    private void completeNotification(Uri uri, Exception e) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setAutoCancel(true).setOngoing(false).setWhen(System.currentTimeMillis()).setProgress(0, 0, false);

        if (e == null) {
            builder.setContentTitle(getString(R.string.download_complete))
                    .setContentText(getString(R.string.click_here_to_open) + " " + filename)
                    .setSmallIcon(android.R.drawable.stat_sys_download_done);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent chooser = Intent.createChooser(intent, getString(R.string.open_file_with));
            intent.setDataAndType(uri, "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, chooser, PendingIntent.FLAG_CANCEL_CURRENT);

            builder.setContentIntent(pendingIntent);
        } else {
            builder.setContentTitle(getString(R.string.download_error))
                    .setContentText(e.getMessage())
                    .setSmallIcon(android.R.drawable.stat_notify_error);
        }

        mgr.notify(NOTIFY_ID, builder.build());
    }

    private Notification buildForegroundNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setOngoing(true)
                .setContentTitle(getActionString())
                .setContentText(filename)
                .setSmallIcon(android.R.drawable.stat_sys_download);

        return builder.build();
    }

    private String getActionString() {
        return aciton == Aciton.DOWNLOAD_ONLY ? getString(R.string.downloading_image) : getString(R.string.downloading_wallpaper);
    }
}
