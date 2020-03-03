package com.snupa.controller

import com.snupa.domain.Post
import com.snupa.dto.post.SearchType
import com.snupa.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
}