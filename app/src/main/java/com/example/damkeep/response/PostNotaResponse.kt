package com.example.damkeep.response

data class PostNotaResponse(
    val body: String,
    val id: String,
    val lastUpdated: String,
    val timeCreated: String,
    val title: String,
    val uuidUser: String
)