package com.snupa.config.oauth

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

class CustomOAuth2Provider {
    class KAKAO {
        companion object {
            private val DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/{registrationId}"

            fun getBuilder(registrationId: String): ClientRegistration.Builder {
            return ClientRegistration.withRegistrationId(registrationId)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUriTemplate(DEFAULT_LOGIN_REDIRECT_URL)
                    .scope("profile")
                    .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                    .tokenUri("https://kauth.kakao.com/oauth/token")
                    .userInfoUri("https://kapi.kakao.com/v1/user/me")
                    .userNameAttributeName("id")
                    .clientName("Kakao")
            }
        }
    }
}