package com.commonpepper.photosen.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.commonpepper.photosen.R.*
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContentView(layout.activity_preview)
        val photoView: PhotoView = findViewById(id.preview_image)
        val progressBar: ProgressBar = findViewById(id.preview_progress)
        val url: String? = intent.getStringExtra(TAG_URL)
        if (url != null) {
            Picasso.get().load(url).resize(MAX_WIDTH_PIXELS, 0).onlyScaleDown().into(photoView, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@PreviewActivity, getString(string.something_went_wrong), Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            finish()
            Toast.makeText(this, getString(string.something_went_wrong), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val TAG_URL = "tag_url"
        private const val MAX_WIDTH_PIXELS = 5000
    }
}
