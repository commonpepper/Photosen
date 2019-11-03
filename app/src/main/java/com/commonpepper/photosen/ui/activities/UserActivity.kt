package com.commonpepper.photosen.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout

import com.commonpepper.photosen.R
import com.commonpepper.photosen.ui.fragments.UserPhotosListFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso

class UserActivity : AbstractNavActivity() {

    private var fragment: UserPhotosListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        navigationView.setNavigationItemSelectedListener(this)

        val user_id = intent.getStringExtra(USER_ID_TAG)
        val username = intent.getStringExtra(USERNAME_TAG)
        val iconUrl = intent.getStringExtra(ICON_URL_TAG)

        supportActionBar!!.title = username

        val avatar = findViewById<ImageView>(R.id.avatar)
        Picasso.get().load(iconUrl).into(avatar)

        val fab = findViewById<FloatingActionButton>(R.id.openinbrowser_fab)
        fab.setOnClickListener { v -> openInBrowser("https://flickr.com/photos/" + user_id!!) }

        if (supportFragmentManager.fragments.size == 0) {
            fragment = UserPhotosListFragment.newInstance(user_id!!)
            supportFragmentManager.beginTransaction()
                    .add(R.id.user_photos_fragment_layout, fragment!!)
                    .commit()
        } else {
            fragment = supportFragmentManager.fragments[0] as UserPhotosListFragment
        }
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

    companion object {
        val USER_ID_TAG = "USER_ID_TAG"
        val USERNAME_TAG = "USERNAME_TAG"
        val ICON_URL_TAG = "ICON_URL_TAG"

        fun getStartingIntent(context: Context, user_id: String, username: String, iconUrl: String): Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(USER_ID_TAG, user_id)
            intent.putExtra(USERNAME_TAG, username)
            intent.putExtra(ICON_URL_TAG, iconUrl)
            return intent
        }
    }
}
