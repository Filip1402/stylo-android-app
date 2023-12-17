package com.airstrike.web_services.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    private var instance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val customerService : CustomerService = instance.create(CustomerService::class.java)
    val productsService : ProductsService = instance.create(ProductsService::class.java)
}


