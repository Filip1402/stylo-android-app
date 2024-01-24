package com.airstrike.web_services.models.responses

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("id") val id: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("streetName") val streetName: String?,
    @SerializedName("streetNumber") val streetNumber: String?,
    @SerializedName("additionalStreetInfo") val additionalStreetInfo: String?,
    @SerializedName("postalCode") val postalCode: String?,
    @SerializedName("city") val city: String?,
    //@SerializedName("region") val region: String,
    //@SerializedName("state") val state: String,
    @SerializedName("country") val country: String?,
    /*@SerializedName("company") val company: String,
    @SerializedName("apartment") val apartment: String,
    @SerializedName("pOBox") val pOBox: String,*/
    @SerializedName("phone") val phone: String?,
    @SerializedName("email") val email: String?,
    //@SerializedName("additionalAddressInfo") val additionalAddressInfo: String
)
data class CustomersAddresses(
    @SerializedName("addresses") val addresses: List<Address>,
    @SerializedName("defaultShippingAddressId") val defaultShippingAddressId: String?,
    @SerializedName("defaultBillingAddressId") val defaultBillingAddressId: String?,
    @SerializedName("shippingAddressIds") val shippingAddressIds: List<String>?,
    @SerializedName("billingAddressIds") val billingAddressIds: List<String>?
)