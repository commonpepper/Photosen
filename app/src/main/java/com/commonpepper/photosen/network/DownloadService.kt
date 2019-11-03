package com.commonpepper.photosen.network

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore

import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.R
import com.commonpepper.photosen.ui.activities.CropActivity
import com.google.firebase.analytics.FirebaseAnalytics

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider

class DownloadService : IntentService("PhotosenDownloadService") {

    private var mgr: NotificationManager? = null

    private var filename: String? = null
    private var aciton: Aciton? = null

    private val actionString: String
        get() = if (aciton == Aciton.DOWNLOAD_ONLY) getString(R.string.downloading_image) else getString(R.string.downloading_wallpaper)

    enum class Aciton {
        DOWNLOAD_ONLY,
        WALLPAPER
    }

    override fun onCreate() {
        isRunning = true
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        Photosen.firebaseAnalytics!!.logEvent("Download_start", null)
        val urlToDownload = intent!!.getStringExtra(TAG_URL)

        filename = intent.getStringExtra(TAG_FILENAME)
        aciton = intent.getSerializableExtra(TAG_ACTION) as Aciton

        mgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mgr!!.getNotificationChannel(CHANNEL_ID) == null) {
            val c = NotificationChannel(CHANNEL_ID, "Photosen", NotificationManager.IMPORTANCE_DEFAULT)

            c.setSound(null, null)
            c.enableVibration(false)

            mgr!!.createNotificationChannel(c)
        }

        startForeground(FOREGROUND_ID, buildForegroundNotification())

        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .path + File.separator + filename

        try {
            val output = File(path)

            if (output.exists()) {
                output.delete()
            }

            val url = URL(urlToDownload)
            val c = url.openConnection() as HttpURLConnection
            val fileLength = c.contentLength

            val fos = FileOutputStream(output.path)

            try {
                BufferedOutputStream(fos).use { out ->
                    val `in` = c.inputStream
                    val buffer = ByteArray(8192)
                    var len = `in`.read(buffer)
                    var sum = 0

                    while (len >= 0) {
                        out.write(buffer, 0, len)
                        sum += len
                        progressNotification(fileLength, sum)
                        len = `in`.read(buffer)
                    }

                    out.flush()
                }
            } finally {
                fos.fd.sync()
                c.disconnect()
            }

            val uri = FileProvider.getUriForFile(this, Photosen.PACKAGE_NAME + ".fileprovider", File(path))
            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
            if (aciton == Aciton.WALLPAPER) {
                try {
                    val wallpaperIntent = WallpaperManager.getInstance(this).getCropAndSetWallpaperIntent(uri)
                    wallpaperIntent.setDataAndType(uri, "image/*")
                    wallpaperIntent.putExtra("mimeType", "image/*")
                    wallpaperIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    wallpaperIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    wallpaperIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    startActivity(wallpaperIntent)
                    Photosen.firebaseAnalytics!!.logEvent("Crop_and_set_wallpaper_intent", null)
                } catch (e: IllegalArgumentException) {
                    //can't crop and set with default methods
                    val myCrop = Intent(this, CropActivity::class.java)
                    myCrop.putExtra(CropActivity.TAG_URISTR, uri.toString())
                    myCrop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(myCrop)
                    Photosen.firebaseAnalytics!!.logEvent("My_crop", null)
                }

            }

            completeNotification(uri, null)
        } catch (e: IOException) {
            completeNotification(null, e)
        } finally {
            mgr!!.cancel(FOREGROUND_ID) //on some devices stopForeground don't remove notification
            stopForeground(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    private fun progressNotification(total: Int, current: Int) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setOnlyAlertOnce(true)
        builder.setContentTitle(actionString)
                .setContentText(filename)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setProgress(total, current, false)

        mgr!!.notify(FOREGROUND_ID, builder.build())
    }

    private fun completeNotification(uri: Uri?, e: Exception?) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setAutoCancel(true).setOngoing(false).setWhen(System.currentTimeMillis()).setProgress(0, 0, false)

        if (e == null) {
            builder.setContentTitle(getString(R.string.download_complete))
                    .setContentText(getString(R.string.click_here_to_open) + " " + filename)
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)

            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val chooser = Intent.createChooser(intent, getString(R.string.open_file_with))
            intent.setDataAndType(uri, "image/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            val pendingIntent = PendingIntent.getActivity(this, 0, chooser, PendingIntent.FLAG_CANCEL_CURRENT)

            builder.setContentIntent(pendingIntent)
        } else {
            builder.setContentTitle(getString(R.string.download_error))
                    .setContentText(e.message)
                    .setSmallIcon(android.R.drawable.stat_notify_error)
        }

        mgr!!.notify(NOTIFY_ID, builder.build())
    }

    private fun buildForegroundNotification(): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setOngoing(true)
                .setContentTitle(actionString)
                .setContentText(filename)
                .setSmallIcon(android.R.drawable.stat_sys_download)

        return builder.build()
    }

    companion object {
        private val TAG = DownloadService::class.java.simpleName

        var isRunning = false

        val TAG_URL = "url"
        val TAG_FILENAME = "filename"
        val TAG_ACTION = "action"

        private val CHANNEL_ID = "download_channel_id"
        val FOREGROUND_ID = 98732517
        val NOTIFY_ID = 98732518
    }
}
