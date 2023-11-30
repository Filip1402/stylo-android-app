package com.airstrike.web_services.models.responses

import com.google.gson.annotations.SerializedName

data class LoggedInUser  (
    @SerializedName("success") var success: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("accessToken") var accessToken: String? = null,
    @SerializedName("refreshToken") var refreshToken: String? = null,
): RegisteredUser()