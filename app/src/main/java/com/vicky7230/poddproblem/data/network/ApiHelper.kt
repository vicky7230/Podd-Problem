package com.vicky7230.poddproblem.data.network

import com.google.gson.JsonElement
import retrofit2.Response


interface ApiHelper {

    suspend fun getVariants(): Response<JsonElement>
}