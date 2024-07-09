package com.kotlinexercise

import com.kotlinexercise.services.TokenService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerIntegrationTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Autowired
	private lateinit var tokenService: TokenService

	private lateinit var token: String

	@BeforeEach
	fun initialize(){
		token = tokenService.getAndGenerateToken()
	}

	@Test
	fun `get and generate token`(){
		mockMvc.perform(get("/api/token"))
			.andExpect(status().isOk)
	}

	@Test
	fun `get all albums`(){
		mockMvc.perform(get("/api/albums")
			.header("Authorization", token))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	}

	@Test
	fun `get album info`(){
		mockMvc.perform(get("/api/albums/1/info")
			.header("Authorization", token))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	}

	@Test
	fun `get album`(){
		mockMvc.perform(get("/api/albums/1")
			.header("Authorization", token))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	}

	@Test
	fun `get photos`(){
		mockMvc.perform(get("/api/albums/1/photos")
			.header("Authorization", token))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	}

	@Test
	fun `get with fake token`(){
		mockMvc.perform(get("/api/albums")
			.header("Authorization", "fake token"))
			.andExpect(status().isUnauthorized)
	}

	@Test
	fun `get album when not found`(){
		mockMvc.perform(get("/api/album/9999999")
			.header("Authorization", token))
			.andExpect(status().isNotFound)
	}

	@Test
	fun `get photos when not found`(){
		mockMvc.perform(get("/api/albums/99999999/photos")
			.header("Authorization", token))
			.andExpect(status().isNotFound)
	}
}
