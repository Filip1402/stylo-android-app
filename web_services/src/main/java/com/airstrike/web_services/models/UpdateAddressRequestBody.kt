package com.airstrike.web_services.models

data class UpdateAddressRequestBody(
    var customerId : String,
    var customerVersion: Int,
    var addressId : String,
    var address: ShippingAddress
)