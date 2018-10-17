package com.dog.samurai.pyramid.model

data class FlickrData(
        val photos: Photos
)

data class Photos(
        val photo: List<PhotoData>
)

data class PhotoData(
        val url_h: String?,
        val height_h: Int?,
        val width_h: Int?
)
