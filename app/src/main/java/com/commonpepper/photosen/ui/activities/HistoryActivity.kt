package com.commonpepper.photosen.ui.activities

import android.R.string
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog.Builder
import androidx.drawerlayout.widget.DrawerLayout
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
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.navigation_view.*
import java.util.concurrent.Executors

class HistoryActivity : AbstractNavActivity() {
    private lateinit var viewModel: HistoryActivityViewModel
    override val abstractDrawerLayout: DrawerLayout get() = drawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_history)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.history)
        }
        navigationView.menu.findItem(id.drawer_history).apply {
            isCheckable = true
            isChecked = true
        }
        navigationView.setNavigationItemSelectedListener(this)
        viewModel = ViewModelProviders.of(this).get(HistoryActivityViewModel::class.java)
        val recyclerView: RecyclerView = findViewById(id.recyclerViewHistory)
        val adapter = HistoryAdapter()
        viewModel.photosList.observe(this, Observer { pagedList: PagedList<Photo?> -> adapter.submitList(pagedList) })
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
                    .setPositiveButton(string.yes) { _, _ -> Executors.newSingleThreadExecutor().execute { instance.database.historyDao.clear() } }
                    .setNegativeButton(string.no, null).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
