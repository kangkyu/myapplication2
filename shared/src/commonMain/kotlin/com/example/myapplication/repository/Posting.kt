package com.example.myapplication.repository

import kotlinx.serialization.Serializable

@Serializable
data class Posting(
    val videoUrl: String,
    val title: String
)
