package com.mobile.pft.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mobile.pft.reclamos.data.NetworkReclamosRepository
import com.mobile.pft.reclamos.data.ReclamosRepository
import com.mobile.pft.reclamos.network.ReclamosApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container al nivel de la aplicación.
 */
interface AppContainer {
    // Acá le pedimos al singleton que implemente los repositorios para cada context [Usuarios, Reclamos, Login, Signup]
    val reclamosRepository: ReclamosRepository
}

/**
 * Implementar el contrato de nuestro contenedor para el contexto de nuestra api
 * que luego en tiempo de ejecución va a saber que hacer ;).
 *
 * Las variables (cofcof repos cofcof mappeo de los endpoints cofcof) no son inicializadas hasta ser llamadas
 * y son compartidas a lo largo de la app (viejo y querido singleton)
 */
class ApiContainer : AppContainer {
    // El emulador no puede contactarse por localhost con la api, posible solución para que quede más prolijo: usar grok
    private val baseUrl = "http://10.0.2.2:8080/WebSide/api/v1/"

    // Si un atributo no viene en la respuesta de la api, ignorarlo = settear null
    private val serializer = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Instancia Retrofit utilizando el kotlinx.serialization converter para transformar las respuestas
     * json a data classes (o no jeje).
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(serializer.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * Service object para las api calls al contexto del signup (lazy: mucho muy importante)
     */
    private val reclamosService: ReclamosApiService by lazy {
        retrofit.create(ReclamosApiService::class.java)
    }

    /**
     * Implementamos el signupRepo y luego lo vamos a poder inyectar en componentes armados por
     * \@composable y llamar a la info desde ahi muajajaj
     */
    override val reclamosRepository: ReclamosRepository by lazy {
        NetworkReclamosRepository(reclamosService)
    }
}