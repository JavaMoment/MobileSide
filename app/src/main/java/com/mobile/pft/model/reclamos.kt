/*
Éste modulo contiene todas los DTO asociados a operaciones dentro del contexto de reclamos
 */

package com.mobile.pft.model

import com.mobile.pft.utils.DateAsLongSerializer
import com.mobile.pft.utils.NamedObject
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class StatusReclamoDTO(
    val idStatus: Long,
    override val nombre: String
) : NamedObject

@Serializable
data class ReclamoDTO(
    val idReclamo: Long,
    val analista: AnalistaDTO,
    val estudiante: EstudianteDTO,
    val evento: EventoDTO,
    val statusReclamo: StatusReclamoDTO,
    val semestre: Int?,
    val creditos: Int?,
    @Serializable(with = DateAsLongSerializer::class)
    val auditDate: Date,
    @Serializable(with = DateAsLongSerializer::class)
    val modifDate: Date?,
    val modifUser: String?,
    val titulo: String,
    val descripcion: String,
    val detalle: String?
)