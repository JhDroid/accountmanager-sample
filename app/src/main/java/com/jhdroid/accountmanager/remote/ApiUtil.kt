package com.jhdroid.accountmanager.remote

import com.jhdroid.accountmanager.remote.service.ApiService

class ApiUtil {
    companion object {
        const val BASE_URL = ""

        fun getApiService(): ApiService {
            return RetrofitClient.getApiClient().create(ApiService::class.java)
        }
    }
}