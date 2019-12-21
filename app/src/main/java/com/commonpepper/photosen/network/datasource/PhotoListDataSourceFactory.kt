package com.commonpepper.photosen.network.datasource

import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.Photosen.Companion.flickrApi
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.model.SearchPhotos
import com.commonpepper.photosen.network.NetworkState
import retrofit2.Response
import java.io.IOException

class PhotoListDataSourceFactory(private val date: String) : AbstractListDataSourceFactory<Photo?>() {
    override fun create(): AbstractListDataSource<Photo?> {
        val dataSource: AbstractListDataSource<Photo?> = object : AbstractListDataSource<Photo?>() {
            @Throws(IOException::class)
            override fun loadFirst(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Photo?>) {
                val response: Response<SearchPhotos?> = flickrApi.getRecentPhotos(date, 1, Photosen.PAGE_SIZE).execute()
                if (response.isSuccessful && response.code() == 200 && response.body()?.stat == "ok") {
                    callback.onResult(response.body()!!.photos!!.photo!!, null, 2)
                    networkState.postValue(NetworkState.SUCCESS)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

            @Throws(IOException::class)
            override fun loadNext(params: LoadParams<Int>, callback: LoadCallback<Int, Photo?>) {
                val response: Response<SearchPhotos?> = flickrApi.getRecentPhotos(date, params.key, Photosen.PAGE_SIZE).execute()
                if (response.isSuccessful && response.code() == 200 && response.body()?.stat == "ok") {
                    callback.onResult(response.body()!!.photos!!.photo!!, params.key + 1)
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
