package com.commonpepper.photosen.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract class AbstractListDataSourceFactory<T> : DataSource.Factory<Int, T>() {

    var liveDataSource = MutableLiveData<AbstractListDataSource<T>>()
        protected set
}