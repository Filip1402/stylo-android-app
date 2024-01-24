package com.airstrike.web_services.network.request_handler

import HomepageContent
import com.airstrike.web_services.network.NetworkService
import retrofit2.Call

class HomepageRequestHandler() : TemplateRequestHandler<HomepageContent>() {
    override fun getServiceCall(): Call<HomepageContent> {
        val service = NetworkService.hompageService
        return service.getHompageContent()
    }
}