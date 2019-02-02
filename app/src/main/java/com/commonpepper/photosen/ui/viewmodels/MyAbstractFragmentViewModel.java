package com.commonpepper.photosen.ui.viewmodels;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.network.datasource.MyAbstractDataSource;
import com.commonpepper.photosen.network.datasource.MyAbstractDataSourceFactory;

import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public abstract class MyAbstractFragmentViewModel<T> extends ViewModel {
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<T>> photosList;

    public MyAbstractFragmentViewModel() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Photosen.PAGE_SIZE)
                .setPrefetchDistance(Photosen.PAGE_SIZE)
                .setInitialLoadSizeHint(Photosen.PAGE_SIZE)
                .build();

        MyAbstractDataSourceFactory<T> dataSourceFactory = createDataSourceFactory();
        networkState = Transformations.switchMap(dataSourceFactory.getLiveDataSource(), MyAbstractDataSource::getNetworkState);

        photosList = new LivePagedListBuilder<>(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();

    }

    public abstract MyAbstractDataSourceFactory<T> createDataSourceFactory();

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<T>> getPhotosList() {
        return photosList;
    }
}
