package com.snupa.service

import com.snupa.domain.Post
import com.snupa.dto.oauth.AuthPrincipal
import com.snupa.dto.post.PostReq
import com.snupa.dto.post.SearchType
import org.springframework.web.multipart.MultipartFile

interface PostService {
    fun getPosts(recent: Boolean, popular: Boolean, type: SearchType): List<Post>
    fun getPost(postId: Long): Post
    fun createPost(files: List<MultipartFile>?, postReq: PostReq, authPrincipal: AuthPrincipal): Post
    fun updatePost(files: List<MultipartFile>?, postId: Long, postReq: PostReq, authPrincipal: AuthPrincipal): Post
    fun deletePost(postId: Long, authPrincipal: AuthPrincipal): Unit
}