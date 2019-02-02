package com.commonpepper.photosen.network.datasource;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.network.model.Photo;
import com.commonpepper.photosen.network.model.SearchPhotos;

import java.io.IOException;

import androidx.annotation.NonNull;
import retrofit2.Response;

public class SearchDataSourceFactory extends MyAbstractDataSourceFactory<Photo> {

    private String query;

    public SearchDataSourceFactory(String query) {
        this.query = query;
    }

    @NonNull
    @Override
    public MyAbstractDataSource<Photo> create() {
        MyAbstractDataSource<Photo> dataSource = new MyAbstractDataSource<Photo>() {
            @Override
            public void loadFirst(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Photo> callback) throws IOException {
                Response<SearchPhotos> response = Photosen.getUnsplashAPI().searchPhotos(query, 1, Photosen.PAGE_SIZE).execute();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    callback.onResult(response.body().getResults(), null, 2);
                    networkState.postValue(NetworkState.SUCCESS);
                } else {
                    networkState.postValue(NetworkState.FAILED);
                }
            }

            @Override
            public void loadNext(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) throws IOException {
                Response<SearchPhotos> response = Photosen.getUnsplashAPI().searchPhotos(query, params.key, Photosen.PAGE_SIZE).execute();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    callback.onResult(response.body().getResults(), params.key + 1);
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
