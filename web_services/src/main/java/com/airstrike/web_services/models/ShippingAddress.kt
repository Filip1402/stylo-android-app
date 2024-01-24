package com.airstrike.web_services.models

data class ShippingAddress(
    val firstName : String,
    val lastName : String,
    val additionalStreetInfo : String,
    val phone : String,
    var streetName : String,
    var streetNumber : String,
    var postalCode : String,
    var city : String,
    var country : String
)