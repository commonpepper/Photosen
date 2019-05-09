package com.commonpepper.photosen.ui.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.network.model.Comments;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsViewModelFactory implements ViewModelProvider.Factory {
    private static final String TAG = PhotoDetailsViewModelFactory.class.getSimpleName();

    private String photo_id;

    public CommentsViewModelFactory(String photo_id) {
        this.photo_id = photo_id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CommentsViewModelFactory.CommentsViewModel();
    }

    public class CommentsViewModel extends ViewModel {
        private MutableLiveData<Comments> comments = new MutableLiveData<>();
        private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
        public boolean hiden = true;

        public CommentsViewModel() {
        }

        public void loadComments() {
            networkState.postValue(NetworkState.RUNNING);

            Photosen.getFlickrApi().getComments(photo_id).enqueue(new Callback<Comments>() {
                @Override
                public void onResponse(Call<Comments> call, Response<Comments> response) {
                    if (response.isSuccessful() && response.code() == 200 && response.body() != null && response.body().getStat() != null && response.body().getStat().equals("ok")) {
                        comments.postValue(response.body());
                        networkState.postValue(NetworkState.SUCCESS);
                    } else {
                        networkState.postValue(NetworkState.FAILED);
                    }
                }

                @Override
                public void onFailure(Call<Comments> call, Throwable t) {
                    Log.d(TAG, t.toString());
                    networkState.postValue(NetworkState.FAILED);
                }
            });
        }

        public LiveData<Comments> getComments() {
            return comments;
        }

        public LiveData<NetworkState> getNetworkState() {
            return networkState;
        }
    }
}
