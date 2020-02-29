package com.snupa.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.snupa.exception.KakaoGetAccessTokenException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


@Service
class KakaoAPI(
        @Value("\${spring.security.oauth.client.registration.kakao.client-id}")
        private val kakaoClientId: String,
        @Value("\${spring.security.oauth.client.registration.kakao.redirect-uri}")
        private val kakaoRedirectUri: String
) {
    data class TokenResponse(
            var access_token: String,
            var refresh_token: String?
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
            var line = ""
            var result = ""
            while (br.readLine().also { line = it } != null) {
                result += line
            }

            br.close()
            bw.close()

            val jsonMapper = jacksonObjectMapper()

            return jsonMapper.readValue(result)
        } catch (e: IOException) {
            throw KakaoGetAccessTokenException(e.message)
        }
    }


}