package com.commonpepper.photosen.network;

import com.commonpepper.photosen.network.model.Photo;
import com.commonpepper.photosen.network.model.SearchPhotos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashAPI {

    @GET("photos")
    Call<List<Photo>> getPhotos(@Query("page") int page,
                                @Query("per_page") int per_page,
                                @Query("order_by") String order_by);

    @GET("search/photos")
    Call<SearchPhotos> searchPhotos(@Query("query") String query,
                                                  @Query("page") int page,
                                                  @Query("per_page") int per_page);
}
