package com.snupa.service.user

import com.snupa.dto.user.UserReq
import com.snupa.dto.user.UserRes
import com.snupa.exception.UserIdNotFoundException
import com.snupa.mapper.UserMapper
import com.snupa.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        @Autowired private val userRepository : UserRepository
) : UserService {
    override fun getUser(userId: Long): UserRes {
        val user = userRepository.findById(userId)
        if(user.isPresent)
            throw UserIdNotFoundException("can not find user : $userId")

        return UserMapper.toDto(user.get())
    }

    override fun updateUser(userReq : UserReq): UserRes {
        val user = userRepository.save(UserMapper.toEntity(userReq))
        return UserMapper.toDto(user)
    }
}