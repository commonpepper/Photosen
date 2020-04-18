package com.commonpepper.photosen.model

data class AlbumsList(
    val photosets: Photosets?,
    val stat: String?
) {
    data class Photosets(
        val photoset: List<Album?>?
    )
}