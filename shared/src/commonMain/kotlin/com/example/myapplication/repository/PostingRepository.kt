package com.example.myapplication.repository

import com.example.myapplication.api.PostingApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json

class PostingRepository(private val api: PostingApi) {
    val posting = MutableStateFlow<Posting>(Posting("", ""))

    suspend fun getPosting(postingId: Int): Result<Unit> {
        return runCatching {
            val result = api.getPosting(postingId)
            posting.value = Json.decodeFromString<Posting>(result)
        }
    }
}
