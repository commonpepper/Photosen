package com.commonpepper.photosen.ui.activities

import android.R.string
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commonpepper.photosen.Photosen.Companion.instance
import com.commonpepper.photosen.R
import com.commonpepper.photosen.R.id
import com.commonpepper.photosen.R.layout
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.ui.adapters.HistoryAdapter
import com.commonpepper.photosen.ui.viewmodels.HistoryActivityViewModel
import com.google.android.material.navigation.NavigationView
import java.util.concurrent.Executors

class HistoryActivity : AbstractNavActivity() {
    private var viewModel: HistoryActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_history)
        val toolbar: Toolbar = findViewById(id.about_toolbar)
        drawerLayout = findViewById(id.drawer_layout)
        val navigationView: NavigationView = findViewById(id.nav_view)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = getString(R.string.history)
        navigationView.menu.findItem(id.drawer_history).isCheckable = true
        navigationView.menu.findItem(id.drawer_history).isChecked = true
        navigationView.setNavigationItemSelectedListener(this)
        viewModel = ViewModelProviders.of(this).get(HistoryActivityViewModel::class.java)
        val recyclerView: RecyclerView = findViewById(id.images_recyclerView)
        val adapter = HistoryAdapter()
        viewModel!!.photosList.observe(this, Observer { pagedList: PagedList<Photo?> -> adapter.submitList(pagedList) })
        val llm = LinearLayoutManager(this)
        llm.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = llm
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.clear_history) {
            Builder(this)
                    .setTitle(getString(R.string.clear_history))
                    .setMessage(getString(R.string.clear_confirm))
                    .setPositiveButton(string.yes) { dialog: DialogInterface?, whichButton: Int -> Executors.newSingleThreadExecutor().execute { instance!!.database!!.historyDao.clear() } }
                    .setNegativeButton(string.no, null).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
