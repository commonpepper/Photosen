package com.commonpepper.photosen.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import com.commonpepper.photosen.R.*
import com.commonpepper.photosen.ui.adapters.MyPagerAdapter
import com.commonpepper.photosen.ui.fragments.SearchListFragment.Companion.newInstance
import com.commonpepper.photosen.ui.viewmodels.SearchActivityViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.navigation_view.*

class SearchActivity : AbstractNavActivity() {
    override val abstractDrawerLayout: DrawerLayout get() = drawerLayout
    private lateinit var pagerAdapter: MyPagerAdapter
    private lateinit var viewModel: SearchActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        navigationView.menu.findItem(id.drawer_search).apply {
            isCheckable = true
            isChecked = true
        }
        navigationView.setNavigationItemSelectedListener(this)
        pagerAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        val firstTag: String? = intent.getStringExtra(TAG_SEARCHTAG)
        val recreated = savedInstanceState?.getBoolean(TAG_RECREATED) ?: false
        viewModel = ViewModelProviders.of(this)[SearchActivityViewModel::class.java]
        if (viewModel.tags.size > 0 || firstTag == null || recreated) {
            viewModel.tags.forEach { addNewChip(it) }
            searchChipGroup.removeView(searchFirstChip)
            if (!recreated) {
                window.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            } else {
                doSearch()
            }
        } else {
            viewModel.tags.add(firstTag)
            searchFirstChip.text = firstTag
            searchFirstChip.setOnCloseIconClickListener(onChipClickListener)
            doSearch()
        }
        searchText.setOnEditorActionListener { _, _, _ ->
            viewModel.query = searchText!!.text.toString()
            doSearch()
            true
        }
        tagInput.setOnEditorActionListener { _, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (event == null || !event.isShiftPressed) {
                    val newTag = tagInput!!.text.toString()
                    if (newTag.isNotEmpty() && !viewModel.tags.contains(newTag)) {
                        addNewChip(newTag)
                        viewModel.tags.add(newTag)
                        tagInput.setText("")
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
        searchChipGroup.addView(chip)
    }

    private val onChipClickListener = OnClickListener { x ->
        val chip = x as Chip
        searchChipGroup.removeView(chip)
        viewModel.tags.remove(chip.text.toString())
        doSearch()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        if (searchText.text.toString().isNotEmpty()) {
            window.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
    }

    private fun doSearch() {
        val query = viewModel.query
        val tagsExtra = viewModel.tags.joinToString(",")
        if ((query != null && query.isNotEmpty()) || tagsExtra.isNotEmpty()) {
            tabLayout.visibility = View.VISIBLE
            val fragmentRelevant = newInstance(query, tagsExtra, "relevance")
            val fragmentMostViewed = newInstance(query, tagsExtra, "interestingness-desc")
            val fragmentLatest = newInstance(query, tagsExtra, "date-posted-desc")
            pagerAdapter.apply {
                clear()
                addFragment(fragmentRelevant, getString(string.relevant))
                addFragment(fragmentMostViewed, getString(string.most_viewed))
                addFragment(fragmentLatest, getString(string.latest))
            }
            val view = this.currentFocus ?: View(this)
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
        const val TAG_RECREATED = "rotated_tag"
    }
}
