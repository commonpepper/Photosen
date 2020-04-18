package com.commonpepper.photosen.network.datasource

import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.Photosen.Companion.flickrApi
import com.commonpepper.photosen.model.Album
import com.commonpepper.photosen.model.AlbumsList
import com.commonpepper.photosen.network.NetworkState
import retrofit2.Response
import java.io.IOException

class AlbumsListDataSourceFactory(private val user_id: String) : AbstractListDataSourceFactory<Album?>() {
    override fun create(): AbstractListDataSource<Album?> {
        val dataSource: AbstractListDataSource<Album?> = object : AbstractListDataSource<Album?>() {
            @Throws(IOException::class)
            override fun loadFirst(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Album?>) {
                val response: Response<AlbumsList?> = flickrApi.getAlbums(user_id, 1, Photosen.PAGE_SIZE).execute()
                if (response.isSuccessful && response.code() == 200 && response.body()?.stat == "ok") {
                    callback.onResult(response.body()!!.photosets!!.photoset!!, null, 2)
                    networkState.postValue(NetworkState.SUCCESS)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

            @Throws(IOException::class)
            override fun loadNext(params: LoadParams<Int>, callback: LoadCallback<Int, Album?>) {
                val response: Response<AlbumsList?> = flickrApi.getAlbums(user_id, params.key, Photosen.PAGE_SIZE).execute()
                if (response.isSuccessful && response.code() == 200 && response.body()?.stat == "ok") {
                    callback.onResult(response.body()!!.photosets!!.photoset!!, params.key + 1)
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