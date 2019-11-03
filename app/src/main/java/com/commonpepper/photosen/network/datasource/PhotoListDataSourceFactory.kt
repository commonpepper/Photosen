package com.commonpepper.photosen.network.datasource

import androidx.paging.PageKeyedDataSource
import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.network.NetworkState
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.model.SearchPhotos

import java.io.IOException
import retrofit2.Response

class PhotoListDataSourceFactory(private val date: String) : AbstractListDataSourceFactory<Photo>() {

    override fun create(): AbstractListDataSource<Photo> {
        val dataSource = object : AbstractListDataSource<Photo>() {
            @Throws(IOException::class)
            override fun loadFirst(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Photo>) {
                val response = Photosen.flickrApi.getRecentPhotos(date, 1, Photosen.PAGE_SIZE).execute()
                if (response.isSuccessful && response.code() == 200 && response.body() != null && response.body()!!.stat != null && response.body()!!.stat == "ok") {
                    callback.onResult(response.body()!!.photos.photo, null, 2)
                    networkState.postValue(NetworkState.SUCCESS)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

            @Throws(IOException::class)
            override fun loadNext(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
                val response = Photosen.flickrApi.getRecentPhotos(date, params.key, Photosen.PAGE_SIZE).execute()
                if (response.isSuccessful && response.code() == 200 && response.body() != null && response.body()!!.stat != null && response.body()!!.stat == "ok") {
                    callback.onResult(response.body()!!.photos.photo, params.key + 1)
                    networkState.postValue(NetworkState.SUCCESS)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }
        }
        liveDataSource.postValue(dataSource)
        return dataSource
    }
}