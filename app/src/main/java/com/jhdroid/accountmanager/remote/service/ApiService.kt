package com.jhdroid.accountmanager.remote.service

import com.google.gson.JsonObject
import com.jhdroid.accountmanager.remote.model.Auth
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/login")
    fun login(@Body userData: JsonObject): Call<Auth>
}