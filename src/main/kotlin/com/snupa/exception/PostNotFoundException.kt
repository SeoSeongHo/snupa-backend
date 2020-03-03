package com.snupa.exception

class PostNotFoundException(val postId: Long): RuntimeException()