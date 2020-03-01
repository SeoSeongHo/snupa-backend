package com.snupa.util

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.snupa.exception.KakaoGetAccessTokenException
import com.snupa.exception.KakaoGetUserInfoException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


@Service
class KakaoAPI(
        @Value("\${spring.security.oauth.client.registration.kakao.client-id:4742178bb6bdac8c5fe5cff3aff900d2}")
        private val kakaoClientId: String,
        @Value("\${spring.security.oauth.client.registration.kakao.redirect-uri:http://localhost:8080/oauth}")
        private val kakaoRedirectUri: String
) {
    private val jsonMapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TokenResponse(
            var access_token: String,
            var refresh_token: String?
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class UserInfoResponse(
            var id: Long,
            var properties: UserProperties
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    class UserProperties(
            var nickname: String
    )

    class KakaoUserInfoResult(
            var userId: Long,
            var nickname: String
    )

    fun getAccessToken(authorize_code: String): TokenResponse {
        val reqURL = Constants.KAKAO_TOKEN_URI
        try {
            val url = URL(reqURL)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.requestMethod = "POST"
            conn.doOutput = true

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            val bw = BufferedWriter(OutputStreamWriter(conn.outputStream))
            val sb = StringBuilder()
            sb.append("grant_type=authorization_code")
            sb.append("&client_id=$kakaoClientId")
            sb.append("&redirect_uri=$kakaoRedirectUri")
            sb.append("&code=$authorize_code")
            bw.write(sb.toString())
            bw.flush()

            //    결과 코드가 200이라면 성공
            val responseCode = conn.responseCode

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            val br = BufferedReader(InputStreamReader(conn.inputStream))
            var line: String? = ""
            var result = ""

            line = br.readLine()
            while (line != null) {
                result += line
                line = br.readLine()
            }

            br.close()
            bw.close()

            return jsonMapper.readValue(result)
        } catch (e: IOException) {
            e.printStackTrace()
            throw KakaoGetAccessTokenException(e.message)
        }
    }

    fun getUserInfo(accessToken: String): KakaoUserInfoResult {
        val reqURL = Constants.KAKAO_USER_INFO_URI
        try {
            val url = URL(reqURL)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer $accessToken")
            val responseCode = conn.responseCode

            val br = BufferedReader(InputStreamReader(conn.inputStream))
            var line: String? = ""
            var result = ""

            line = br.readLine()
            while (line != null) {
                result += line
                line = br.readLine()
            }

            val response: UserInfoResponse = jsonMapper.readValue(result)

            val userId = response.id
            val nickname = response.properties.nickname

            return KakaoUserInfoResult(userId, nickname)
        } catch (e: IOException) {
            throw KakaoGetUserInfoException(e.message)
        }
    }
}