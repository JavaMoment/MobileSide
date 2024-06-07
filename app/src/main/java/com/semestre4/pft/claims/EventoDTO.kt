package com.semestre4.pft.claims

import java.sql.Date

data class EventoDTO(
    val idEvento: Long,
    val titulo: String,
    val tiposEvento: TipoEventosDTO

){
    override fun toString(): String {
        return titulo
    }
}


data class TipoEventosDTO(
    val nombre: String
)






