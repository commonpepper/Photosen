package com.commonpepper.photosen.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.DataSource.Factory
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.Config
import androidx.paging.PagedList.Config.Builder
import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.Photosen.Companion
import com.commonpepper.photosen.Photosen.Companion.instance
import com.commonpepper.photosen.model.Photo
import java.util.concurrent.Executors

class HistoryActivityViewModel : ViewModel() {
    private val dataSourceFactory: Factory<Int, Photo>?
    val photosList: LiveData<PagedList<Photo?>>

    init {
        val config = Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Photosen.PAGE_SIZE)
                .setPrefetchDistance(Photosen.PREFETCH_DISTANCE)
                .setInitialLoadSizeHint(Photosen.PAGE_SIZE)
                .build()
        dataSourceFactory = instance!!.database!!.historyDao.paged
        photosList = LivePagedListBuilder(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()
    }
}