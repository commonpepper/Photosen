package com.commonpepper.photosen.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource.Factory

abstract class AbstractListDataSourceFactory<T> : Factory<Int?, T>() {
    var liveDataSource = MutableLiveData<AbstractListDataSource<T>>()
}
