package com.airstrike.web_services.network.request_handler

import com.airstrike.web_services.models.AddAddressRequestBody
import com.airstrike.web_services.models.responses.AddedAddress
import com.airstrike.web_services.network.NetworkService
import retrofit2.Call

class AddAddressRequestHandler(private val newAddress: AddAddressRequestBody) : TemplateRequestHandler<AddedAddress>() {
    override fun getServiceCall(): Call<AddedAddress> {
        val service = NetworkService.customerService
        return service.addAddress(newAddress)
    }
}
