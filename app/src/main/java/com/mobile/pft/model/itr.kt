/*
Éste modulo contiene todas los DTO asociados a operaciones dentro del contexto de itrs
 */

package com.mobile.pft.model

import kotlinx.serialization.Serializable

@Serializable
data class ItrDTO(
    val idItr: Long,
    val activo: Boolean,
    val nombre: String
)
