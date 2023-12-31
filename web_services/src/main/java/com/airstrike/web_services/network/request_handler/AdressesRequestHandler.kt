package com.airstrike.web_services.network.request_handler

import com.airstrike.web_services.models.responses.CustomersAddresses
import com.airstrike.web_services.network.NetworkService
import retrofit2.Call

class AddressesRequestHandler(private val email : String) : TemplateRequestHandler<CustomersAddresses>(){
    override fun getServiceCall(): Call<CustomersAddresses> {
        val service = NetworkService.customerService
        return service.getCustomerAddresses(email)
    }


}