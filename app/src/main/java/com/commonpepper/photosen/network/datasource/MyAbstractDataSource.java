package com.commonpepper.photosen.network.datasource;

import com.commonpepper.photosen.network.NetworkState;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Response;

/**
 * PageKeyedDataSource that supports network state and retry last {@link #loadAfter(LoadParams, LoadCallback) call
 *
 * @param <T> Type of items being loaded by the DataSource.
 */
public abstract class MyAbstractDataSource<T> extends PageKeyedDataSource<Integer, T> {
    protected MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    private PageKeyedDataSource.LoadParams<Integer> lastParams;
    private PageKeyedDataSource.LoadCallback<Integer, T> lastCallback;

    /**
     * Method will be used by {@link #loadInitial(LoadInitialParams, LoadInitialCallback)}
     */
    public abstract void loadFirst(@NonNull PageKeyedDataSource.LoadInitialParams<Integer> params, @NonNull PageKeyedDataSource.LoadInitialCallback<Integer, T> callback) throws IOException;

    /**
     * Method will be used by {@link #loadAfter(LoadParams, LoadCallback)
     */
    public abstract void loadNext(@NonNull PageKeyedDataSource.LoadParams<Integer> params, @NonNull PageKeyedDataSource.LoadCallback<Integer, T> callback) throws IOException;

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public void retryLast() {
        if (lastParams != null && lastCallback != null) {
            Executors.newSingleThreadExecutor().submit(
                    () -> loadAfter(lastParams, lastCallback)
            );
        }
    }

    @Override
    public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Integer> params, @NonNull PageKeyedDataSource.LoadInitialCallback<Integer, T> callback) {
        networkState.postValue(NetworkState.RUNNING);

        try {
            loadFirst(params, callback);
        } catch (IOException e) {
            networkState.postValue(NetworkState.FAILED);
        }
    }

    @Override
    public void loadBefore(@NonNull PageKeyedDataSource.LoadParams<Integer> params, @NonNull PageKeyedDataSource.LoadCallback<Integer, T> callback) {
    }

    @Override
    public void loadAfter(@NonNull PageKeyedDataSource.LoadParams<Integer> params, @NonNull PageKeyedDataSource.LoadCallback<Integer, T> callback) {
        lastParams = params;
        lastCallback = callback;

        networkState.postValue(NetworkState.RUNNING);

        try {
            loadNext(params, callback);
        } catch (Exception e) {
            networkState.postValue(NetworkState.FAILED);
        }
    }
}
