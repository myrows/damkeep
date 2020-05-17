package com.example.damkeep

data class RegisterResponse(
    val fullName: String,
    val id: String,
    val roles: String,
    val username: String
)