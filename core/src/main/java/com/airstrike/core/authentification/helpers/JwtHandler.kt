package com.airstrike.core.authentification.helpers

object JwtHandler {
    fun isAccessTokenExpired(accessToken: String) : Boolean
    {
        val expirationInMillis = extractExpirationTime(accessToken)
        val currentTime = System.currentTimeMillis()
        return expirationInMillis != null && currentTime >= expirationInMillis
    }

    private fun extractExpirationTime(accessToken: String): Long? {
        try {
            val jwtBody = accessToken.split(".")[1]
            val decodedJwtBody = android.util.Base64.decode(jwtBody, android.util.Base64.URL_SAFE)
            val jwtBodyString = String(decodedJwtBody, Charsets.UTF_8)
            val matchResult = Regex("\"exp\"\\s*:\\s*(\\d+)").find(jwtBodyString)
            val result = matchResult!!.groupValues.get(1).toLong()
            // If the regex finds a match, extract and convert the expiration time
            return  result * 1000
        } catch (e: Exception) {
        return null
    }
    }
}