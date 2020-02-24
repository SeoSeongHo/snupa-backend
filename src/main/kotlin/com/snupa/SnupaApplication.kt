package com.snupa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SnupaApplication

fun main(args: Array<String>) {
    runApplication<SnupaApplication>(*args)
}
