package com.airstrike.web_services.request_handler

import com.airstrike.web_services.models.LoginBody

import com.airstrike.web_services.models.responses.LoggedInUser

import com.airstrike.web_services.network.NetworkService
import retrofit2.Call

class LoginRequestHandler(private val reqBody: LoginBody) : TemplateRequestHandler<LoggedInUser>(){
    override fun getServiceCall(): Call<LoggedInUser> {
        val service = NetworkService.customerService
        return service.loginCustomer(reqBody)
    }
}