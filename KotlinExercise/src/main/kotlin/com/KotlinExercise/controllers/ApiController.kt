package com.kotlinexercise.controllers

import com.kotlinexercise.services.AlbumInfo
import com.kotlinexercise.services.AlbumService
import com.kotlinexercise.services.Photo
import com.kotlinexercise.services.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class ApiController(private val albumService: AlbumService, private val tokenService: TokenService) {

    @GetMapping("/token")
    fun getToken(): ResponseEntity<String> {
        val token = tokenService.getAndGenerateToken()
        return ResponseEntity(token, HttpStatus.OK)
    }

    @GetMapping("/albums")
    fun getAllAlbums(): ResponseEntity<List<AlbumInfo>> {
        val albums = albumService.getAllAlbumsInfo()
        return ResponseEntity(albums, HttpStatus.OK)
    }

    @GetMapping("/albums/{id}")
    fun getAlbum(@PathVariable id: Int): ResponseEntity<AlbumInfo> {
        val album = albumService.getAlbumInfo(id)
        return if(album != null){
            ResponseEntity(album, HttpStatus.OK)
        }else{
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("/albums/{id}/photos")
    fun getPhotos(@PathVariable id: Int): ResponseEntity<List<Photo>> {
        val photos = albumService.getPhotos(id)
        return if(photos.isNullOrEmpty()){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }else{
            ResponseEntity(photos, HttpStatus.OK)
        }
    }

}