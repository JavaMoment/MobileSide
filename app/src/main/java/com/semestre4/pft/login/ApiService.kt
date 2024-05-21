package com.semestre4.pft.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// define una interfaz de servicio de api psra las solicitudes de red
interface ApiService {
    // define un método para hacer una solicitud POST a la url "WebSide/"
    @POST("WebSide/")
    // el método doLogin toma un objeto loginRequest en el cuerpo de la solicitud
    // y devuelve un objeto Call que envuelve una respuesta de tipo LoginResponse
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
