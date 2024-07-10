package com.mobile.pft.model

import kotlinx.serialization.Serializable

@Serializable
data class PatchDTO(
    val op: String,
    val path: String,
    val value: String
)
