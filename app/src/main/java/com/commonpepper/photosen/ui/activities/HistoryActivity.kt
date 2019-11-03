package com.commonpepper.photosen.ui.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.R
import com.commonpepper.photosen.ui.adapters.HistoryAdapter
import com.commonpepper.photosen.ui.viewmodels.HistoryActivityViewModel
import com.google.android.material.navigation.NavigationView

import java.util.concurrent.Executors

class HistoryActivity : AbstractNavActivity() {

    private var viewModel: HistoryActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val toolbar = findViewById<Toolbar>(R.id.about_toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setTitle(getString(R.string.history))

        navigationView.menu.findItem(R.id.drawer_history).isCheckable = true
        navigationView.menu.findItem(R.id.drawer_history).isChecked = true
        navigationView.setNavigationItemSelectedListener(this)

        viewModel = ViewModelProviders.of(this).get(HistoryActivityViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.images_recyclerView)

        val adapter = HistoryAdapter()
        viewModel!!.photosList.observe(this, Observer { adapter.submitList(it) })

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
            AlertDialog.Builder(this)
                    .setTitle(getString(R.string.clear_history))
                    .setMessage(getString(R.string.clear_confirm))
                    .setPositiveButton(android.R.string.yes) { dialog, whichButton ->
                        Executors.newSingleThreadExecutor().execute { Photosen.instance!!.database!!.historyDao.clear() } }
                    .setNegativeButton(android.R.string.no, null).show()

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
