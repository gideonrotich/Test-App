package com.flexcode.authenticationapp.data.remote.response

data class LoginResponse(
    val success: Boolean,
    val redirect_url: String?
)
