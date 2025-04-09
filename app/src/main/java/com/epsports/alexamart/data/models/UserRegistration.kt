package com.epsports.alexamart.data.models

data class UserRegistration(
    val name: String,
    val email: String,
    val password: String,
    val userType: String,
    var userId: String,
)
