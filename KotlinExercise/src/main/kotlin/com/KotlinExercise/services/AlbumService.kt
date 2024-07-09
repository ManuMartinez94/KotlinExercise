package com.kotlinexercise.services

import com.kotlinexercise.dto.AlbumDTO
import com.kotlinexercise.dto.AlbumInfoDTO
import com.kotlinexercise.dto.AlbumInfoDTO.Companion.toAlbumInfoDTO
import com.kotlinexercise.dto.AlbumInfoDTO.Companion.toAlbumInfoDTOList
import com.kotlinexercise.dto.PhotoDTO
import com.kotlinexercise.dto.PhotoDTO.Companion.toPhotoDTOList
import com.kotlinexercise.models.AlbumInfo
import com.kotlinexercise.models.Photo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@Service
class AlbumService(@Autowired private val rest: RestTemplate) {

    fun getAllAlbums(): List<AlbumDTO> {
        val albums = rest.getForObject<Array<AlbumInfo>>("https://jsonplaceholder.typicode.com/albums").toList()

        val executors = Executors.newFixedThreadPool(10)

        val response = albums.map {album ->
            CompletableFuture.supplyAsync({
                val photos = getPhotos(album.id) ?: emptyList()
                AlbumDTO(album.title, photos)
            }, executors)
        }.map { it.get() }

        executors.shutdown()

        return response
    }

    fun getAllAlbumsInfo(): List<AlbumInfoDTO> {
        return rest.getForObject<Array<AlbumInfo>>("https://jsonplaceholder.typicode.com/albums").toAlbumInfoDTOList()
    }

    fun getAlbumInfo(albumId: Int): AlbumInfoDTO? {
        return try {
            rest.getForObject<AlbumInfo>("https://jsonplaceholder.typicode.com/albums/$albumId").toAlbumInfoDTO()
        } catch (e: HttpClientErrorException){
            null
        }
    }

    fun getAlbum(albumId: Int): AlbumDTO? {
        val albumInfo = getAlbumInfo(albumId) ?: return null
        val photos = getPhotos(albumId) ?: emptyList()
        return AlbumDTO( albumInfo.title, photos)
    }

    fun getPhotos(albumId: Int): List<PhotoDTO>? {
        return try {
            rest.getForObject<Array<Photo>>("https://jsonplaceholder.typicode.com/albums/$albumId/photos").toPhotoDTOList()
        } catch (e: HttpClientErrorException){
            null
        }
    }
}