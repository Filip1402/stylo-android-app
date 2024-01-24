package com.airstrike.web_services.network

import HomepageContent
import retrofit2.Call
import retrofit2.http.GET

interface HomepageService {

    @GET("/homepage")
    fun  getHompageContent() : Call<HomepageContent>
}
