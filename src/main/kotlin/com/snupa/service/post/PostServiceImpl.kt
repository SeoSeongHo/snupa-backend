package com.snupa.service.post

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.snupa.domain.Comment
import com.snupa.domain.Post
import com.snupa.dto.oauth.AuthPrincipal
import com.snupa.dto.post.PostReq
import com.snupa.dto.post.SearchType
import com.snupa.exception.PostAuthorNotMatchedException
import com.snupa.exception.PostNotFoundException
import com.snupa.repository.LikeRepository
import com.snupa.repository.PostRepository
import com.snupa.service.user.UserService
import com.snupa.util.EnumCollection
import com.snupa.util.S3Uploader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PostServiceImpl(
        @Autowired private val postRepository: PostRepository,
        @Autowired private val likeRepository: LikeRepository,
        @Autowired private val s3Uploader: S3Uploader,
        @Autowired private val userService: UserService
): PostService {
    private val jsonMapper = jacksonObjectMapper()

    override fun getPosts(recent: Boolean, popular: Boolean, type: SearchType, authPrincipal: AuthPrincipal): List<Post> {
        val posts: MutableList<Post> = when (type) {
            SearchType.ALL -> {
                postRepository.findAll()
            }
            SearchType.FAVORITE -> {
                val likes = likeRepository.findByUserId(authPrincipal.userId)
                likes.map{ it.post }.toMutableList()
            }
            else -> {
                postRepository.findByCategory(EnumCollection.Category.valueOf(type.name))
            }
        }

        if(recent && !popular) posts.sortByDescending { it.createdAt }
        if(!recent && popular) posts.sortByDescending { it.likes.size }

        return posts
    }

    override fun getPost(postId: Long): Post {
        return postRepository.findById(postId).orElseThrow{ PostNotFoundException(postId) }
    }

    override fun createPost(files: List<MultipartFile>?, postReq: PostReq, authPrincipal: AuthPrincipal): Post {
        val imageUrls = files?.map { s3Uploader.upload(it) }
        val poster = imageUrls?.let { jsonMapper.writeValueAsString(it) }
        val user = userService.getUser(authPrincipal.userId)

        val newPost = Post.of(postReq, user, poster)

        return postRepository.save(newPost)
    }

    override fun updatePost(files: List<MultipartFile>?, postId: Long, postReq: PostReq, authPrincipal: AuthPrincipal): Post {
        val post = getPost(postId)
        userService.getUser(authPrincipal.userId)

        if(!post.isOwnedBy(authPrincipal.userId)) throw PostAuthorNotMatchedException(postId, authPrincipal.userId)

        val imageUrls = files?.map { s3Uploader.upload(it) }
        val poster = imageUrls?.let { jsonMapper.writeValueAsString(it) }

        return post.update(postReq, poster)
    }

    override fun deletePost(postId: Long, authPrincipal: AuthPrincipal) {
        val post = getPost(postId)
        userService.getUser(authPrincipal.userId)

        if(!post.isOwnedBy(authPrincipal.userId)) throw PostAuthorNotMatchedException(postId, authPrincipal.userId)

        postRepository.delete(post)
    }
}