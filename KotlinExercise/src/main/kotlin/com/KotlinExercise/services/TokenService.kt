package com.kotlinexercise.services

import com.kotlinexercise.utils.minutes
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {
    private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun getAndGenerateToken(): String {
        val currentDate = Date()

        val expiration = Date(currentDate.time + 20.minutes)

        return Jwts.builder()
            .setIssuedAt(currentDate)
            .setExpiration(expiration)
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        }catch (e: Exception){
            false
        }
    }
}