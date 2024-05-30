package com.mobile.pft.reclamos.data

import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import com.mobile.pft.reclamos.network.ReclamosApiService

/**
 * Repository encargado de pegarle a la api en el contexto del signup.
 */
interface ReclamosRepository {
    suspend fun getReclamos(): List<ReclamoDTO>
    suspend fun getStatusReclamo(): List<StatusReclamoDTO>
}

/**
 * Network Implementation (donde realmente se realiza la call).
 */
class NetworkReclamosRepository(
    private val reclamosApiService: ReclamosApiService
) : ReclamosRepository {
    override suspend fun getReclamos(): List<ReclamoDTO> = reclamosApiService.getReclamos()
    override suspend fun getStatusReclamo(): List<StatusReclamoDTO> = reclamosApiService.getStatusReclamo()
}