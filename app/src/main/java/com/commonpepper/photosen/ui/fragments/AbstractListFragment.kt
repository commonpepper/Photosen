package com.commonpepper.photosen.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commonpepper.photosen.R
import com.commonpepper.photosen.R.layout
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.network.NetworkState
import com.commonpepper.photosen.ui.adapters.PhotoAdapter
import com.commonpepper.photosen.ui.viewmodels.AbstractListFragmentViewModel

open class AbstractListFragment : Fragment() {
    protected var mViewModel: AbstractListFragmentViewModel<*>? = null
    protected var mRecyclerView: RecyclerView? = null
    protected var mPhotoAdapter = PhotoAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(layout.fragment_images, container, false)
        mRecyclerView = view.findViewById(R.id.fragment_images_recyclerView)
        mViewModel!!.photosList.observe(this, Observer { pagedList: PagedList<*> -> mPhotoAdapter.submitList(pagedList as PagedList<Photo?>) })
        mViewModel!!.networkState.observe(this, Observer { networkState: NetworkState -> mPhotoAdapter.setNetworkState(networkState) })
        val llm = LinearLayoutManager(context)
        llm.orientation = RecyclerView.VERTICAL
        mRecyclerView!!.layoutManager = llm
        mRecyclerView!!.adapter = mPhotoAdapter
        return view
    }

    fun scrollToTop() {
        mRecyclerView?.scrollToPosition(0)
    }
}