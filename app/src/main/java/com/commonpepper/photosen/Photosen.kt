package com.commonpepper.photosen

import android.Manifest.permission
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build.VERSION
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.commonpepper.photosen.database.PhotosenDatabase
import com.commonpepper.photosen.network.FlickrApi
import com.commonpepper.photosen.network.KeyFormatInterceptor
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.crashlytics.android.core.CrashlyticsCore.Builder
import com.google.firebase.analytics.FirebaseAnalytics
import io.fabric.sdk.android.Fabric
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Photosen : Application() {
    var database: PhotosenDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        configureCrashReporting()
        database = Room.databaseBuilder(this, PhotosenDatabase::class.java, "photosen_database")
                .fallbackToDestructiveMigration()
                .build()
    }

    private fun configureCrashReporting() {
        val crashlyticsCore: CrashlyticsCore? = Builder()
                .disabled(BuildConfig.DEBUG)
                .build()
        Fabric.with(this, Crashlytics.Builder().core(crashlyticsCore).build())
    }

    companion object {
        const val API_URL = "https://api.flickr.com/"
        const val PAGE_SIZE = 30
        const val PREFETCH_DISTANCE = 5
        const val PACKAGE_NAME = "com.commonpepper.photosen"
        const val PREFERENCES = "$PACKAGE_NAME.PREFERENCES"
        var firebaseAnalytics: FirebaseAnalytics? = null
            private set
        var instance: Photosen? = null
            private set

        val flickrApi: FlickrApi
            get() = Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(FlickrApi::class.java)

        private val client: OkHttpClient
            get() = OkHttpClient.Builder()
                    .addInterceptor(KeyFormatInterceptor())
                    .build()

        fun isStoragePermissionGranted(activity: Activity?): Boolean {
            return if (VERSION.SDK_INT >= 23) {
                if (ActivityCompat.checkSelfPermission(activity!!, permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    true
                } else {
                    ActivityCompat.requestPermissions(activity, arrayOf<String?>(permission.WRITE_EXTERNAL_STORAGE), 1)
                    false
                }
            } else {
                true
            }
        }
    }
}