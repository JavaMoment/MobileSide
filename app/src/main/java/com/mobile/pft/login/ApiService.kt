package com.mobile.pft.login

import com.semestre4.pft.claims.ClaimDTO
import com.semestre4.pft.claims.EventoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// define una interfaz de servicio de api psra las solicitudes de red
interface ApiService {
    // define un método para hacer una solicitud POST a la url "WebSide/"
    @POST("login")
    // el método doLogin toma un objeto loginRequest en el cuerpo de la solicitud
    // y devuelve un objeto Call que envuelve una respuesta de tipo LoginResponse
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("reclamos/eventos")
    fun getEvents(): Call<List<EventoDTO>>

    @POST("reclamos")
    fun postClaims(@Body claimDTO: ClaimDTO): Call<ClaimDTO>
}
