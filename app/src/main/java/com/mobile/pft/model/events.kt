package com.mobile.pft.model

import com.mobile.pft.utils.NamedObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TipoEventoDTO(
    val nombre: String
)

@Serializable
data class EventoDTO(
    val idEvento: Long,
    @SerialName(value = "titulo")
    override val nombre: String,
    val tiposEvento: TipoEventoDTO?
) : NamedObject {
    override fun toString(): String {
        return nombre
    }
}
