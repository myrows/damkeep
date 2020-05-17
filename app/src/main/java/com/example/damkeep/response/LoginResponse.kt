package com.example.damkeep.response

data class LoginResponse(
    val token: String,
    val user: User
)