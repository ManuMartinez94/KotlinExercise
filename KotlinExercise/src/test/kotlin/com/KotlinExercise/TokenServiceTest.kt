package com.kotlinexercise

import com.kotlinexercise.services.TokenService
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class TokenServiceTest {

    private val tokenService: TokenService = TokenService()

    @Test
    fun `generate token test`(){
        val token = tokenService.getAndGenerateToken()

        assertTrue(token.isNotEmpty())
    }

    @Test
    fun `generate and validate token test`(){
        val token = tokenService.getAndGenerateToken()
        val success = tokenService.validateToken(token)

        assertTrue(success)
    }
}