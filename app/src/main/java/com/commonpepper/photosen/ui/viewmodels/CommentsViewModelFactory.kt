package com.commonpepper.photosen.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.commonpepper.photosen.Photosen.Companion.flickrApi
import com.commonpepper.photosen.model.Comments
import com.commonpepper.photosen.network.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsViewModelFactory(private val photo_id: String) : Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentsViewModel() as T
    }

    inner class CommentsViewModel : ViewModel() {
        private val comments = MutableLiveData<Comments?>()
        private val networkState = MutableLiveData<NetworkState>()
        var hiden = true
        fun loadComments() {
            networkState.postValue(NetworkState.RUNNING)
            flickrApi.getComments(photo_id).enqueue(object : Callback<Comments?> {
                override fun onResponse(call: Call<Comments?>?, response: Response<Comments?>) {
                    if (response.isSuccessful && response.code() == 200 && response.body()?.stat == "ok") {
                        comments.postValue(response.body())
                        networkState.postValue(NetworkState.SUCCESS)
                    } else {
                        networkState.postValue(NetworkState.FAILED)
                    }
                }

                override fun onFailure(call: Call<Comments?>?, t: Throwable) {
                    Log.d(TAG, t.toString())
                    networkState.postValue(NetworkState.FAILED)
                }
            })
        }

        fun getComments(): LiveData<Comments?> {
            return comments
        }

        fun getNetworkState(): LiveData<NetworkState> {
            return networkState
        }
    }

    companion object {
        private val TAG = PhotoDetailsViewModelFactory::class.java.simpleName
    }

}