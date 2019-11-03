package com.commonpepper.photosen.ui.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.commonpepper.photosen.ui.viewmodels.PhotoFragmentViewModelFactory

class PhotoListFragment : AbstractListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        val order_by = args!!.getString(TAG_DATE)
        val factory = PhotoFragmentViewModelFactory(order_by!!)

        mViewModel = ViewModelProviders.of(this, factory).get<PhotoFragmentViewModelFactory.PhotoListFragmentViewModel>(PhotoFragmentViewModelFactory.PhotoListFragmentViewModel::class.java)
    }

    companion object {
        private val TAG_DATE = "date"

        fun newInstance(date: String?): PhotoListFragment {
            val newFragment = PhotoListFragment()
            val args = Bundle()
            args.putString(TAG_DATE, date)
            newFragment.arguments = args
            return newFragment
        }
    }
}
