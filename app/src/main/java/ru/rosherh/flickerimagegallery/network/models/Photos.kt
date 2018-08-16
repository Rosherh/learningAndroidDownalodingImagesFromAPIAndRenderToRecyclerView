package ru.rosherh.flickerimagegallery.network.models

import com.google.gson.annotations.SerializedName

data class Photos(
        val photos: InnerPhotos
) {
    data class InnerPhotos(
            val page: Int,
            val pages: Int,
            @SerializedName("perpage") val perPage: Int,
            val total: Int,
            val photo: List<Photo>
    )
}