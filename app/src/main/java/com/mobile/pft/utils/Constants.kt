package com.mobile.pft.utils

enum class Status(
    val value: String
) {
    EN_PROCESO("EN PROCESO"), PENDIENTE("PENDIENTE"), RECHAZADO("RECHAZADO"), FINALIZADO("FINALIZADO")
}

enum class TipoUsuario {
    ANALISTA, ESTUDIANTE, TUTOR
}

const val APPLICATION_JSON_PATCH = "application/json+patch"

val creditBearingEvents = setOf("VME", "APE", "OPTATIVAS")