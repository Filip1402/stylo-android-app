package com.airstrike.web_services.request_handler

import android.util.Log
import com.airstrike.core.authentification.network.RequestListener
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class TemplateRequestHandler<T>  : RequestListener<T> {
    override fun sendRequest(listener: ResponseListener<T>) {
        val serviceCall = getServiceCall()
        serviceCall.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {

                if(response.isSuccessful) {
                    val responseBody: T? = response.body()
                    if (responseBody != null) {
                        listener.onSuccess(responseBody)
                    }
                }
                else
                {
                    var error_body = response.errorBody()
                    listener.onErrorResponse(Gson().fromJson(error_body.string(),ErrorResponseBody::class.java))
                }

            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                listener.onFailure(t)
            }
        })
    }

    protected abstract fun getServiceCall() : Call<T>
}