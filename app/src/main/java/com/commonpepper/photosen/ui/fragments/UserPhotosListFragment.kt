package com.commonpepper.photosen.ui.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.commonpepper.photosen.ui.viewmodels.UserPhotosFragmentViewModelFactory
import com.commonpepper.photosen.ui.viewmodels.UserPhotosFragmentViewModelFactory.UserPhotosListFragmentViewModel

class UserPhotosListFragment : AbstractListFragment() {
    companion object {
        private const val TAG_USER_ID = "user_id"
        fun newInstance(user_id: String?): UserPhotosListFragment {
            val newFragment = UserPhotosListFragment()
            val args = Bundle()
            args.putString(TAG_USER_ID, user_id)
            newFragment.arguments = args
            return newFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        val user_id = args!!.getString(TAG_USER_ID) ?: ""
        val factory = UserPhotosFragmentViewModelFactory(user_id)
        mViewModel = ViewModelProviders.of(this, factory).get(UserPhotosListFragmentViewModel::class.java)
    }
}