package com.mobile.pft.login

import com.mobile.pft.utils.TipoUsuario


data class LoginRequest(val emailUtec: String, val password: String)

data class LoginResponse(val message: String, val token: String, val tipoUsuario: TipoUsuario, val username: String)

