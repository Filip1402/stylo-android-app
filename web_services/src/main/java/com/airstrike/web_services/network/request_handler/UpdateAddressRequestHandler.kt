package com.airstrike.web_services.network.request_handler

import com.airstrike.web_services.models.UpdateAddressRequestBody
import com.airstrike.web_services.models.responses.AddedAddress
import com.airstrike.web_services.network.NetworkService
import retrofit2.Call

class UpdateAddressRequestHandler(private val updatedAddress: UpdateAddressRequestBody) : TemplateRequestHandler<AddedAddress>() {
    override fun getServiceCall(): Call<AddedAddress> {
        val service = NetworkService.customerService
        return service.updateAddress(updatedAddress)
    }
}
