package com.mobile.pft.login


import com.mobile.pft.model.Evento
import com.mobile.pft.model.NewClaimDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// define una interfaz de servicio de api psra las solicitudes de red
interface ApiService {
    // define un método para hacer una solicitud POST a la url "WebSide/"
    @POST("login")
    // el método doLogin toma un objeto loginRequest en el cuerpo de la solicitud
    // y devuelve un objeto Call que envuelve una respuesta de tipo LoginResponse
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("reclamos/eventos")
    fun getEventos(): Call<List<Evento>>

    @POST("reclamos/{username}")
    fun postClaims( @Path("username") username: String?, @Body newClaimDTO: NewClaimDTO): Call<NewClaimDTO>



}
