package com.commonpepper.photosen.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.commonpepper.photosen.network.NetworkState
import java.io.IOException
import java.util.concurrent.Executors

/**
 * PageKeyedDataSource that supports network state and retry last [call][.loadAfter]
 */
abstract class AbstractListDataSource<T> : PageKeyedDataSource<Int, T>() {
    var networkState = MutableLiveData<NetworkState>()
        protected set
    private var lastParams: LoadParams<Int>? = null
    private var lastCallback: LoadCallback<Int, T>? = null
    /**
     * Method will be used by [.loadInitial]
     */
    @Throws(IOException::class)
    abstract fun loadFirst(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>)

    /**
     * Method will be used by [.loadAfter]
     */
    @Throws(IOException::class)
    abstract fun loadNext(params: LoadParams<Int>, callback: LoadCallback<Int, T>)

    fun retryLast() {
        if (lastParams != null && lastCallback != null) {
            Executors.newSingleThreadExecutor().submit { loadAfter(lastParams!!, lastCallback!!) }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        networkState.postValue(NetworkState.RUNNING)
        try {
            loadFirst(params, callback)
        } catch (e: IOException) {
            networkState.postValue(NetworkState.FAILED)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {}
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        lastParams = params
        lastCallback = callback
        networkState.postValue(NetworkState.RUNNING)
        try {
            loadNext(params, callback)
        } catch (e: Exception) {
            networkState.postValue(NetworkState.FAILED)
        }
    }
}
