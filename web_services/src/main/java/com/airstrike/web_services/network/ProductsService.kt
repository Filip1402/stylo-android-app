package com.airstrike.web_services.network

import com.airstrike.web_services.models.responses.ProductDetailsResponse
import com.airstrike.web_services.models.responses.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductsService {

    @GET("/products/filter")
    fun  getFilteredProducts(
        @Query("gender") category : String,
    ) : Call<List<ProductResponse>>
    @GET("/products/{id}/")
    fun getProductById(
        @Path("id") productId: String
    ): Call<ProductDetailsResponse>
}