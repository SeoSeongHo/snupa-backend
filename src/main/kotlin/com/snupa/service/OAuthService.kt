package com.snupa.service

import com.snupa.config.security.jwt.JwtTokenProvider
import com.snupa.domain.User
import com.snupa.dto.oauth.LoginRes
import com.snupa.repository.UserRepository
import com.snupa.util.KakaoAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OAuthService(
        @Autowired
        private val kakaoAPI: KakaoAPI,
        @Autowired
        private val jwtTokenProvider: JwtTokenProvider,
        @Autowired
        private val userRepository: UserRepository
) {
    fun login(code: String): LoginRes {
        val tokenResponse = kakaoAPI.getAccessToken(code)
        val userInfo = kakaoAPI.getUserInfo(tokenResponse.access_token)

        if(!userRepository.existsById(userInfo.userId)) {
            val newUser = User(id = userInfo.userId, nickname = userInfo.nickname)
            userRepository.save(newUser)
        }

        return LoginRes(jwtTokenProvider.createToken(userInfo.userId))
    }
}