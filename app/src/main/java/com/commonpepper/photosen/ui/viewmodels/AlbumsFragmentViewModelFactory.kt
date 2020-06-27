package com.commonpepper.photosen.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.commonpepper.photosen.model.Album
import com.commonpepper.photosen.network.datasource.AlbumsListDataSourceFactory

class AlbumsFragmentViewModelFactory(private val user_id: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumsFragmentViewModel() as T
    }

    inner class AlbumsFragmentViewModel : AbstractListFragmentViewModel<Album?>() {
        override fun createDataSourceFactory(): AlbumsListDataSourceFactory {
            return AlbumsListDataSourceFactory(user_id)
        }
    }

}