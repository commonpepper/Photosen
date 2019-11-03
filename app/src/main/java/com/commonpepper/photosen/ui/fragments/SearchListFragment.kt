package com.commonpepper.photosen.ui.fragments

import android.os.Bundle

import com.commonpepper.photosen.ui.viewmodels.SearchFragmentViewModelFactory
import androidx.lifecycle.ViewModelProviders

class SearchListFragment : AbstractListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        val query = args!!.getString(TAG_QUERY)
        val tags = args.getString(TAG_TAGS)
        val sort = args.getString(TAG_SORT)
        val factory = SearchFragmentViewModelFactory(query!!, tags!!, sort!!)

        mViewModel = ViewModelProviders.of(this, factory).get<SearchFragmentViewModelFactory.SearchListFragmentViewModel>(SearchFragmentViewModelFactory.SearchListFragmentViewModel::class.java)
    }

    companion object {
        private val TAG_QUERY = "query"
        private val TAG_TAGS = "tags"
        private val TAG_SORT = "sort"

        fun newInstance(query: String, tags: String, sort: String): SearchListFragment {
            val newFragment = SearchListFragment()
            val args = Bundle()
            args.putString(TAG_QUERY, query)
            args.putString(TAG_TAGS, tags)
            args.putString(TAG_SORT, sort)
            newFragment.arguments = args
            return newFragment
        }
    }
}
