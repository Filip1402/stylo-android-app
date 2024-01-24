package com.airstrike.web_services.network.request_handler

import com.airstrike.web_services.models.responses.JWT
import com.airstrike.web_services.network.NetworkService
import retrofit2.Call

class JwtRequestHandler() : TemplateRequestHandler<JWT>() {
    override fun getServiceCall(): Call<JWT> {
        val service = NetworkService.customerService
        return service.getJwt()
    }
}