package com.mobile.pft.login


data class LoginRequest(val emailUtec: String, val password: String)

data class LoginResponse(val message: String, val token: String)

