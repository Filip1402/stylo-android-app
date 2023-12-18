package com.airstrike.web_services.models

data class RegistrationBody(
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val password: String,
)