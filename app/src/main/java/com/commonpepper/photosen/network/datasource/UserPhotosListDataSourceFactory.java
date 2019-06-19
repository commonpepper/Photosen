package com.commonpepper.photosen.network.datasource;

import androidx.annotation.NonNull;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.model.Photo;
import com.commonpepper.photosen.model.SearchPhotos;

import java.io.IOException;

import retrofit2.Response;

public class UserPhotosListDataSourceFactory extends AbstractListDataSourceFactory<Photo> {

    private String user_id;

    public UserPhotosListDataSourceFactory(String user_id) {
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public AbstractListDataSource<Photo> create() {
        AbstractListDataSource<Photo> dataSource = new AbstractListDataSource<Photo>() {
            @Override
            public void loadFirst(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Photo> callback) throws IOException {
                Response<SearchPhotos> response = Photosen.getFlickrApi().getUserPhotos(user_id, 1, Photosen.PAGE_SIZE).execute();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null && response.body().getStat() != null && response.body().getStat().equals("ok")) {
                    callback.onResult(response.body().getPhotos().getPhoto(), null, 2);
                    networkState.postValue(NetworkState.SUCCESS);
                } else {
                    networkState.postValue(NetworkState.FAILED);
                }
            }

            @Override
            public void loadNext(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) throws IOException {
                Response<SearchPhotos> response = Photosen.getFlickrApi().getUserPhotos(user_id, params.key, Photosen.PAGE_SIZE).execute();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null && response.body().getStat() != null && response.body().getStat().equals("ok")) {
                    callback.onResult(response.body().getPhotos().getPhoto(), params.key + 1);
                    networkState.postValue(NetworkState.SUCCESS);
                } else {
                    networkState.postValue(NetworkState.FAILED);
                }
            }
        };
        liveDataSource.postValue(dataSource);
        return dataSource;
    }
}
