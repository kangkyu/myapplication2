package com.example.myapplication.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.request.get
import io.ktor.util.InternalAPI

private const val baseUrl = "https://kyohoe-authorization-server-1a58960ea78f.herokuapp.com"

@OptIn(InternalAPI::class)
class PostingApi {

    @Throws(Exception::class)
    suspend fun getPosting(id: Int): String {
        val client = HttpClient(CIO)
        val response = client.get("${baseUrl}/api/v1/postings/${id}")

        client.close()

        val data = response.content.toString()
        println(data)
        return data
    }
}
