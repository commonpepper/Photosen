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
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.R
import com.commonpepper.photosen.database.HistoryDao
import com.commonpepper.photosen.network.DownloadService
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.model.PhotoSizes
import com.commonpepper.photosen.ui.fragments.CommentsFragment
import com.commonpepper.photosen.ui.fragments.PhotoDetailsFragment
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import java.util.concurrent.Executors

class SinglePhotoActivity : AbstractNavActivity() {

    private var photo: Photo? = null

    private var action: DownloadService.Aciton? = null
    private var detailsFragment: PhotoDetailsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_photo)

        val toolbar = findViewById<Toolbar>(R.id.single_image_toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        navigationView.setNavigationItemSelectedListener(this)

        photo = intent.getParcelableExtra(PHOTO_TAG)
        val saveHistory = intent.getBooleanExtra(SAVE_HISTORY_TAG, true)

        val historyDao = Photosen.instance!!.database!!.historyDao

        if (saveHistory) {
            Executors.newSingleThreadExecutor().execute {
                photo!!.time = System.currentTimeMillis()
                historyDao.insert(photo)
            }
        }

        val title = findViewById<TextView>(R.id.single_photo_title)
        val imageView = findViewById<ImageView>(R.id.single_photo_image_view)
        val nestedScrollView = findViewById<NestedScrollView>(R.id.single_image_nested_sc)

        val fabDownload = findViewById<FloatingActionButton>(R.id.fab_download)
        val fabSetWallpaper = findViewById<FloatingActionButton>(R.id.fab_wallpaper)
        hideFAB(fabDownload)
        hideFAB(fabSetWallpaper)
        fabDownload.setOnClickListener { v ->
            action = DownloadService.Aciton.DOWNLOAD_ONLY
            if (Photosen.isStoragePermissionGranted(this@SinglePhotoActivity)) {
                downloadPhoto()
            }
        }
        fabSetWallpaper.setOnClickListener { v ->
            action = DownloadService.Aciton.WALLPAPER
            if (Photosen.isStoragePermissionGranted(this@SinglePhotoActivity)) {
                downloadPhoto()
            }
        }

        if (photo!!.title != null && photo!!.title.isNotEmpty()) {
            title.text = photo!!.title
        } else {
            title.visibility = View.GONE
        }

        Picasso.get().load(photo!!.url_z).into(imageView)

        //        OLD VERSION:
        //        if (getSupportFragmentManager().getFragments().size() == 0) {
        //            detailsFragment = PhotoDetailsFragment.newInstance(photo);
        //            getSupportFragmentManager().beginTransaction()
        //                    .add(R.id.single_image_details_layout, detailsFragment)
        //                    .commit();
        //        } else {
        //            detailsFragment = (PhotoDetailsFragment) getSupportFragmentManager().getFragments().get(0);
        //        }

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
            detailsFragment = PhotoDetailsFragment.newInstance(photo!!)
            supportFragmentManager.beginTransaction()
                    .add(R.id.single_image_details_layout, detailsFragment!!)
                    .commit()
        }
        if (!commentsFlag) {
            commentsFragment = CommentsFragment.newInstance(photo!!.id)
            supportFragmentManager.beginTransaction()
                    .add(R.id.single_image_comments_layout, commentsFragment)
                    .commit()
        }
        //        END OF RESTORING FRAGMENT LINKS
        commentsFragment!!.setNestedScrollView(nestedScrollView)

        detailsFragment!!.liveSizes.observe(this, Observer { x ->
            showFAB(fabDownload)
            showFAB(fabSetWallpaper)

            val prefs = getSharedPreferences(Photosen.PREFERENCES, Context.MODE_PRIVATE)
            val firstLaunch = prefs.getBoolean(PREFERENCE_FIRST_LAUNCH, true)
            if (firstLaunch) {
                TapTargetSequence(this).targets(
                        TapTarget.forView(fabDownload, getString(R.string.download_fab_hint))
                                .tintTarget(false),
                        TapTarget.forView(fabSetWallpaper, getString(R.string.set_as_wallpaper_fab_hint))
                                .tintTarget(false)
                ).continueOnCancel(true).start()
                prefs.edit().putBoolean(PREFERENCE_FIRST_LAUNCH, false).apply()
            }

            imageView.setOnClickListener { v ->
                val sizes = x.sizes.size
                val url = sizes[sizes.size - 1].source
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
            openInBrowser(PHOTOS_URL + photo!!.owner + "/" + photo!!.id)
        } else if (id == R.id.single_image_share) {
            shareUrl(PHOTOS_URL + photo!!.owner + "/" + photo!!.id)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun downloadPhoto() {
        if (!DownloadService.isRunning) {
            Toast.makeText(this, getString(R.string.download_started), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DownloadService::class.java)
            if (detailsFragment!!.liveSizes.value != null) { //shouldn't be null, when fabs are visible, but check just in case
                val sizes = detailsFragment!!.liveSizes.value!!.sizes.size
                val url = sizes[sizes.size - 1].source
                intent.putExtra(DownloadService.TAG_URL, url)
                val urlSplit = url.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                var format = urlSplit[urlSplit.size - 1]
                if (format != "jpg" && format != "gif" && format != "png")
                    format = "jpg"
                intent.putExtra(DownloadService.TAG_FILENAME, photo!!.id + "." + format)
                intent.putExtra(DownloadService.TAG_ACTION, action)
                startService(intent)
            }
        } else {
            Toast.makeText(this, getString(R.string.please_wait_for_current_download), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
            if (action != null) downloadPhoto()
        } else {
            Toast.makeText(this, getString(R.string.no_permisson), Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareUrl(url: String) {
        if (photo != null) {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

            share.putExtra(Intent.EXTRA_SUBJECT, R.string.flickr_image)
            share.putExtra(Intent.EXTRA_TEXT, url)

            startActivity(Intent.createChooser(share, getString(R.string.share_with)))
        }
    }

    private fun hideFAB(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior?
        if (behavior != null) behavior.isAutoHideEnabled = false
        fab.hide()
    }

    private fun showFAB(fab: FloatingActionButton) {
        fab.show()
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior?
        if (behavior != null) behavior.isAutoHideEnabled = true
    }

    companion object {
        private val TAG = SinglePhotoActivity::class.java.simpleName

        val PHOTO_TAG = "PARCELABLE_PHOTO"
        val SAVE_HISTORY_TAG = "SAVE_HISTORY"
        val PHOTOS_URL = "https://www.flickr.com/photos/"

        private val PREFERENCE_FIRST_LAUNCH = "SINGLE_PHOTO_FIRST_LAUNCH"
    }
}
