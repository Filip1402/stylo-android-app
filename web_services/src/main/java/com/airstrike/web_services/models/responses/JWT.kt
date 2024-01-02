package com.airstrike.web_services.models.responses

import com.google.gson.annotations.SerializedName

data class JWT (
    @SerializedName("jwt") val token: String
)