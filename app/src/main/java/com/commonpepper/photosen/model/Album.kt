package com.commonpepper.photosen.model

data class Album(
        val can_comment: Int?,
        val count_comments: Int?,
        val count_photos: String?,
        val count_videos: Int?,
        val count_views: String?,
        val date_create: String?,
        val date_update: String?,
        val description: Description?,
        val farm: Int?,
        val id: String?,
        val needs_interstitial: Int?,
        val owner: String?,
        val photos: String?,
        val primary: String?,
        val primary_photo_extras: PrimaryPhotoExtras?,
        val secret: String?,
        val server: String?,
        val title: Title?,
        val username: String?,
        val videos: Int?,
        val visibility_can_see_set: Int?
) {
    data class Description(
            val _content: String?
    )

    data class PrimaryPhotoExtras(
            val height_z: String?,
            val url_z: String?,
            val width_z: String?
    )

    data class Title(
            val _content: String?
    )
}