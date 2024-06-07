package com.semestre4.pft.claims

data class ClaimDTO(
    val titulo: String,
    val descripcion: String,
    val evento: EventoDTO,
    val semestre: Int,
    val creditos: Int
)
