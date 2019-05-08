package com.commonpepper.photosen.network;

import com.commonpepper.photosen.network.model.Comments;
import com.commonpepper.photosen.network.model.PhotoDetails;
import com.commonpepper.photosen.network.model.PhotoSizes;
import com.commonpepper.photosen.network.model.SearchPhotos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {

    @GET("services/rest/?method=flickr.interestingness.getList" +
            "&extras=date_taken,url_o,url_z,original_format,icon_server,owner_name,o_dims,tags")
    Call<SearchPhotos> getRecentPhotos(@Query("date") String date,
                                       @Query("page") int page,
                                       @Query("per_page") int per_page);

    @GET("services/rest/?method=flickr.photos.search" +
            "&extras=date_taken,url_o,url_z,original_format,icon_server,owner_name,o_dims,tags" +
            "&tag_mode=all")
    Call<SearchPhotos> searchPhotos(@Query("text") String text,
                                    @Query("tags") String tags,
                                    @Query("sort") String sort,
                                    @Query("page") int page,
                                    @Query("per_page") int per_page);

    @GET("services/rest/?method=flickr.photos.getInfo")
    Call<PhotoDetails> getPhotoInfo(@Query("photo_id") String photo_id,
                                    @Query("secret") String secret);

    @GET("services/rest/?method=flickr.photos.getSizes")
    Call<PhotoSizes> getPhotoSizes(@Query("photo_id") String photo_id);

    @GET("services/rest/?method=flickr.people.getPublicPhotos" +
            "&extras=date_taken,url_o,url_z,original_format,icon_server,owner_name,o_dims,tags")
    Call<SearchPhotos> getUserPhotos(@Query("user_id") String user_id,
                                     @Query("page") int page,
                                     @Query("per_page") int per_page);

    @GET("services/rest/?method=flickr.photos.comments.getList")
    Call<Comments> getComments(@Query("photo_id") String photo_id);
}
