package com.commonpepper.photosen.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.model.PhotoDetails
import com.commonpepper.photosen.model.PhotoSizes
import com.commonpepper.photosen.network.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoDetailsViewModelFactory(private val photo_id: String, private val secret: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhotoDetailsViewModel() as T
    }

    inner class PhotoDetailsViewModel : ViewModel() {
        private val photoDetails = MutableLiveData<PhotoDetails>()
        private val photoSizes = MutableLiveData<PhotoSizes>()
        private val networkStateDetails = MutableLiveData<NetworkState>()
        private val networkStateSizes = MutableLiveData<NetworkState>()
        private val networkState = MutableLiveData<NetworkState>()

        private val observerDetails = { x : NetworkState ->
            if (x == NetworkState.SUCCESS && networkStateSizes.value == NetworkState.SUCCESS) {
                networkState.postValue(NetworkState.SUCCESS)
            } else if (x == NetworkState.FAILED) {
                networkState.postValue(NetworkState.FAILED)
            }
        }

        private val observerSizes = { x : NetworkState ->
            if (x == NetworkState.SUCCESS && networkStateDetails.value == NetworkState.SUCCESS) {
                networkState.postValue(NetworkState.SUCCESS)
            } else if (x == NetworkState.FAILED) {
                networkState.postValue(NetworkState.FAILED)
            }
        }

        init {
            loadDetails()
        }

        fun loadDetails() {
            networkStateDetails.postValue(NetworkState.RUNNING)
            networkStateSizes.postValue(NetworkState.RUNNING)
            networkState.postValue(NetworkState.RUNNING)

            networkStateDetails.observeForever(observerDetails)
            networkStateSizes.observeForever(observerSizes)

            Photosen.flickrApi.getPhotoInfo(photo_id, secret).enqueue(object : Callback<PhotoDetails> {
                override fun onResponse(call: Call<PhotoDetails>, response: Response<PhotoDetails>) {
                    if (response.isSuccessful && response.code() == 200 && response.body() != null && response.body()!!.stat != null && response.body()!!.stat == "ok") {
                        photoDetails.postValue(response.body())
                        networkStateDetails.postValue(NetworkState.SUCCESS)
                    } else {
                        networkStateDetails.postValue(NetworkState.FAILED)
                    }
                }

                override fun onFailure(call: Call<PhotoDetails>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    networkStateDetails.postValue(NetworkState.FAILED)
                }
            })

            Photosen.flickrApi.getPhotoSizes(photo_id).enqueue(object : Callback<PhotoSizes> {
                override fun onResponse(call: Call<PhotoSizes>, response: Response<PhotoSizes>) {
                    if (response.isSuccessful && response.code() == 200 && response.body() != null && response.body()!!.stat != null && response.body()!!.stat == "ok") {
                        photoSizes.postValue(response.body())
                        networkStateSizes.postValue(NetworkState.SUCCESS)
                    } else {
                        networkStateSizes.postValue(NetworkState.FAILED)
                    }
                }

                override fun onFailure(call: Call<PhotoSizes>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    networkStateSizes.postValue(NetworkState.FAILED)
                }
            })
        }

        override fun onCleared() {
            networkStateDetails.removeObserver(observerDetails)
            networkStateSizes.removeObserver(observerSizes)
        }

        fun getPhotoDetails(): LiveData<PhotoDetails> {
            return photoDetails
        }

        fun getNetworkState(): LiveData<NetworkState> {
            return networkState
        }

        fun getPhotoSizes(): LiveData<PhotoSizes> {
            return photoSizes
        }
    }

    companion object {
        private val TAG = PhotoDetailsViewModelFactory::class.java.simpleName
    }
}
