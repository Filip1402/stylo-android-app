package com.airstrike.core.authentification

interface LoginToken {
    fun getAccessToken()
    fun getRefreshToken()
}