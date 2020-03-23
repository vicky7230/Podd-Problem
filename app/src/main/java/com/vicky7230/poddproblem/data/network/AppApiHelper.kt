package com.vicky7230.poddproblem.data.network


import com.google.gson.JsonElement
import com.vicky7230.poddproblem.data.network.ApiHelper
import com.vicky7230.poddproblem.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class AppApiHelper @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getVariants(): Response<JsonElement> {
        return apiService.getVariants()
    }
}