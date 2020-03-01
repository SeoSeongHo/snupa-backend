package com.snupa.exception

import java.lang.RuntimeException

class JwtValidationException(val msg: String?) : RuntimeException()