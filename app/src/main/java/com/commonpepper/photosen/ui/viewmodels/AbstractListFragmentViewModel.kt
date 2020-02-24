package com.commonpepper.photosen.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.Config.Builder
import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.network.NetworkState
import com.commonpepper.photosen.network.datasource.AbstractListDataSource
import com.commonpepper.photosen.network.datasource.AbstractListDataSourceFactory
import java.util.concurrent.Executors

abstract class AbstractListFragmentViewModel<T> : ViewModel() {
    val networkState: LiveData<NetworkState>
    val photosList: LiveData<PagedList<T>>
    abstract fun createDataSourceFactory(): AbstractListDataSourceFactory<T>

    init {
        val config = Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Photosen.PAGE_SIZE)
                .setPrefetchDistance(Photosen.PREFETCH_DISTANCE)
                .setInitialLoadSizeHint(Photosen.PAGE_SIZE)
                .build()
        val dataSourceFactory = createDataSourceFactory()
        networkState = Transformations.switchMap(dataSourceFactory.liveDataSource) { obj -> obj.networkState }
        photosList = LivePagedListBuilder(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()
    }
}