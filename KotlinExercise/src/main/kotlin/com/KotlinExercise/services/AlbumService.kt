package com.kotlinexercise.services

import com.kotlinexercise.models.AlbumInfo
import com.kotlinexercise.models.Photo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
class AlbumService(@Autowired private val rest: RestTemplate) {

    fun getAllAlbumsInfo(): List<AlbumInfo> {
        return rest.getForObject<Array<AlbumInfo>>("https://jsonplaceholder.typicode.com/albums").toList()
    }

    fun getAlbumInfo(albumId: Int): AlbumInfo? {
        return try {
            rest.getForObject("https://jsonplaceholder.typicode.com/albums/$albumId")
        } catch (e: HttpClientErrorException){
            null
        }
    }

    fun getPhotos(albumId: Int): List<Photo>? {
        return try {
            rest.getForObject<Array<Photo>>("https://jsonplaceholder.typicode.com/albums/$albumId/photos").toList()
        } catch (e: HttpClientErrorException){
            null
        }
    }
}