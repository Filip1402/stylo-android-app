package com.airstrike.web_services.models.responses

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
        @SerializedName("id") val id: String,
        @SerializedName("manufacturer") val manufacturer: String,
        @SerializedName("model") val model: String,
        @SerializedName("available") val available: Boolean,
        @SerializedName("price") val price: Double,
        @SerializedName("type") val type: String,
        @SerializedName("categories") val categories: List<String>,
        @SerializedName("variants") val variants: List<Variant>
    )

    data class Variant(
        @SerializedName("sku") val sku: String,
        @SerializedName("color") val color: String,
        @SerializedName("images") val images: List<String>,
        @SerializedName("sizes") val sizes: List<Size>
    )

    data class Size(
        @SerializedName("size") val size: Int,
        @SerializedName("quantity") val quantity: Int
    )

