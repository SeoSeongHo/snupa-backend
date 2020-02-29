package com.snupa.config.bean

import com.snupa.config.oauth.CustomOAuth2Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository

@Configuration
class BeanConfig {
    @Bean
    fun bCryptPasswordEncoder() : BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun clientRegistrationRepository(
            @Value("\${spring.security.oauth.client.registration.kakao.client-id}")
            kakaoClientId: String,
            @Value("\${spring.security.oauth.client.registration.kakao.client-secret}")
            kakaoClientSecret: String
    ): ClientRegistrationRepository {
        val registrations = arrayListOf<ClientRegistration>()
        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp")
                .build()
        )

        return InMemoryClientRegistrationRepository(registrations)
    }
}