package com.snupa.controller

import com.snupa.dto.oauth.AuthPrincipal
import com.snupa.dto.user.UserReq
import com.snupa.dto.user.UserRes
import com.snupa.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController (
        @Autowired private val userService: UserService
){
    @GetMapping("/")
    fun getSetting(@AuthenticationPrincipal authPrincipal: AuthPrincipal): ResponseEntity<UserRes> {
        var user = userService.getUser(authPrincipal.userId)
        return ResponseEntity.ok().body(user)
    }

    @PostMapping()
    //authPrincipal.userId
    fun updateSetting(@AuthenticationPrincipal authPrincipal: AuthPrincipal, @RequestBody userReq : UserReq): ResponseEntity<UserRes> {
        userReq.id = authPrincipal.userId
        var user = userService.updateUser(userReq)
        return ResponseEntity.ok().body(user)
    }
}