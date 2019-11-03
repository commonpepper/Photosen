package com.commonpepper.photosen.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.commonpepper.photosen.BuildConfig
import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.R
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar

class AboutActivity : AbstractNavActivity() {

    private val clickListener = { view : View ->
        when (view.id) {
            R.id.layout_about_introduction -> {
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent)
            }
            R.id.layout_about_github -> openUrl("https://" + resources.getString(R.string.github_link))
            R.id.layout_about_flickr -> openUrl("https://flickr.com")
            R.id.layout_about_rate -> {
                Photosen.firebaseAnalytics!!.logEvent("ABOUT_RATE", null)
                val uri = Uri.parse("market://details?id=" + Photosen.PACKAGE_NAME)
                val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                try {
                    startActivity(goToMarket)
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + Photosen.PACKAGE_NAME)))
                }

            }
            R.id.layout_about_retrofit -> openUrl("https://github.com/square/retrofit")
            R.id.layout_about_picasso -> openUrl("https://github.com/square/picasso")
            R.id.layout_about_photoview -> openUrl("https://github.com/chrisbanes/PhotoView")
            R.id.layout_about_image_cropper -> openUrl("https://github.com/ArthurHub/Android-Image-Cropper")
            R.id.layout_about_taptargetview -> openUrl("https://github.com/KeepSafe/TapTargetView")
            R.id.layout_about_androidrate -> openUrl("https://github.com/Vorlonsoft/AndroidRate")
            R.id.layout_about_appintro -> openUrl("https://github.com/AppIntro/AppIntro")
            R.id.layout_about_expandable -> openUrl("https://github.com/cachapa/ExpandableLayout")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val toolbar = findViewById<Toolbar>(R.id.about_toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val versionView = findViewById<TextView>(R.id.version_view)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = getString(R.string.about)

        navigationView.menu.findItem(R.id.drawer_about).isCheckable = true
        navigationView.menu.findItem(R.id.drawer_about).isChecked = true
        navigationView.setNavigationItemSelectedListener(this)

        versionView.text = BuildConfig.VERSION_NAME

        val containers = arrayOf(findViewById(R.id.layout_about_introduction), findViewById(R.id.layout_about_version), findViewById(R.id.layout_about_github), findViewById(R.id.layout_about_flickr), findViewById(R.id.layout_about_rate), findViewById(R.id.layout_about_retrofit), findViewById(R.id.layout_about_picasso), findViewById(R.id.layout_about_photoview), findViewById(R.id.layout_about_image_cropper), findViewById(R.id.layout_about_taptargetview), findViewById(R.id.layout_about_androidrate), findViewById(R.id.layout_about_appintro), findViewById<LinearLayout>(R.id.layout_about_expandable))
        for (r in containers) {
            r.setOnClickListener(clickListener)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun openUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}
