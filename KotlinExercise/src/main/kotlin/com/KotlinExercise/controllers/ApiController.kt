package com.kotlinexercise.controllers

import com.kotlinexercise.services.AlbumInfo
import com.kotlinexercise.services.AlbumService
import com.kotlinexercise.services.Photo
import com.kotlinexercise.services.TokenService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
@Tag(name = "Albums and photos api", description = "This api manages albums and photos with authentication via the Authorization token header.")
class ApiController(private val albumService: AlbumService, private val tokenService: TokenService) {

    @Operation(summary = "Get a token with 20 minutes expiry. This token will need to be added in the header to make requests to the api.")
    @GetMapping("/token")
    fun getToken(): ResponseEntity<String> {
        val token = tokenService.getAndGenerateToken()
        return ResponseEntity(token, HttpStatus.OK)
    }

    @Operation(summary = "Get all albums")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Albums found"),
            ApiResponse(responseCode = "401", description = "Authentication token not provided in header or invalid", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Albums not found", content = [Content()])
        ]
    )
    @GetMapping("/albums")
    fun getAllAlbums(
        @RequestHeader(value = "Authorization", required = true) token: String
    ): ResponseEntity<List<AlbumInfo>> {
        val albums = albumService.getAllAlbumsInfo()
        return ResponseEntity(albums, HttpStatus.OK)
    }

    @Operation(summary = "Get album by albumId")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Album found"),
            ApiResponse(responseCode = "401", description = "Authentication token not provided in header or invalid", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Album not found", content = [Content()])
        ]
    )
    @GetMapping("/albums/{id}")
    fun getAlbum(
        @Parameter(description = "Album id", example = "1", required = true)
        @PathVariable id: Int,
        @RequestHeader(value = "Authorization", required = true) token: String
    ): ResponseEntity<AlbumInfo> {
        val album = albumService.getAlbumInfo(id)
        return if (album != null) {
            ResponseEntity(album, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @Operation(summary = "Get photos by albumId")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Photos found"),
            ApiResponse(responseCode = "401", description = "Authentication token not provided in header or invalid", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Photos not found", content = [Content()])
        ]
    )
    @GetMapping("/albums/{id}/photos")
    fun getPhotos(
        @Parameter(description = "Id of the photo album", example = "1", required = true)
        @PathVariable id: Int,
        @RequestHeader(value = "Authorization", required = true) token: String
    ): ResponseEntity<List<Photo>> {
        val photos = albumService.getPhotos(id)
        return if(photos.isNullOrEmpty()){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }else{
            ResponseEntity(photos, HttpStatus.OK)
        }
    }
}