package com.snupa.repository

import com.snupa.domain.Setting
import org.springframework.data.repository.CrudRepository

interface SettingRepository : CrudRepository<Setting, String>