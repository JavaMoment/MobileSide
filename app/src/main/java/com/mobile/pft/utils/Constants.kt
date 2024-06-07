package com.mobile.pft.utils

enum class Status(
    val value: String
) {
    EN_PROGRESO("EN PROGRESO"), PENDIENTE("PENDIENTE"), RECHAZADO("RECHAZADO")
}

enum class TipoUsuario {
    ANALISTA, ESTUDIANTE, TUTOR
}

const val APPLICATION_JSON_PATCH = "application/json+patch"