package com.commonpepper.photosen.ui.activities

import android.os.Bundle

import com.commonpepper.photosen.R
import com.commonpepper.photosen.ui.adapters.CategoriesAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoriesActivity : AbstractNavActivity() {
    private var mToolbar: Toolbar? = null
    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        mToolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        mRecyclerView = findViewById(R.id.categories_recycler)

        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = getString(R.string.categories)

        navigationView.menu.findItem(R.id.drawer_categories).isCheckable = true
        navigationView.menu.findItem(R.id.drawer_categories).isChecked = true
        navigationView.setNavigationItemSelectedListener(this)

        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = GridLayoutManager(this, 2)
        mRecyclerView!!.adapter = CategoriesAdapter(resources.getStringArray(R.array.categories))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
