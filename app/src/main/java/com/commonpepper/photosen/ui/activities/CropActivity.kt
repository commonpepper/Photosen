package com.commonpepper.photosen.ui.activities

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.commonpepper.photosen.R
import com.commonpepper.photosen.R.*
import com.theartofdev.edmodo.cropper.CropImageView
import com.theartofdev.edmodo.cropper.CropImageView.CropResult
import kotlinx.android.synthetic.main.activity_crop.*
import java.io.IOException

class CropActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_crop)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(drawable.ic_close_white_24dp)
            title = getString(string.crop_wallpaper)
        }
        val width = WallpaperManager.getInstance(this).desiredMinimumWidth
        val height = WallpaperManager.getInstance(this).desiredMinimumHeight
        val uriStr: String? = intent.getStringExtra(TAG_URISTR)
        val sourceUri: Uri? = Uri.parse(uriStr)
        cropView.setImageUriAsync(sourceUri)
        cropView.setAspectRatio(width, height)
        cropView.setOnCropImageCompleteListener { _, result: CropResult ->
            if (result.error == null) {
                try {
                    val bitmap: Bitmap? = result.bitmap
                    if (VERSION.SDK_INT >= VERSION_CODES.N) {
                        WallpaperManager.getInstance(this).setBitmap(bitmap, null, true,
                                WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
                    } else {
                        WallpaperManager.getInstance(this).setBitmap(bitmap)
                    }
                    finish()
                } catch (e: IOException) {
                    showError(e)
                }
            } else {
                showError(result.error)
            }
        }
    }

    private fun showError(e: Exception) {
        Toast.makeText(this@CropActivity, getString(string.error) + ": " + e.toString(),
                Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.crop_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.crop_done) {
            cropView!!.getCroppedImageAsync()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val TAG_URISTR = "tag_uri_str"
    }
}
