package com.mobile.pft.reclamos.network

import com.mobile.pft.model.EventoDTO
import com.mobile.pft.model.NewClaimDTO
import com.mobile.pft.model.PatchDTO
import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import com.mobile.pft.utils.APPLICATION_JSON_PATCH
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Interfaz publica donde exponemos los metodos api relacionados al signup
 */
interface ReclamosApiService {
    /**
     * GET que devuelve una [List] de [ReclamoDTO] y puede ser llamado desde una coroutine (cof cof composable cof cof).
     */


    @GET("reclamos")
    suspend fun getReclamos(): List<ReclamoDTO>

    @GET("reclamos/statuses")
    suspend fun getStatusReclamo(): List<StatusReclamoDTO>

    @GET("reclamos/{idReclamo}")
    suspend fun getReclamoBy(@Path("idReclamo") idReclamo: Long): ReclamoDTO

    @GET("reclamos/estudiante/{nombreUsuario}")
    suspend fun getReclamosBy(@Path("nombreUsuario") nombreUsuario: String): List<ReclamoDTO>

    @GET("reclamos/eventos")
    suspend fun getEventos(): List<EventoDTO>

    @Headers("Content-Type: $APPLICATION_JSON_PATCH")
    @PATCH("reclamos/{idReclamo}")
    suspend fun partialUpdate(@Path("idReclamo") idReclamo: Long, @Body operation: ArrayList<PatchDTO>): ReclamoDTO
}