package com.commonpepper.photosen.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.Photosen.Companion.instance
import com.commonpepper.photosen.Photosen.Companion.isStoragePermissionGranted
import com.commonpepper.photosen.R
import com.commonpepper.photosen.R.*
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.model.PhotoSizes
import com.commonpepper.photosen.network.DownloadService
import com.commonpepper.photosen.network.DownloadService.Aciton
import com.commonpepper.photosen.ui.fragments.CommentsFragment
import com.commonpepper.photosen.ui.fragments.CommentsFragment.Companion.newInstance
import com.commonpepper.photosen.ui.fragments.PhotoDetailsFragment
import com.commonpepper.photosen.ui.fragments.PhotoDetailsFragment.Companion.newInstance
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton.Behavior
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_single_photo.*
import java.util.concurrent.Executors

class SinglePhotoActivity : AbstractNavActivity() {
    override val abstractDrawerLayout: DrawerLayout get() = drawerLayout
    private lateinit var photo: Photo
    private lateinit var detailsFragment: PhotoDetailsFragment
    private var action: Aciton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_single_photo)
        val toolbar: Toolbar = findViewById(id.single_image_toolbar)
        val navigationView: NavigationView = findViewById(id.navigationView)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        navigationView.setNavigationItemSelectedListener(this)
        photo = intent.getParcelableExtra(PHOTO_TAG)!!
        val saveHistory = intent.getBooleanExtra(SAVE_HISTORY_TAG, true)
        val historyDao = instance.database.historyDao
        if (saveHistory) {
            Executors.newSingleThreadExecutor().execute {
                photo.time = System.currentTimeMillis()
                historyDao.insert(photo)
            }
        }
        val title: TextView = findViewById(id.single_photo_title)
        val imageView: ImageView = findViewById(id.single_photo_image_view)
        val nestedScrollView: NestedScrollView = findViewById(id.single_image_nested_sc)
        val fabDownload: FloatingActionButton = findViewById(id.fab_download)
        val fabSetWallpaper: FloatingActionButton = findViewById(id.fab_wallpaper)
        hideFAB(fabDownload)
        hideFAB(fabSetWallpaper)
        fabDownload.setOnClickListener {
            action = Aciton.DOWNLOAD_ONLY
            if (isStoragePermissionGranted(this@SinglePhotoActivity)) {
                downloadPhoto()
            }
        }
        fabSetWallpaper.setOnClickListener {
            action = Aciton.WALLPAPER
            if (isStoragePermissionGranted(this@SinglePhotoActivity)) {
                downloadPhoto()
            }
        }
        if (photo.title != null && photo.title!!.isNotEmpty()) {
            title.text = photo.title
        } else {
            title.visibility = View.GONE
        }
        Picasso.get().load(photo.url_z).into(imageView)

//        LET'S GET FRAGMENT LINKS IN CASE OF SCREEN ROTATION
        var commentsFragment: CommentsFragment? = null
        var detailsFlag = false
        var commentsFlag = false
        for (fragment in supportFragmentManager.fragments) {
            if (fragment is PhotoDetailsFragment) {
                detailsFragment = fragment
                detailsFlag = true
            }
            if (fragment is CommentsFragment) {
                commentsFragment = fragment
                commentsFlag = true
            }
        }
        if (!detailsFlag) {
            detailsFragment = newInstance(photo)
            supportFragmentManager.beginTransaction()
                    .add(id.single_image_details_layout, detailsFragment)
                    .commit()
        }
        if (!commentsFlag) {
            commentsFragment = newInstance(photo.id)
            supportFragmentManager.beginTransaction()
                    .add(id.single_image_comments_layout, commentsFragment)
                    .commit()
        }
//        END OF RESTORING FRAGMENT LINKS


        commentsFragment!!.setNestedScrollView(nestedScrollView)
        detailsFragment.liveSizes.observe(this, Observer { x: PhotoSizes ->
            showFAB(fabDownload)
            showFAB(fabSetWallpaper)
            val prefs: SharedPreferences = getSharedPreferences(Photosen.PREFERENCES, Context.MODE_PRIVATE)
            val firstLaunch = prefs.getBoolean(PREFERENCE_FIRST_LAUNCH, true)
            if (firstLaunch) {
                TapTargetSequence(this).targets(
                        TapTarget.forView(fabDownload, getString(string.download_fab_hint))
                                .tintTarget(false),
                        TapTarget.forView(fabSetWallpaper, getString(string.set_as_wallpaper_fab_hint))
                                .tintTarget(false)
                ).continueOnCancel(true).start()
                prefs.edit().putBoolean(PREFERENCE_FIRST_LAUNCH, false).apply()
            }
            imageView.setOnClickListener {
                val sizes = x.sizes!!.size
                val url = sizes!![sizes.size - 1].source
                val intent = Intent(this@SinglePhotoActivity, PreviewActivity::class.java)
                intent.putExtra(PreviewActivity.TAG_URL, url)
                startActivity(intent)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun openInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.single_image_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.single_image_open_in_browser) {
            openInBrowser(PHOTOS_URL + photo.owner + "/" + photo.id)
        } else if (id == R.id.single_image_share) {
            shareUrl(PHOTOS_URL + photo.owner + "/" + photo.id)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun downloadPhoto() {
        if (!DownloadService.isRunning) {
            Toast.makeText(this, getString(string.download_started), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DownloadService::class.java)
            if (detailsFragment.liveSizes.value != null) { //shouldn't be null, when fabs are visible, but check just in case

                val sizes = detailsFragment.liveSizes.value!!.sizes!!.size
                val url = sizes!![sizes.size - 1].source
                intent.putExtra(DownloadService.TAG_URL, url)
                val urlSplit = url!!.split("\\.").toTypedArray()
                var format = urlSplit[urlSplit.size - 1]
                if (format != "jpg" && format != "gif" && format != "png") format = "jpg"
                intent.putExtra(DownloadService.TAG_FILENAME, photo.id + "." + format)
                intent.putExtra(DownloadService.TAG_ACTION, action)
                startService(intent)
            }
        } else {
            Toast.makeText(this, getString(string.please_wait_for_current_download), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
            if (action != null) downloadPhoto()
        } else {
            Toast.makeText(this, getString(string.no_permisson), Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareUrl(url: String) {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        share.putExtra(Intent.EXTRA_SUBJECT, string.flickr_image)
        share.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(share, getString(string.share_with)))
    }

    private fun hideFAB(fab: FloatingActionButton) {
        val params = fab.layoutParams as LayoutParams
        val behavior = params.behavior as Behavior?
        if (behavior != null) behavior.isAutoHideEnabled = false
        fab.hide()
    }

    private fun showFAB(fab: FloatingActionButton) {
        fab.show()
        val params = fab.layoutParams as LayoutParams
        val behavior = params.behavior as Behavior?
        if (behavior != null) behavior.isAutoHideEnabled = true
    }

    companion object {
        private val TAG = SinglePhotoActivity::class.java.simpleName
        const val PHOTO_TAG = "PARCELABLE_PHOTO"
        const val SAVE_HISTORY_TAG = "SAVE_HISTORY"
        const val PHOTOS_URL = "https://www.flickr.com/photos/"
        private const val PREFERENCE_FIRST_LAUNCH = "SINGLE_PHOTO_FIRST_LAUNCH"
    }
}
