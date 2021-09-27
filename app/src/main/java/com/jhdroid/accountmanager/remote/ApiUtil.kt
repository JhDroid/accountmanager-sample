package com.jhdroid.accountmanager.remote

import com.jhdroid.accountmanager.remote.service.ApiService

class ApiUtil {
    companion object {
        // 인증 서버 BASE URL 또는 Postman Mock Server URL
        const val BASE_URL = ""

        fun getApiService(): ApiService {
            return RetrofitClient.getApiClient().create(ApiService::class.java)
        }
    }
}