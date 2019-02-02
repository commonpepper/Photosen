package com.commonpepper.photosen.network.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public abstract class MyAbstractDataSourceFactory<T> extends DataSource.Factory<Integer, T> {

    protected MutableLiveData<MyAbstractDataSource<T>> liveDataSource = new MutableLiveData<>();

    public MutableLiveData<MyAbstractDataSource<T>> getLiveDataSource() {
        return liveDataSource;
    }
}