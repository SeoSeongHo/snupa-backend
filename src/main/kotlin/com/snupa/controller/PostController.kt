package com.snupa.controller

import com.snupa.domain.Post
import com.snupa.dto.oauth.AuthPrincipal
import com.snupa.dto.post.PostReq
import com.snupa.dto.post.SearchType
import com.snupa.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
        @Autowired private val postService: PostService
) {
    @GetMapping("/")
    fun getPosts(
            @RequestParam("recent") recent: Boolean,
            @RequestParam("popular") popular: Boolean,
            @RequestParam("type") type: SearchType
    ): ResponseEntity<List<Post>> {
        val posts = postService.getPosts(recent, popular, type)
        return ResponseEntity(posts, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable("id") postId: Long): ResponseEntity<Post> {
        val post = postService.getPost(postId)
        return ResponseEntity(post, HttpStatus.OK)
    }

    @PostMapping("/")
    fun createPost(@RequestParam("files") files: List<MultipartFile>?,
                   @RequestBody @Valid postReq: PostReq,
                   @AuthenticationPrincipal authPrincipal: AuthPrincipal
    ): ResponseEntity<Post> {
        val post = postService.createPost(files, postReq, authPrincipal)
        return ResponseEntity(post, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updatePost(@RequestParam("files") files: List<MultipartFile>?,
                   @PathVariable("id") postId: Long,
                   @RequestBody @Valid postReq: PostReq,
                   @AuthenticationPrincipal authPrincipal: AuthPrincipal
    ): ResponseEntity<Post> {
        val post = postService.updatePost(files, postId, postReq, authPrincipal)
        return ResponseEntity(post, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable("id") postId: Long,
                   @AuthenticationPrincipal authPrincipal: AuthPrincipal
    ): ResponseEntity<Unit> {
        postService.deletePost(postId, authPrincipal)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}