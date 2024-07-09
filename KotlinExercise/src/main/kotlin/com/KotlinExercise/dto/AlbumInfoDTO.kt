package com.kotlinexercise.dto

import com.kotlinexercise.models.AlbumInfo

data class AlbumInfoDTO(val userId: Int, val title: String) {

    companion object {

        fun AlbumInfo.toAlbumInfoDTO() = AlbumInfoDTO(userId, title)

        fun Array<AlbumInfo>.toAlbumInfoDTOList() = map {
            it.toAlbumInfoDTO()
        }
    }
}