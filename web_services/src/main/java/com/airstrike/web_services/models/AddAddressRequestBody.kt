package com.airstrike.web_services.models

data class AddAddressRequestBody(
    var customerId : String,
    var customerVersion: Int,
    var address: ShippingAddress
)