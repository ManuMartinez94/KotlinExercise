package com.kotlinexercise

import com.kotlinexercise.models.AlbumInfo
import com.kotlinexercise.models.Photo
import com.kotlinexercise.services.AlbumService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AlbumServiceTest {

    @Mock
    private lateinit var restTemplate: RestTemplate

    @InjectMocks
    private lateinit var albumService: AlbumService

    private val album1 = AlbumInfo(12,1,"Album 1")
    private val album2 = AlbumInfo(24,2,"Album 2")
    private val album3 = AlbumInfo(53,3,"Album 3")
    private val photo1 = Photo(1,"First Photo","https://photo.com/", "https://photo.com/image1.png")
    private val photo2 = Photo(2,"Second Photo","https://photo.com/", "https://photo.com/image2.png")
    private val albums = arrayOf(album1, album2, album3)
    private val photos = arrayOf(photo1, photo2)

    @BeforeEach
    fun initialize(){
        MockitoAnnotations.initMocks(this)

        `when`(restTemplate.getForObject(
            "https://jsonplaceholder.typicode.com/albums",
            Array<AlbumInfo>::class.java
        )).thenReturn(albums)

        `when`(restTemplate.getForObject(
            "https://jsonplaceholder.typicode.com/albums/1",
            AlbumInfo::class.java
        )).thenReturn(album1)

        `when`(restTemplate.getForObject(
            "https://jsonplaceholder.typicode.com/albums/2/photos",
            Array<Photo>::class.java
        )).thenReturn(photos)

        `when`(restTemplate.getForObject(
            "https://jsonplaceholder.typicode.com/albums/88888",
            AlbumInfo::class.java
        )).thenThrow(HttpClientErrorException::class.java)

        `when`(restTemplate.getForObject(
            "https://jsonplaceholder.typicode.com/albums/77777/photos",
            Array<Photo>::class.java
        )).thenThrow(HttpClientErrorException::class.java)
    }

    @Test
    fun getAllAlbumsInfoTest() {
        val response = albumService.getAllAlbumsInfo()
        val expect = albums.toList()

        assertEquals(expect, response)
        assertEquals(expect[1], response[1])
        assertEquals(expect[2], response[2])
    }

    @Test
    fun getAlbumInfoTest() {
        val response = albumService.getAlbumInfo(1)
        val expect = album1

        assertEquals(expect, response)
    }

    @Test
    fun getPhotos(){
        val response = albumService.getPhotos(2)
        val expect = photos.toList()

        assertEquals(expect, response)
    }

    @Test
    fun `return null when album not found`(){
        val response = albumService.getAlbumInfo(88888)
        assertNull(response)
    }

    @Test
    fun `return null when photos not found`(){
        val response = albumService.getPhotos(77777)
        assertNull(response)
    }
}