package com.vicky7230.poddproblem.data

import com.google.gson.JsonElement
import com.vicky7230.poddproblem.data.network.AppApiHelper
import retrofit2.Response
import javax.inject.Inject

class AppDataManager @Inject
constructor(
    private val appApiHelper: AppApiHelper
) : DataManager {
    override suspend fun getVariants(): Response<JsonElement> {
        return appApiHelper.getVariants()
    }
}