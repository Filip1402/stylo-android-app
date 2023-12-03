package com.airstrike.web_services.network

import com.airstrike.web_services.models.LoginBody
import com.airstrike.web_services.models.RegistrationBody
import com.airstrike.web_services.models.responses.LoggedInUser
import com.airstrike.web_services.models.responses.RegisteredUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CustomerService {
    @POST("customers/signup")
    fun registerCustomer(@Body registerBody : RegistrationBody) : Call<RegisteredUser>

    @POST("customers/login")
    fun loginCustomer(@Body loginBOdy : LoginBody) : Call<LoggedInUser>
}