package com.commonpepper.photosen.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.commonpepper.photosen.R.*
import com.commonpepper.photosen.ui.adapters.MyPagerAdapter
import com.commonpepper.photosen.ui.fragments.SearchListFragment.Companion.newInstance
import com.commonpepper.photosen.ui.viewmodels.SearchActivityViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class SearchActivity : AbstractNavActivity() {
    private var searchText: EditText? = null
    private var chipGroup: ChipGroup? = null
    private var firstChip: Chip? = null
    private var inputTag: EditText? = null
    private var viewPager: ViewPager? = null
    private var mPagerAdapter: MyPagerAdapter? = null
    private var tabLayout: TabLayout? = null
    private var viewModel: SearchActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_search)
        viewPager = findViewById<ViewPager?>(id.view_pager)
        tabLayout = findViewById<TabLayout?>(id.tab_layout)
        searchText = findViewById<EditText?>(id.search_edit_text)
        val toolbar: Toolbar = findViewById(id.search_toolbar)
        chipGroup = findViewById<ChipGroup?>(id.search_chip_group)
        firstChip = findViewById<Chip?>(id.search_first_chip)
        inputTag = findViewById<EditText?>(id.tag_input)
        drawerLayout = findViewById(id.drawer_layout)
        val navigationView: NavigationView = findViewById(id.nav_view)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        navigationView.menu.findItem(id.drawer_search).isCheckable = true
        navigationView.menu.findItem(id.drawer_search).isChecked = true
        navigationView.setNavigationItemSelectedListener(this)
        mPagerAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager!!.adapter = mPagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)
        val intent: Intent = intent
        val firstTag: String? = intent.getStringExtra(TAG_SEARCHTAG)
        var recreated = false
        if (savedInstanceState != null) recreated = savedInstanceState.getBoolean(TAG_RECREATED)
        viewModel = ViewModelProviders.of(this).get(SearchActivityViewModel::class.java)
        if (viewModel!!.tags.size > 0 || firstTag == null || recreated) {
            for (tag in viewModel!!.tags) {
                addNewChip(tag)
            }
            chipGroup!!.removeView(firstChip)
            if (!recreated) {
                window.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            } else {
                doSearch()
            }
        } else {
            viewModel!!.tags.add(firstTag)
            firstChip!!.text = firstTag
            firstChip!!.setOnCloseIconClickListener(onChipClickListener)
            doSearch()
        }
        searchText!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            viewModel!!.queue = searchText!!.text.toString()
            doSearch()
            true
        }
        inputTag!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (event == null || !event.isShiftPressed) {
                    val newTag = inputTag!!.text.toString()
                    if (newTag.length > 0 && !viewModel!!.tags.contains(newTag)) {
                        addNewChip(newTag)
                        viewModel!!.tags.add(newTag)
                        inputTag!!.setText("")
                        doSearch()
                    }
                    return@setOnEditorActionListener true
                }
            }
            false
        }
    }

    private fun addNewChip(newTag: String) {
        val chip = Chip(this)
        chip.text = newTag
        chip.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener(onChipClickListener)
        chipGroup!!.addView(chip)
    }

    private val onChipClickListener = OnClickListener { x ->
        val chip = x as Chip
        chipGroup!!.removeView(chip)
        viewModel!!.tags.remove(chip.text.toString())
        doSearch()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        if (searchText!!.text.toString().length > 0) {
            window.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
    }

    private fun doSearch() {
        var query = viewModel!!.queue
        if (query != null && query.length == 0) query = null
        val tagsExtra = viewModel!!.tagsToString()
        if (query != null || tagsExtra != null) {
            tabLayout!!.visibility = View.VISIBLE
            val fragmentRelevant = newInstance(query, tagsExtra, "relevance")
            val fragmentMostViewed = newInstance(query, tagsExtra, "interestingness-desc")
            val fragmentLatest = newInstance(query, tagsExtra, "date-posted-desc")
            mPagerAdapter!!.clear()
            mPagerAdapter!!.addFragment(fragmentRelevant, getString(string.relevant))
            mPagerAdapter!!.addFragment(fragmentMostViewed, getString(string.most_viewed))
            mPagerAdapter!!.addFragment(fragmentLatest, getString(string.latest))
            var view = this.currentFocus
            if (view == null) view = View(this)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putBoolean(TAG_RECREATED, true)
        super.onSaveInstanceState(savedInstanceState)
    }

    companion object {
        const val TAG_SEARCHTAG = "search_tag"
        private const val TAG_RECREATED = "rotated_tag"
    }
}
