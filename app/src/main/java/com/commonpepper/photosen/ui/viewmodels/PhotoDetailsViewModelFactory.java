package com.commonpepper.photosen.ui.viewmodels;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.network.model.PhotoDetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailsViewModelFactory implements ViewModelProvider.Factory {

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
        private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

        public PhotoDetailsViewModel() {
            loadDetails();
        }

        public void loadDetails() {
            networkState.postValue(NetworkState.RUNNING);
            Photosen.getFlickrApi().getPhotoInfo(photo_id, secret).enqueue(new Callback<PhotoDetails>() {
                @Override
                public void onResponse(Call<PhotoDetails> call, Response<PhotoDetails> response) {
                    if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                        photoDetails.postValue(response.body());
                        networkState.postValue(NetworkState.SUCCESS);
                    } else {
                        networkState.postValue(NetworkState.FAILED);
                    }
                }

                @Override
                public void onFailure(Call<PhotoDetails> call, Throwable t) {
                    networkState.postValue(NetworkState.FAILED);
                }
            });
        }

        public LiveData<PhotoDetails> getPhotoDetails() {
            return photoDetails;
        }

        public LiveData<NetworkState> getNetworkState() {
            return networkState;
        }
    }
}
