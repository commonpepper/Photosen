package com.commonpepper.photosen.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.network.datasource.SearchListDataSourceFactory

class SearchFragmentViewModelFactory(private val query: String, private val tags: String, private val sort: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchListFragmentViewModel() as T
    }

    inner class SearchListFragmentViewModel : AbstractListFragmentViewModel<Photo>() {

        override fun createDataSourceFactory(): SearchListDataSourceFactory {
            return SearchListDataSourceFactory(query, tags, sort)
        }
    }

}
