package com.KotlinExercise

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class ApiController {

    @GetMapping("/")
    fun hello(): ResponseEntity<String> {
        return ResponseEntity("Hello World", HttpStatus.OK)
    }
}