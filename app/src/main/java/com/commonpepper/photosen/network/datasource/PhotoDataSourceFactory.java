package com.commonpepper.photosen.network.datasource;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.network.model.Photo;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Response;

public class PhotoDataSourceFactory extends MyAbstractDataSourceFactory<Photo> {

    private String order_by;

    public PhotoDataSourceFactory(String order_by) {
        this.order_by = order_by;
    }

    @NonNull
    @Override
    public MyAbstractDataSource<Photo> create() {
        MyAbstractDataSource<Photo> dataSource = new MyAbstractDataSource<Photo>() {
            @Override
            public void loadFirst(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Photo> callback) throws IOException {
                Response<List<Photo>> response = Photosen.getUnsplashAPI().getPhotos(1, Photosen.PAGE_SIZE, order_by).execute();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    callback.onResult(response.body(), null, 2);
                    networkState.postValue(NetworkState.SUCCESS);
                } else {
                    networkState.postValue(NetworkState.FAILED);
                }
            }

            @Override
            public void loadNext(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) throws IOException {
                Response<List<Photo>> response = Photosen.getUnsplashAPI().getPhotos(params.key, Photosen.PAGE_SIZE, order_by).execute();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    callback.onResult(response.body(), params.key + 1);
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