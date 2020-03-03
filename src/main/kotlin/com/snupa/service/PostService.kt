package com.snupa.service

import com.snupa.domain.Post

interface PostService {
    fun getPosts(recent: Boolean, popular: Boolean): List<Post>
    fun getPost(postId: Long): Post
    fun createPost(): Post
    fun updatePost(): Post
    fun deletePost(postId: Long): Unit
}