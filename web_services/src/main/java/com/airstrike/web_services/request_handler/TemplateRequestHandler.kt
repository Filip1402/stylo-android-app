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

                Log.i("Test",response.message())
                Log.i("Test",response.code().toString())
                Log.i("Test",response.headers().toString())
                if(response.isSuccessful) {
                    val responseBody: T? = response.body()
                    Log.i("TEST","adsa "+ responseBody?.toString())
                    Log.i("TEST",(responseBody.toString()))
                    if (responseBody != null) {
                        listener.onSuccess(responseBody)
                    }
                }
                else
                {
                    var error_body = response.errorBody()
                    Log.i("TEST", error_body.toString())
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