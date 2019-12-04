package com.commonpepper.photosen.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.network.datasource.UserPhotosListDataSourceFactory

class UserPhotosFragmentViewModelFactory(private val user_id: String) : Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserPhotosListFragmentViewModel() as T
    }

    inner class UserPhotosListFragmentViewModel : AbstractListFragmentViewModel<Photo?>() {
        override fun createDataSourceFactory(): UserPhotosListDataSourceFactory {
            return UserPhotosListDataSourceFactory(user_id)
        }
    }

}