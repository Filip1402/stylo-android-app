package com.airstrike.web_services.network.request_handler



import com.airstrike.web_services.models.responses.ProductDetailsResponse
import com.airstrike.web_services.network.NetworkService
import retrofit2.Call

class ProductsDetailsHandler(private val productId : String) : TemplateRequestHandler<ProductDetailsResponse>(){
    override fun getServiceCall(): Call<ProductDetailsResponse> {
        val service = NetworkService.productsService
        return service.getProductById(productId)
    }
}