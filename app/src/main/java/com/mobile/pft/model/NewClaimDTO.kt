package com.mobile.pft.model

data class NewClaimDTO(
    val titulo: String,
    val descripcion: String,
    val evento: Evento,
    val semestre: Int,
    val creditos: Int
)
