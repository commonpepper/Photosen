package com.commonpepper.photosen.network

import com.commonpepper.photosen.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("services/rest/?method=flickr.interestingness.getList" +
            "&extras=date_taken,url_o,url_z,original_format,icon_server,owner_name,o_dims,tags")
    fun getRecentPhotos(@Query("date") date: String?,
                        @Query("page") page: Int,
                        @Query("per_page") per_page: Int): Call<SearchPhotos?>

    @GET("services/rest/?method=flickr.photos.search" +
            "&extras=date_taken,url_o,url_z,original_format,icon_server,owner_name,o_dims,tags" +
            "&tag_mode=all")
    fun searchPhotos(@Query("text") text: String?,
                     @Query("tags") tags: String?,
                     @Query("sort") sort: String?,
                     @Query("page") page: Int,
                     @Query("per_page") per_page: Int): Call<SearchPhotos>

    @GET("services/rest/?method=flickr.photos.getInfo")
    fun getPhotoInfo(@Query("photo_id") photo_id: String?,
                     @Query("secret") secret: String?): Call<PhotoDetails>

    @GET("services/rest/?method=flickr.photos.getSizes")
    fun getPhotoSizes(@Query("photo_id") photo_id: String?): Call<PhotoSizes>

    @GET("services/rest/?method=flickr.people.getPublicPhotos" +
            "&extras=date_taken,url_o,url_z,original_format,icon_server,owner_name,o_dims,tags")
    fun getUserPhotos(@Query("user_id") user_id: String?,
                      @Query("page") page: Int,
                      @Query("per_page") per_page: Int): Call<SearchPhotos>

    @GET("services/rest/?method=flickr.photos.comments.getList")
    fun getComments(@Query("photo_id") photo_id: String?): Call<Comments>

    @GET("services/rest/?method=flickr.photosets.getList" +
            "&primary_photo_extras=url_z")
    fun getAlbums(@Query("user_id") user_id: String?,
                  @Query("page") page: Int,
                  @Query("per_page") per_page: Int): Call<AlbumsList>
}