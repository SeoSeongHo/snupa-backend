package com.snupa.config.security.jwt

import com.snupa.dto.oauth.AuthPrincipal
import com.snupa.exception.JwtValidationException
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
        @Value("\${jwt.secretKey: SNUPA_JWT_SECRET_KEY}")
        private val secretKey: String
) {
    private val validityInMilliseconds: Long = 36000000

    fun createToken(userId: Long): String {
        val claims: Claims = Jwts.claims().setSubject(userId.toString())

        val validity = Date(Date().time + validityInMilliseconds)

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val principal = AuthPrincipal(getClaims(token).subject.toLong())

        return UsernamePasswordAuthenticationToken(principal, "", mutableListOf())
    }

    fun resolveToken(req : HttpServletRequest): String? {
        val bearerToken: String? = req.getHeader("Authorization")
        bearerToken?: return null

        if(!bearerToken.startsWith("Bearer ")) return null

        return bearerToken.substring(7, bearerToken.length)
    }

    fun validateToken(token: String): Boolean {
        try{
            if(getClaims(token).expiration.before(Date())) return false
            return true
        } catch (e: Exception) {
            throw JwtValidationException(e.message)
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
    }
}