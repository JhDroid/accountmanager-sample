package com.jhdroid.accountmanager.remote.model

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("code") val code: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("auth_token") val authToken: String?
)
