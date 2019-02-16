package com.commonpepper.photosen;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;

import com.commonpepper.photosen.network.KeyFormatInterceptor;
import com.commonpepper.photosen.network.FlickrApi;

import androidx.core.app.ActivityCompat;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Photosen extends Application {

    public static final String API_URL = "https://api.flickr.com/";

    public static final int PAGE_SIZE = 10;
    public static final String PACKAGE_NAME = "com.commonpepper.photosen";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static FlickrApi getFlickrApi() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FlickrApi.class);
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new KeyFormatInterceptor())
                .build();
    }

    public static boolean isStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

}
