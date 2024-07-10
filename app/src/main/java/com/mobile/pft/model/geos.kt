/*
Éste modulo contiene todas los DTO asociados a operaciones dentro del contexto de geolocalizaciones
tales como: departamentos y localidades
 */


package com.mobile.pft.model

import kotlinx.serialization.Serializable

@Serializable
data class LocalidadDTO(
    val idLocalidad: Long,
    val nombre: String,
    val departamento: DepartamentoDTO
)

@Serializable
data class DepartamentoDTO (
    val idDepartamento: Long,
    val itr: ItrDTO,
    val nombre: String
)
