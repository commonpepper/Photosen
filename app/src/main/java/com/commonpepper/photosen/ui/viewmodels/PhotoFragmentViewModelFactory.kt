package com.commonpepper.photosen.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.network.datasource.PhotoListDataSourceFactory

class PhotoFragmentViewModelFactory(private val date: String) : Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotoListFragmentViewModel() as T
    }

    inner class PhotoListFragmentViewModel : AbstractListFragmentViewModel<Photo?>() {
        override fun createDataSourceFactory(): PhotoListDataSourceFactory {
            return PhotoListDataSourceFactory(date)
        }
    }

}