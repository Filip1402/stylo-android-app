package com.airstrike.web_services.network.request_handler

import com.airstrike.web_services.models.OrderBody
import com.airstrike.web_services.models.responses.OrderResponse
import com.airstrike.web_services.network.NetworkService
import com.airstrike.web_services.network.ReceiptsService
import retrofit2.Call

class CreateOrderRequestHandler(private val order : OrderBody) : TemplateRequestHandler<OrderResponse>(){
    override fun getServiceCall(): Call<OrderResponse> {
        val service = NetworkService.receiptsService
        return service.crateOrder(order)
    }


}