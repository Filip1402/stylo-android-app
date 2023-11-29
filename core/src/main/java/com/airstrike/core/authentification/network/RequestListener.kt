package com.airstrike.core.authentification.network

interface RequestListener<T> {
    fun sendRequest(listener : ResponseListener<T>)
}