package com.airstrike.core.authentification.network.models

import com.google.gson.annotations.SerializedName

data class ErrorResponseBody(
    @SerializedName("error")
    val error1: String ? = null,
    @SerializedName("Error")
    var error2: String ? = null
)
{
    val error: String?
        get() = error1 ?: error2
}
