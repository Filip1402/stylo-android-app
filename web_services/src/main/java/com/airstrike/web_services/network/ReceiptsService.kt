package com.airstrike.web_services.network

import com.airstrike.web_services.models.OrderBody
import com.airstrike.web_services.models.responses.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReceiptsService {
    @POST("/orders")
    fun crateOrder(@Body order:OrderBody): Call<OrderResponse>

}