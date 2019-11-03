package com.commonpepper.photosen.ui.activities

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.commonpepper.photosen.R
import com.theartofdev.edmodo.cropper.CropImageView

import java.io.IOException
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class CropActivity : AppCompatActivity() {
    private var cropView: CropImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        cropView = findViewById(R.id.cropView)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        supportActionBar!!.title = getString(R.string.crop_wallpaper)

        val width = WallpaperManager.getInstance(this).desiredMinimumWidth
        val height = WallpaperManager.getInstance(this).desiredMinimumHeight

        val uriStr = intent.getStringExtra(TAG_URISTR)
        val sourceUri = Uri.parse(uriStr)
        cropView!!.setImageUriAsync(sourceUri)
        cropView!!.setAspectRatio(width, height)

        cropView!!.setOnCropImageCompleteListener { view, result ->
            if (result.error == null) {
                try {
                    val bitmap = result.bitmap
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
        Toast.makeText(this@CropActivity, getString(R.string.error) + ": " + e.toString(),
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
        val TAG_URISTR = "tag_uri_str"
    }
}
