package com.airstrike.core.authentification.network
import com.airstrike.core.authentification.network.models.ErrorResponseBody

interface ResponseListener<T> {
    fun onSuccess(response : T)
    fun onErrorResponse(response : ErrorResponseBody)
    fun onFailure(t: Throwable)

}