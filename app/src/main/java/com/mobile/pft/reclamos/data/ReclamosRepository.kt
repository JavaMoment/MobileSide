package com.mobile.pft.reclamos.data

import com.mobile.pft.model.EventoDTO
import com.mobile.pft.model.PatchDTO
import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import com.mobile.pft.reclamos.network.ReclamosApiService

/**
 * Repository encargado de pegarle a la api en el contexto del signup.
 */
interface ReclamosRepository {
    suspend fun getReclamos(): List<ReclamoDTO>
    suspend fun getStatusReclamo(): List<StatusReclamoDTO>
    suspend fun getReclamoBy(idReclamo: Long): ReclamoDTO
    suspend fun getReclamosBy(nombreUsuario: String): List<ReclamoDTO>
    suspend fun getEventos(): List<EventoDTO>
    suspend fun partialUpdate(idReclamo: Long, operation: PatchDTO): ReclamoDTO
    suspend fun updateReclamo(reclamo: ReclamoDTO): ReclamoDTO
}

/**
 * Network Implementation (donde realmente se realiza la call).
 */
class NetworkReclamosRepository(
    private val reclamosApiService: ReclamosApiService
) : ReclamosRepository {
    override suspend fun getReclamos(): List<ReclamoDTO> = reclamosApiService.getReclamos()
    override suspend fun getStatusReclamo(): List<StatusReclamoDTO> = reclamosApiService.getStatusReclamo()
    override suspend fun getReclamoBy(idReclamo: Long): ReclamoDTO = reclamosApiService.getReclamoBy(idReclamo)
    override suspend fun getReclamosBy(nombreUsuario: String): List<ReclamoDTO> = reclamosApiService.getReclamosBy(nombreUsuario)
    override suspend fun getEventos(): List<EventoDTO> = reclamosApiService.getEventos()
    override suspend fun partialUpdate(idReclamo: Long, operation: PatchDTO): ReclamoDTO = reclamosApiService.partialUpdate(idReclamo, arrayListOf(operation))
    override suspend fun updateReclamo(reclamo: ReclamoDTO): ReclamoDTO = reclamosApiService.updateReclamo(reclamo)
}