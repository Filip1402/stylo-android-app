package com.airstrike.web_services.network.request_handler

import com.airstrike.web_services.models.LoginBody
import com.airstrike.web_services.models.RegistrationBody
import com.airstrike.web_services.models.responses.LoggedInUser
import com.airstrike.web_services.models.responses.ProductResponse
import com.airstrike.web_services.network.NetworkService
import retrofit2.Call

class ProductsRequestHandler(private val categoryFilter : String) : TemplateRequestHandler<List<ProductResponse>>() {
    override fun getServiceCall(): Call<List<ProductResponse>> {
        val service = NetworkService.productsService
        return service.getFilteredProducts(categoryFilter)
    }
}