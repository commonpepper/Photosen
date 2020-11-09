package com.commonpepper.photosen.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.commonpepper.photosen.BuildConfig
import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.Photosen.Companion.firebaseAnalytics
import com.commonpepper.photosen.R.*
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.navigation_view.*
import java.lang.RuntimeException

class AboutActivity : AbstractNavActivity() {
    override val abstractDrawerLayout: DrawerLayout get() = drawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_about)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(string.about)
        }
        navigationView.menu.findItem(id.drawer_about).apply {
            isCheckable = true
            isChecked = true
        }
        navigationView.setNavigationItemSelectedListener(this)
        versionView.text = BuildConfig.VERSION_NAME
        listOf(findViewById(id.layout_about_introduction),
                findViewById(id.layout_about_version),
                findViewById(id.layout_about_github),
                findViewById(id.layout_about_flickr),
                findViewById(id.layout_about_rate),
                findViewById(id.layout_about_retrofit),
                findViewById(id.layout_about_picasso),
                findViewById(id.layout_about_photoview),
                findViewById(id.layout_about_image_cropper),
                findViewById(id.layout_about_taptargetview),
                findViewById(id.layout_about_androidrate),
                findViewById(id.layout_about_appintro),
                findViewById<LinearLayout>(id.layout_about_expandable)
        ).forEach { it.setOnClickListener(clickListener) }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val clickListener = OnClickListener { view: View ->
        when (view.id) {
            id.layout_about_introduction -> {
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent)
            }
            id.layout_about_github -> openUrl("https://" + resources.getString(string.github_link))
            id.layout_about_flickr -> openUrl("https://flickr.com")
            id.layout_about_rate -> {
                firebaseAnalytics.logEvent("ABOUT_RATE", null)
                val uri: Uri? = Uri.parse("market://details?id=" + Photosen.PACKAGE_NAME)
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
            id.layout_about_retrofit -> openUrl("https://github.com/square/retrofit")
            id.layout_about_picasso -> openUrl("https://github.com/square/picasso")
            id.layout_about_photoview -> openUrl("https://github.com/chrisbanes/PhotoView")
            id.layout_about_image_cropper -> openUrl("https://github.com/ArthurHub/Android-Image-Cropper")
            id.layout_about_taptargetview -> openUrl("https://github.com/KeepSafe/TapTargetView")
            id.layout_about_androidrate -> openUrl("https://github.com/Vorlonsoft/AndroidRate")
            id.layout_about_appintro -> openUrl("https://github.com/AppIntro/AppIntro")
            id.layout_about_expandable -> openUrl("https://github.com/cachapa/ExpandableLayout")
        }
    }

    private fun openUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}