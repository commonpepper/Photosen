package com.commonpepper.photosen.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

import com.commonpepper.photosen.R
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import androidx.appcompat.app.AppCompatActivity

class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContentView(R.layout.activity_preview)

        val photoView = findViewById<PhotoView>(R.id.preview_image)
        val progressBar = findViewById<ProgressBar>(R.id.preview_progress)

        val url = intent.getStringExtra(TAG_URL)
        if (url != null) {
            Picasso.get().load(url).resize(MAX_WIDTH_PIXELS, 0).onlyScaleDown().into(photoView, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@PreviewActivity, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            finish()
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        val TAG_URL = "tag_url"

        private val MAX_WIDTH_PIXELS = 5000
    }
}
