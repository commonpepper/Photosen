package com.commonpepper.photosen.ui.viewmodels;

import android.util.Log;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.model.PhotoDetails;
import com.commonpepper.photosen.model.PhotoSizes;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailsViewModelFactory implements ViewModelProvider.Factory {
    private static final String TAG = PhotoDetailsViewModelFactory.class.getSimpleName();

    private String photo_id;
    private String secret;

    public PhotoDetailsViewModelFactory(String photo_id, String secret) {
        this.photo_id = photo_id;
        this.secret = secret;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotoDetailsViewModelFactory.PhotoDetailsViewModel();
    }

    public class PhotoDetailsViewModel extends ViewModel {
        private MutableLiveData<PhotoDetails> photoDetails = new MutableLiveData<>();
        private MutableLiveData<PhotoSizes> photoSizes = new MutableLiveData<>();
        private MutableLiveData<NetworkState> networkStateDetails = new MutableLiveData<>();
        private MutableLiveData<NetworkState> networkStateSizes = new MutableLiveData<>();
        private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

        private Observer<NetworkState> observerDetails = x -> {
            if (x == NetworkState.SUCCESS && networkStateSizes.getValue() == NetworkState.SUCCESS) {
                networkState.postValue(NetworkState.SUCCESS);
            } else if (x == NetworkState.FAILED) {
                networkState.postValue(NetworkState.FAILED);
            }
        };

        private Observer<NetworkState> observerSizes = x -> {
            if (x == NetworkState.SUCCESS && networkStateDetails.getValue() == NetworkState.SUCCESS) {
                networkState.postValue(NetworkState.SUCCESS);
            } else if (x == NetworkState.FAILED) {
                networkState.postValue(NetworkState.FAILED);
            }
        };

        public PhotoDetailsViewModel() {
            loadDetails();
        }

        public void loadDetails() {
            networkStateDetails.postValue(NetworkState.RUNNING);
            networkStateSizes.postValue(NetworkState.RUNNING);
            networkState.postValue(NetworkState.RUNNING);

            networkStateDetails.observeForever(observerDetails);
            networkStateSizes.observeForever(observerSizes);

            Photosen.getFlickrApi().getPhotoInfo(photo_id, secret).enqueue(new Callback<PhotoDetails>() {
                @Override
                public void onResponse(Call<PhotoDetails> call, Response<PhotoDetails> response) {
                    if (response.isSuccessful() && response.code() == 200 && response.body() != null && response.body().getStat() != null && response.body().getStat().equals("ok")) {
                        photoDetails.postValue(response.body());
                        networkStateDetails.postValue(NetworkState.SUCCESS);
                    } else {
                        networkStateDetails.postValue(NetworkState.FAILED);
                    }
                }

                @Override
                public void onFailure(Call<PhotoDetails> call, Throwable t) {
                    Log.d(TAG, t.toString());
                    networkStateDetails.postValue(NetworkState.FAILED);
                }
            });

            Photosen.getFlickrApi().getPhotoSizes(photo_id).enqueue(new Callback<PhotoSizes>() {
                @Override
                public void onResponse(Call<PhotoSizes> call, Response<PhotoSizes> response) {
                    if (response.isSuccessful() && response.code() == 200 && response.body() != null && response.body().getStat() != null && response.body().getStat().equals("ok")) {
                        photoSizes.postValue(response.body());
                        networkStateSizes.postValue(NetworkState.SUCCESS);
                    } else {
                        networkStateSizes.postValue(NetworkState.FAILED);
                    }
                }

                @Override
                public void onFailure(Call<PhotoSizes> call, Throwable t) {
                    Log.d(TAG, t.toString());
                    networkStateSizes.postValue(NetworkState.FAILED);
                }
            });
        }

        @Override
        protected void onCleared() {
            networkStateDetails.removeObserver(observerDetails);
            networkStateSizes.removeObserver(observerSizes);
        }

        public LiveData<PhotoDetails> getPhotoDetails() {
            return photoDetails;
        }

        public LiveData<NetworkState> getNetworkState() {
            return networkState;
        }

        public LiveData<PhotoSizes> getPhotoSizes() {
            return photoSizes;
        }
    }
}
