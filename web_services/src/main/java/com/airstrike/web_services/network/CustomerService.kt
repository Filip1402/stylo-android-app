package com.airstrike.web_services.network

import com.airstrike.web_services.models.RegistrationBody
import com.airstrike.web_services.models.responses.RegisteredUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CustomerService {
    @POST("customers/signup")
    fun registerCustomer(@Body registerBody : RegistrationBody) : Call<RegisteredUser>

}