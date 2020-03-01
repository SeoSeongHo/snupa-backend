package com.snupa.controller

import com.snupa.service.SettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/setting")
class SettingController (
        @Autowired private val settingService: SettingService
){
}