package com.commonpepper.photosen;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Build;

import com.commonpepper.photosen.database.PhotosenDatabase;
import com.commonpepper.photosen.network.KeyFormatInterceptor;
import com.commonpepper.photosen.network.FlickrApi;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.firebase.analytics.FirebaseAnalytics;

import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Photosen extends Application {

    public static final String API_URL = "https://api.flickr.com/";

    public static final int PAGE_SIZE = 30;
    public static final int PREFETCH_DISTANCE = 5;
    public static final String PACKAGE_NAME = "com.commonpepper.photosen";
    public static final String PREFERENCES = PACKAGE_NAME + ".PREFERENCES";

    private static FirebaseAnalytics firebaseAnalytics;
    private static Photosen instance;
    private PhotosenDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        configureCrashReporting();

        database = Room.databaseBuilder(this, PhotosenDatabase.class, "photosen_database")
                .build();
    }

    private void configureCrashReporting() {
        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build());
    }

    public static Photosen getInstance() {
        return instance;
    }

    public PhotosenDatabase getDatabase() {
        return database;
    }

    public static FirebaseAnalytics getFirebaseAnalytics() {
        return firebaseAnalytics;
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
