package com.commonpepper.photosen.network.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public abstract class AbstractListDataSourceFactory<T> extends DataSource.Factory<Integer, T> {

    protected MutableLiveData<AbstractListDataSource<T>> liveDataSource = new MutableLiveData<>();

    public MutableLiveData<AbstractListDataSource<T>> getLiveDataSource() {
        return liveDataSource;
    }
}