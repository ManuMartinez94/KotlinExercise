package com.kotlinexercise.dto

import com.kotlinexercise.models.Photo

data class PhotoDTO(val title: String, val url: String) {
    companion object {
        fun Photo.toPhotoDTO() = PhotoDTO(title = title, url = url)

        fun Array<Photo>.toPhotoDTOList() = map {
            PhotoDTO(title = it.title, url = it.url)
        }
    }
}