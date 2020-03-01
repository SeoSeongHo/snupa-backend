package com.snupa.controller

import com.snupa.dto.oauth.AuthPrincipal
import com.snupa.dto.oauth.LoginReq
import com.snupa.dto.oauth.LoginRes
import com.snupa.service.OAuthService
import com.snupa.util.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/oauth")
class OAuthController(
        @Autowired
        private val oAuthService: OAuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginReq: LoginReq): ResponseEntity<LoginRes> {
        val loginRes = oAuthService.login(loginReq.code)
        return ResponseEntity(loginRes, HttpStatus.OK)
    }

    //로그인 테스트용 샘플 메소드
    @GetMapping("/test")
    fun test(@AuthenticationPrincipal authPrincipal: AuthPrincipal): Long {
        return authPrincipal.userId
    }
}