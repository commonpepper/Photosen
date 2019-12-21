package com.commonpepper.photosen.ui.activities

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commonpepper.photosen.R.*
import com.commonpepper.photosen.ui.adapters.CategoriesAdapter
import com.google.android.material.navigation.NavigationView

class CategoriesActivity : AbstractNavActivity() {
    internal var mToolbar: Toolbar? = null
    internal var mRecyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_categories)
        mToolbar = findViewById<Toolbar?>(id.toolbar)
        drawerLayout = findViewById(id.drawer_layout)
        val navigationView: NavigationView = findViewById(id.nav_view)
        mRecyclerView = findViewById<RecyclerView?>(id.categories_recycler)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = getString(string.categories)
        navigationView.menu.findItem(id.drawer_categories).isCheckable = true
        navigationView.menu.findItem(id.drawer_categories).isChecked = true
        navigationView.setNavigationItemSelectedListener(this)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = GridLayoutManager(this, 2)
        mRecyclerView!!.adapter = CategoriesAdapter(resources.getStringArray(array.categories))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
