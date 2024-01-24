package com.airstrike.stylo.models

data class Address(
    val id: String?,
    val firstName: String?,
    val lastName: String?,
    val streetName: String?,
    val streetNumber: String?,
    val additionalStreetInfo: String?,
    val postalCode: String?,
    val city: String?,
    val country: String?,
    val phone: String?,
    //val additionalAddressInfo: String
)