package com.commonpepper.photosen.ui.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders

import com.commonpepper.photosen.ui.viewmodels.UserPhotosFragmentViewModelFactory

class UserPhotosListFragment : AbstractListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        val user_id = args!!.getString(TAG_USER_ID)
        val factory = UserPhotosFragmentViewModelFactory(user_id!!)

        mViewModel = ViewModelProviders.of(this, factory).get<UserPhotosFragmentViewModelFactory.UserPhotosListFragmentViewModel>(UserPhotosFragmentViewModelFactory.UserPhotosListFragmentViewModel::class.java)
    }

    companion object {
        private val TAG_USER_ID = "user_id"

        fun newInstance(user_id: String): UserPhotosListFragment {
            val newFragment = UserPhotosListFragment()
            val args = Bundle()
            args.putString(TAG_USER_ID, user_id)
            newFragment.arguments = args
            return newFragment
        }
    }
}
