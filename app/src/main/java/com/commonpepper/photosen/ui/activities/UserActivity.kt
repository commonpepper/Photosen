package com.commonpepper.photosen.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.commonpepper.photosen.R.id
import com.commonpepper.photosen.R.layout
import com.commonpepper.photosen.ui.fragments.UserPhotosListFragment
import com.commonpepper.photosen.ui.fragments.UserPhotosListFragment.Companion.newInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso

class UserActivity : AbstractNavActivity() {
    internal var fragment: UserPhotosListFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_user)
        val toolbar: Toolbar = findViewById(id.toolbar)
        drawerLayout = findViewById(id.drawer_layout)
        val navigationView: NavigationView = findViewById(id.nav_view)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        navigationView.setNavigationItemSelectedListener(this)
        val user_id: String = intent.getStringExtra(USER_ID_TAG)
        val username: String? = intent.getStringExtra(USERNAME_TAG)
        val iconUrl: String? = intent.getStringExtra(ICON_URL_TAG)
        supportActionBar!!.title = username
        val avatar: ImageView = findViewById(id.avatar)
        Picasso.get().load(iconUrl).into(avatar)
        val fab: FloatingActionButton = findViewById(id.openinbrowser_fab)
        fab.setOnClickListener { v: View? -> openInBrowser("https://flickr.com/photos/$user_id") }
        if (supportFragmentManager.fragments.size == 0) {
            fragment = newInstance(user_id)
            supportFragmentManager.beginTransaction()
                    .add(id.user_photos_fragment_layout, fragment!!)
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
        const val USER_ID_TAG = "USER_ID_TAG"
        const val USERNAME_TAG = "USERNAME_TAG"
        const val ICON_URL_TAG = "ICON_URL_TAG"
        fun getStartingIntent(context: Context?, user_id: String?, username: String?, iconUrl: String?): Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(USER_ID_TAG, user_id)
            intent.putExtra(USERNAME_TAG, username)
            intent.putExtra(ICON_URL_TAG, iconUrl)
            return intent
        }
    }
}
