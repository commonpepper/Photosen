package com.commonpepper.photosen.ui.activities

import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.commonpepper.photosen.R.*
import com.commonpepper.photosen.ui.adapters.CategoriesAdapter
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.navigation_view.*

class CategoriesActivity : AbstractNavActivity() {
    override val abstractDrawerLayout: DrawerLayout get() = drawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_categories)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(string.categories)
        }
        navigationView.menu.findItem(id.drawer_categories).apply {
            isCheckable = true
            isChecked = true
        }
        navigationView.setNavigationItemSelectedListener(this)
        recyclerCategories.setHasFixedSize(true)
        recyclerCategories.layoutManager = GridLayoutManager(this, 2)
        recyclerCategories.adapter = CategoriesAdapter(resources.getStringArray(array.categories))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
