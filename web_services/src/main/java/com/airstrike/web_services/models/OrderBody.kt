package com.airstrike.web_services.models

import org.json.JSONArray
import org.json.JSONObject

data class OrderBody (
    val cart : List<CartItem>,
    val shippingAddress : ShippingAddress,
    val customerId : String,
    val customerEmail : String
)