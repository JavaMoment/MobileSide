package com.mobile.pft.model

import kotlinx.serialization.Serializable

@Serializable
data class TipoEventoDTO(
    val nombre: String
)

@Serializable
data class EventoDTO(
    val titulo: String,
    val tiposEvento: TipoEventoDTO?
)