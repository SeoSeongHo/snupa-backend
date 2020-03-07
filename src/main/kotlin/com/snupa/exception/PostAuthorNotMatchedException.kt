package com.snupa.exception

class PostAuthorNotMatchedException(val postId: Long, val userId: Long): RuntimeException()