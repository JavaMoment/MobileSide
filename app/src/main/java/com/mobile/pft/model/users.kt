/*
Éste modulo contiene todas los DTO asociados a operaciones dentro del contexto de usuarios
 */

package com.mobile.pft.model
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class UsuarioDTO(
    val nombreUsuario: String,
    val activo: Byte,
    val apellido1: String,
    val apellido2: String,
    val documento: String,
    //@Contextual val fechaNacimiento: Date,
    val genero: Char,
    val itr: ItrDTO,
    val localidad: LocalidadDTO,
    val mailInstitucional: String,
    val mailPersonal: String,
    val nombre1: String,
    val nombre2: String,
    val telefono: String
)

@Serializable
data class AnalistaDTO(
    val idAnalista: Long,
    val usuario: UsuarioDTO
)

@Serializable
data class EstudianteDTO(
    val idEstudiante: Long,
    val usuario: UsuarioDTO,
    val generacion: String
)

data class Estudiante(
    val idEstudiante: Long,
    val usuario: UsuarioDTO,
    val generacion: String
)
