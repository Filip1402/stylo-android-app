package com.airstrike.web_services.network

import com.airstrike.web_services.models.AddAddressRequestBody
import com.airstrike.web_services.models.LoginBody
import com.airstrike.web_services.models.RegistrationBody
import com.airstrike.web_services.models.UpdateAddressRequestBody
import com.airstrike.web_services.models.responses.AddedAddress
import com.airstrike.web_services.models.responses.CustomersAddresses
import com.airstrike.web_services.models.responses.JWT
import com.airstrike.web_services.models.responses.LoggedInUser
import com.airstrike.web_services.models.responses.RegisteredUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerService {
    @POST("customers/signup")
    fun registerCustomer(@Body registerBody : RegistrationBody) : Call<RegisteredUser>

    @POST("customers/login")
    fun loginCustomer(@Body loginBOdy : LoginBody) : Call<LoggedInUser>

    @GET("customers/{email}")
    fun getCustomerAddresses(
        @Header("Authorization") authorizationHeader: String,
        @Path("email") customerEmail: String)
    : Call<CustomersAddresses>
    @GET("getJWT")
    fun getJwt() : Call<JWT>

    @POST("customers/addAddress")
    fun addAddress(@Body newAddress : AddAddressRequestBody) : Call<AddedAddress>

    @PUT("customers/changeAddress")
    fun updateAddress(@Body updatedAddress : UpdateAddressRequestBody) : Call<AddedAddress>
}