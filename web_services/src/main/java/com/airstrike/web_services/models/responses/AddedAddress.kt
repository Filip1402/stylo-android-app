package com.airstrike.web_services.models.responses

import com.google.gson.annotations.SerializedName

data class AddedAddress(
    @SerializedName("response") val data: AddressResponse
)

data class AddressResponse(
    @SerializedName("version") val version: Int
)