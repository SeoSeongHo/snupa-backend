package com.snupa.service

import com.snupa.domain.Post
import com.snupa.dto.oauth.AuthPrincipal
import com.snupa.dto.post.PostReq
import com.snupa.dto.post.SearchType
import com.snupa.exception.PostNotFoundException
import com.snupa.repository.PostRepository
import com.snupa.util.S3Uploader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PostServiceImpl(
        @Autowired private val postRepository: PostRepository,
        @Autowired private val s3Uploader: S3Uploader
): PostService {
    override fun getPosts(recent: Boolean, popular: Boolean, type: SearchType): List<Post> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPost(postId: Long): Post {
        val post = postRepository.findById(postId)
        if(!post.isPresent) throw PostNotFoundException(postId)

        return post.get()
    }

    override fun createPost(files: List<MultipartFile>?, postReq: PostReq, authPrincipal: AuthPrincipal): Post {
        val imageUrls = files?.map { s3Uploader.upload(it) }

    }

    override fun updatePost(files: List<MultipartFile>?, postId: Long, postReq: PostReq, authPrincipal: AuthPrincipal): Post {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePost(postId: Long, authPrincipal: AuthPrincipal) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}