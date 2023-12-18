package com.airstrike.web_services.models.responses

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class ProductResponse(
    @SerializedName("id") val id: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("model") val model: String,
    @SerializedName("price") val price: Double,
    @SerializedName("available") val available: Boolean,
    @SerializedName("images") val images: List<Any>
) {

    fun extractImages(): List<String> {
        return images.flatMap { imageItem ->
            when (imageItem) {
                is String -> listOf(imageItem)
                is JSONArray -> extractImages(imageItem)
                else -> emptyList()
            }
        }
    }

    private fun extractImages(imagesJsonArray: JSONArray): List<String> {
        val imagesList = mutableListOf<String>()

        for (i in 0 until imagesJsonArray.length()) {
            val imageItem = imagesJsonArray[i]

            if (imageItem is String) {
                imagesList.add(imageItem)
            } else if (imageItem is JSONArray) {
                // Handle nested array
                for (j in 0 until imageItem.length()) {
                    if (imageItem[j] is String) {
                        imagesList.add(imageItem[j] as String)
                    }
                }
            }
        }
        return imagesList
    }
}

