package com.mobile.pft.reclamos.network

import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import retrofit2.http.GET

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
}