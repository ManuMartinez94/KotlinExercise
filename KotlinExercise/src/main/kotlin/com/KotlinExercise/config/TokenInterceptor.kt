package com.kotlinexercise.config

import com.kotlinexercise.services.TokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class TokenInterceptor(private val tokenService: TokenService): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = request.getHeader("Authorization")

        return when {

            token == null -> {
                response.status = HttpServletResponse.SC_BAD_REQUEST
                false
            }

            !tokenService.validateToken(token) -> {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                false
            }

            else -> true
        }
    }
}