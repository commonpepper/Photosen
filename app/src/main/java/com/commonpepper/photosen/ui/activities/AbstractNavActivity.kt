package com.commonpepper.photosen.ui.activities

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.commonpepper.photosen.R
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

abstract class AbstractNavActivity : AppCompatActivity(), OnNavigationItemSelectedListener {
    protected var drawerLayout: DrawerLayout? = null
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.drawer_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.drawer_popular -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.drawer_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.drawer_categories -> {
                val intent = Intent(this, CategoriesActivity::class.java)
                startActivity(intent)
            }
            R.id.drawer_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
            }
        }
        if (drawerLayout != null) drawerLayout!!.closeDrawers()
        return true
    }
}
