package com.vicky7230.poddproblem.data.network

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("bins/3b0u2")
    suspend fun getVariants(): Response<JsonElement>
}