package com.commonpepper.photosen.network

import android.R.drawable
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.NotificationCompat.Builder
import androidx.core.content.FileProvider
import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.Photosen.Companion.firebaseAnalytics
import com.commonpepper.photosen.R.string
import com.commonpepper.photosen.ui.activities.CropActivity
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class DownloadService : IntentService("PhotosenDownloadService") {
    private var mgr: NotificationManager? = null

    enum class Aciton { DOWNLOAD_ONLY, WALLPAPER }

    private var filename: String? = null
    private var aciton: Aciton? = null
    override fun onCreate() {
        isRunning = true
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        firebaseAnalytics.logEvent("Download_start", null)
        val urlToDownload: String? = intent!!.getStringExtra(TAG_URL)
        filename = intent.getStringExtra(TAG_FILENAME)
        aciton = intent.getSerializableExtra(TAG_ACTION) as Aciton
        mgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (VERSION.SDK_INT >= VERSION_CODES.O && mgr!!.getNotificationChannel(CHANNEL_ID) == null) {
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
            val out = BufferedOutputStream(fos)
            try {
                val `in`: InputStream = c.inputStream
                val buffer = ByteArray(8192)
                var len: Int
                var sum = 0
                while (`in`.read(buffer).also { len = it } >= 0) {
                    out.write(buffer, 0, len)
                    sum += len
                    progressNotification(fileLength, sum)
                }
                out.flush()
            } finally {
                fos.fd.sync()
                out.close()
                c.disconnect()
            }
            val uri: Uri = FileProvider.getUriForFile(this, Photosen.PACKAGE_NAME + ".fileprovider", File(path))

            //Different ways of scan image work on different devices
            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
            MediaScannerConnection.scanFile(applicationContext, arrayOf(path), arrayOf("image/*")) { _, _->}
//            if (VERSION.SDK_INT >= VERSION_CODES.Q) {
//                MediaStore.setIncludePending(uri)
//            } else {
//                MediaStore.Images.Media.insertImage(contentResolver, path, filename, "")
//            }

            if (aciton == Aciton.WALLPAPER) {
                try {
                    val wallpaperIntent: Intent = WallpaperManager.getInstance(this).getCropAndSetWallpaperIntent(uri)
                    wallpaperIntent.setDataAndType(uri, "image/*")
                    wallpaperIntent.putExtra("mimeType", "image/*")
                    wallpaperIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    wallpaperIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    wallpaperIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    startActivity(wallpaperIntent)
                    firebaseAnalytics.logEvent("Crop_and_set_wallpaper_intent", null)
                } catch (e: IllegalArgumentException) {
                    //can't crop and set with default methods

                    val myCrop = Intent(this, CropActivity::class.java)
                    myCrop.putExtra(CropActivity.TAG_URISTR, uri.toString())
                    myCrop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(myCrop)
                    firebaseAnalytics.logEvent("My_crop", null)
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
        val builder = Builder(this, CHANNEL_ID)
        builder.setOnlyAlertOnce(true)
        builder.setContentTitle(actionString)
                .setContentText(filename)
                .setSmallIcon(drawable.stat_sys_download)
                .setProgress(total, current, false)
        mgr!!.notify(FOREGROUND_ID, builder.build())
    }

    private fun completeNotification(uri: Uri?, e: Exception?) {
        val builder = Builder(this, CHANNEL_ID)
        builder.setAutoCancel(true).setOngoing(false).setWhen(System.currentTimeMillis()).setProgress(0, 0, false)
        if (e == null) {
            builder.setContentTitle(getString(string.download_complete))
                    .setContentText(getString(string.click_here_to_open) + " " + filename)
                    .setSmallIcon(drawable.stat_sys_download_done)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val chooser: Intent? = Intent.createChooser(intent, getString(string.open_file_with))
            intent.setDataAndType(uri, "image/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            val pendingIntent: PendingIntent? = PendingIntent.getActivity(this, 0, chooser, PendingIntent.FLAG_CANCEL_CURRENT)
            builder.setContentIntent(pendingIntent)
        } else {
            Log.d(TAG, e.message!!)
            builder.setContentTitle(getString(string.download_error))
                    .setContentText(e.message)
                    .setSmallIcon(drawable.stat_notify_error)
        }
        mgr!!.notify(NOTIFY_ID, builder.build())
    }

    private fun buildForegroundNotification(): Notification? {
        val builder = Builder(this, CHANNEL_ID)
        builder.setOngoing(true)
                .setContentTitle(actionString)
                .setContentText(filename)
                .setSmallIcon(drawable.stat_sys_download)
        return builder.build()
    }

    private val actionString: String
        get() = if (aciton == Aciton.DOWNLOAD_ONLY) getString(string.downloading_image) else getString(string.downloading_wallpaper)

    companion object {
        private val TAG = DownloadService::class.java.simpleName
        var isRunning = false
        const val TAG_URL = "url"
        const val TAG_FILENAME = "filename"
        const val TAG_ACTION = "action"
        private const val CHANNEL_ID = "download_channel_id"
        const val FOREGROUND_ID = 98732517
        const val NOTIFY_ID = 98732518
    }
}
