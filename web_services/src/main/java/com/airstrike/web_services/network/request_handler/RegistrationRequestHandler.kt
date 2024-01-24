package com.airstrike.web_services.network.request_handler

import com.airstrike.web_services.models.RegistrationBody
import com.airstrike.web_services.models.responses.RegisteredUser
import com.airstrike.web_services.network.NetworkService
import retrofit2.Call

class RegistrationRequestHandler(private val reqBody: RegistrationBody) : TemplateRequestHandler<RegisteredUser>(){
    override fun getServiceCall(): Call<RegisteredUser> {
        val service = NetworkService.customerService
        return service.registerCustomer(reqBody)
    }


}