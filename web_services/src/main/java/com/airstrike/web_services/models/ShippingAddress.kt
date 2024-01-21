package com.airstrike.web_services.models

data class ShippingAddress(
    var streetName : String,
    var streetNumber : String,
    var postalCode : String,
    var city : String,
    var country : String
)